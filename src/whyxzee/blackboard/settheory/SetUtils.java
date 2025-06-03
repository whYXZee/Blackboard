package whyxzee.blackboard.settheory;

import java.util.ArrayList;
import java.util.Arrays;

import whyxzee.blackboard.settheory.predicates.PredicateAbstract;
import whyxzee.blackboard.settheory.predicates.EvenOddPredicate;
import whyxzee.blackboard.settheory.predicates.InequalityPredicate;

public class SetUtils {
    //
    // Management Methods
    //
    public static final ArrayList<PredicateAbstract> sortPredicates(ArrayList<PredicateAbstract> predicates) {
        /* Sort via Comparable<> implementation */
        PredicateAbstract[] arr = new PredicateAbstract[predicates.size()];
        for (int i = 0; i < predicates.size(); i++) {
            arr[i] = predicates.get(i);
        }
        Arrays.sort(arr);

        /* */
        ArrayList<PredicateAbstract> output = new ArrayList<PredicateAbstract>();
        for (PredicateAbstract i : arr) {
            output.add(i);
        }
        return output;

    }

    public static final SetAbstract[] augmentDomains(SetAbstract[] domains, SetAbstract addend) {
        if (inDomains(addend, domains)) {
            return domains;
        }

        SetAbstract[] newDomains = Arrays.copyOf(domains, domains.length + 1);
        newDomains[domains.length] = addend;
        return newDomains;
    }

    public static final ArrayList<PredicateAbstract> unionPredicates(String var,
            ArrayList<PredicateAbstract> predicates) {
        /* Predicates */
        IntervalSet ineq = null;
        Boolean isConditionOdd = null; // for EvenOddPredicate

        for (PredicateAbstract i : predicates) {
            switch (i.getType()) {
                case INEQUALITY:
                    InequalityPredicate ineqPred = (InequalityPredicate) i;
                    if (ineq != null) {
                        ineq = ineqPred.toInterval();
                    } else {
                        ineq = (IntervalSet) ineq.union(ineqPred.toInterval());
                    }
                    break;
                case RANGE:
                    break;
                case EVEN_ODD:
                    EvenOddPredicate evenOdd = (EvenOddPredicate) i;
                    if (isConditionOdd == null) {
                        isConditionOdd = evenOdd.isConditionOdd();
                    } else if (isConditionOdd != evenOdd.isConditionOdd()) {
                        isConditionOdd = null;
                    }
                    break;

                default:
                    break;
            }
        }

        ArrayList<PredicateAbstract> output = new ArrayList<PredicateAbstract>();

        if (isConditionOdd != null) {
            output.add(new EvenOddPredicate(var, isConditionOdd));
        }
        return output;
    }

    public static final int[][] indexOfPredicates(ArrayList<PredicateAbstract> predicates) {
        int[][] output = { {}, {}, {} };

        for (int i = 0; i < predicates.size(); i++) {
            PredicateAbstract indexPredicate = predicates.get(i);
            int index;
            switch (indexPredicate.getType()) {
                case INEQUALITY:
                    index = 0;
                    break;
                case RANGE:
                    index = 1;
                    break;
                case EVEN_ODD:
                    index = 2;
                    break;
                default:
                    index = -1;
                    break;
            }

            int[] indexArray = output[index];
            int indexArrayLength = indexArray.length;
            indexArray = Arrays.copyOf(indexArray, indexArrayLength + 1);
            indexArray[indexArrayLength] = i;
        }

        return output;
    }

    //
    // Boolean Methods
    //
    public static final boolean inDomains(SetAbstract idk, SetAbstract[] domains) {
        for (SetAbstract i : domains) {
            if (i.equals(idk)) {
                return true;
            }
        }

        return false;
    }

    public static final boolean equalOrPredicates(PredicateAbstract[] one, PredicateAbstract[] two) {
        if (one.length != two.length) {
            return false;
        }
        Arrays.sort(one);
        Arrays.sort(two);
        return Arrays.equals(one, two);
    }

}
