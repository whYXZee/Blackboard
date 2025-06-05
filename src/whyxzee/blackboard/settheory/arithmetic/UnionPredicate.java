package whyxzee.blackboard.settheory.arithmetic;

import java.util.ArrayList;

import whyxzee.blackboard.settheory.predicates.*;
import whyxzee.blackboard.utils.SetUtils;

public class UnionPredicate {
    /* Variables */
    private static ArrayList<PredicateAbstract> elementsOf = new ArrayList<PredicateAbstract>();

    public static final ArrayList<PredicateAbstract> performUnion(ArrayList<PredicateAbstract> predicates) {
        if (predicates.size() == 0) {
            return predicates;
        }

        if (elementsOf.size() != 0) {
            elementsOf.clear();
        }

        for (PredicateAbstract i : predicates) {
            if (!contains(i)) {
                add(i);
            }
        }

        elementsOf = SetUtils.UnionHelper.unionDomains(elementsOf);
        return getPredicateList();
    }

    //
    // Get & Set Methods
    //
    private static final ArrayList<PredicateAbstract> getPredicateList() {
        return new ArrayList<PredicateAbstract>() {
            {
                addAll(elementsOf);
            }
        };
    }

    private static final void add(PredicateAbstract predicate) {
        switch (predicate.getType()) {
            case ELEMENT_OF:
                elementsOf.add((ElementOf) predicate);
                break;
            case EQUAL:
                break;
            case OR:
                break;
            case RANGE:
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
            case OR:
            case RANGE:
            default:
                return false;
        }
    }
}
