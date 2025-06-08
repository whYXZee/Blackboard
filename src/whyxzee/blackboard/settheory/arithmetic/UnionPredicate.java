package whyxzee.blackboard.settheory.arithmetic;

import java.util.ArrayList;

import whyxzee.blackboard.settheory.predicates.*;
import whyxzee.blackboard.utils.SetUtils;

public class UnionPredicate {
    // TODO: redo, as union is not and, it is or
    /* Variables */
    private static ArrayList<PredicateAbstract> elementsOf = new ArrayList<PredicateAbstract>();
    private static ArrayList<PredicateAbstract> equals = new ArrayList<PredicateAbstract>();
    private static ArrayList<PredicateAbstract> ranges = new ArrayList<PredicateAbstract>();
    private static ArrayList<PredicateAbstract> ands = new ArrayList<PredicateAbstract>();
    // or predicate..?

    public static final ArrayList<PredicateAbstract> performUnion(ArrayList<PredicateAbstract> predicates) {
        if (predicates.size() == 0) {
            return new ArrayList<PredicateAbstract>();
        } else if (predicates.size() == 1) {
            return predicates.get(0).toPredicateList();
        }

        PredicateAbstract[] predicateArr = new PredicateAbstract[predicates.size()];
        for (int i = 0; i < predicates.size(); i++) {
            predicateArr[i] = predicates.get(i);
        }
        return performUnion(predicateArr);
    }

    public static final ArrayList<PredicateAbstract> performUnion(PredicateAbstract... predicates) {
        if (predicates.length == 0) {
            return new ArrayList<PredicateAbstract>();
        } else if (predicates.length == 1) {
            return predicates[0].toPredicateList();
        }

        if (!areArraysEmpty()) {
            elementsOf.clear();
            equals.clear();
            ranges.clear();
            ands.clear();
        }

        for (PredicateAbstract i : predicates) {
            if (!contains(i)) {
                add(i);
            }
        }

        /* Element Of */
        if (elementsOf.size() != 0) {
            // check if DefinedLists are redundant because of new predicates
            elementsOf = SetUtils.UnionHelper.unionDomains(elementsOf);
        }
        if (ranges.size() >= 2) {
            ArrayList<RangePredicate> specialization = new ArrayList<RangePredicate>();
            for (PredicateAbstract i : predicates) {
                specialization.add((RangePredicate) i);
            }
            ranges = UnionBounds.performUnion(specialization).toPredicateList();
        }
        return getPredicateList();
    }

    //
    // Get & Set Methods
    //
    private static final ArrayList<PredicateAbstract> getPredicateList() {
        ArrayList<PredicateAbstract> output = new ArrayList<PredicateAbstract>() {
            {
                addAll(elementsOf);
                addAll(equals);
                addAll(ranges);
            }
        };
        if (ands.isEmpty()) {
            return output;
        }

        output.addAll(ands);
        return new OrPredicate(output).toPredicateList();

    }

    private static final void add(PredicateAbstract predicate) {
        switch (predicate.getType()) {
            case ELEMENT_OF:
                elementsOf.add(predicate);
                break;
            case EQUAL:
                equals.add(predicate);
                break;
            case OR:
                break;
            case AND:
                ands.add(predicate);
                break;
            case RANGE:
                ranges.add(predicate);
                break;
            default:
                break;
        }
    }

    //
    // Boolean Methods
    //
    private static final boolean contains(PredicateAbstract predicate) {
        switch (predicate.getType()) {
            case ELEMENT_OF:
                for (PredicateAbstract i : elementsOf) {
                    if (i.equals(predicate)) {
                        return true;
                    }
                }
                return false;
            case EQUAL:
                for (PredicateAbstract i : equals) {
                    if (i.equals(predicate)) {
                        return true;
                    }
                }
                return false;
            case OR:
                return false;
            case AND:
                for (PredicateAbstract i : ands) {
                    if (i.equals(predicate)) {
                        return true;
                    }
                }
                return false;
            case RANGE:
                for (PredicateAbstract i : ranges) {
                    if (i.equals(predicate)) {
                        return true;
                    }
                }
                return false;
            default:
                return false;
        }
    }

    private static final boolean areArraysEmpty() {
        return elementsOf.size() != 0 || equals.size() != 0 || ranges.size() != 0 || ands.size() != 0;
    }
}
