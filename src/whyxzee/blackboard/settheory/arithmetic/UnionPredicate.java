package whyxzee.blackboard.settheory.arithmetic;

import java.util.ArrayList;

import whyxzee.blackboard.settheory.predicates.*;
import whyxzee.blackboard.utils.SetUtils;

public class UnionPredicate {
    /* Variables */
    private ArrayList<PredicateAbstract> elementsOf;

    public UnionPredicate() {
        elementsOf = new ArrayList<PredicateAbstract>();
    }

    public final ArrayList<PredicateAbstract> performUnion(ArrayList<PredicateAbstract> predicates) {
        if (predicates.size() == 0) {
            return predicates;
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
    private final ArrayList<PredicateAbstract> getPredicateList() {
        return new ArrayList<PredicateAbstract>() {
            {
                addAll(elementsOf);
            }
        };
    }

    private final void add(PredicateAbstract predicate) {
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
    private final boolean contains(PredicateAbstract predicate) {
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
