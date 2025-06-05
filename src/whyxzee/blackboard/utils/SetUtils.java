package whyxzee.blackboard.utils;

import java.util.ArrayList;

import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.settheory.AmbiguousList;
import whyxzee.blackboard.settheory.SetAbstract;
import whyxzee.blackboard.settheory.SetAbstract.SetType;
import whyxzee.blackboard.settheory.predicates.ElementOf;
import whyxzee.blackboard.settheory.predicates.OrPredicate;
import whyxzee.blackboard.settheory.predicates.PredicateAbstract;
import whyxzee.blackboard.settheory.predicates.PredicateAbstract.PredicateType;

public class SetUtils {

    /**
     * The functionality of this class has been checked on {@code 6/5/2025}, and the
     * following needs to be checked:
     * <ul>
     * <li>union of OrPredicates with ElementOf in unionDomains()
     */
    public static final class UnionHelper {
        /**
         * Performs a union on {@link whyxzee.blackboard.settheory.predicates.ElementOf}
         * predicates.
         * 
         * @param predicates
         * @return
         */
        public static final ArrayList<PredicateAbstract> unionDomains(ArrayList<PredicateAbstract> predicates) {
            // TODO: implement "not element of" type of predicates
            if (predicates.size() == 0) {
                return predicates;
            }

            ArrayList<AmbiguousList> domains = new ArrayList<AmbiguousList>();
            ArrayList<PredicateAbstract> output = new ArrayList<PredicateAbstract>();
            for (PredicateAbstract i : predicates) {
                if (!i.isType(PredicateType.ELEMENT_OF) && !i.isType(PredicateType.OR)) {
                    break;
                }

                if (i.isType(PredicateType.OR)) {
                    // has not been tested
                    for (PredicateAbstract j : ((OrPredicate) i).getPredicates()) {
                        if (j.isType(PredicateType.ELEMENT_OF)) {
                            SetAbstract jDomain = ((ElementOf) j).getDomain();
                            if (jDomain.isType(SetType.AMBIGUOUS_LIST)) {
                                domains.add((AmbiguousList) jDomain);
                            } else {
                                output.add(j);
                            }
                        }
                    }
                } else {
                    SetAbstract iDomain = ((ElementOf) i).getDomain();
                    if (iDomain.isType(SetType.AMBIGUOUS_LIST)) {
                        domains.add((AmbiguousList) iDomain);
                    } else {
                        output.add(i);
                    }
                }
            }

            // is this even needed?
            if (domains.size() == 0 && output.size() == 0) {
                return predicates;
            }

            int iGreatestOrder = 0; // index of greatest order
            int greatestOrder = domains.get(0).getOrder();
            for (int i = 1; i < domains.size(); i++) {
                int indexOrder = domains.get(i).getOrder();
                if (greatestOrder < indexOrder) {
                    iGreatestOrder = i;
                }
            }

            output.add(domains.get(iGreatestOrder).toPredicate());
            return output;
        }

    }

    /**
     * Returns an array, where the 0th index is the number of domains needed, while
     * the 1st index is the index of the number that does not fit the domain.
     * 
     * @param numbers
     * @param domain
     * @return 0 if the domain covers all the inputted numbers
     *         <li>1 if the domain covers all the inputted numbers except one
     *         <li>2+ if more than one domain is needed
     */
    public static final int[] needsTwoDomains(ArrayList<NumberAbstract> numbers, AmbiguousList domain) {
        if (numbers.size() == 0) {
            int[] output = { 0, 0 };
            return output;
        }

        int[] output = { 0, 0 };
        for (int i = 0; i < numbers.size(); i++) {
            NumberAbstract iNum = numbers.get(i);
            if (!domain.inSet(iNum)) {
                System.out.println(iNum);
                output[0]++;
                output[1] = i;
            }
            if (output[0] == 2) {
                return output;
            }
        }

        return output;
    }

}
