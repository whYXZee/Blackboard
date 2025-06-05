package whyxzee.blackboard.settheory.predicates;

import java.util.ArrayList;

import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.settheory.IntervalSet;

public class OrPredicate extends PredicateAbstract {
    /* Variables */
    private ArrayList<PredicateAbstract> predicates;

    public OrPredicate(ArrayList<PredicateAbstract> predicates) {
        super("", PredicateType.OR);
        this.predicates = predicates;
    }

    @Override
    public final String toString() {
        if (predicates.size() == 0) {
            return "";
        }

        String output = predicates.get(0).toString();
        for (int i = 1; i < predicates.size(); i++) {
            output += " or " + predicates.get(i);
        }
        return output;
    }

    @Override
    public String printConsole() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printConsole'");
    }

    @Override
    public IntervalSet toInterval() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toInterval'");
    }

    //
    // Get & Set Methods
    //
    public final ArrayList<PredicateAbstract> getPredicates() {
        return predicates;
    }

    //
    // Boolean Methods
    //
    @Override
    public boolean checkPredicate(NumberAbstract number) {
        for (PredicateAbstract i : predicates) {
            if (!i.checkPredicate(number)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public final boolean equals(PredicateAbstract other) {
        if (!other.isType(PredicateType.OR)) {
            return false;
        }

        OrPredicate or = (OrPredicate) other;
        return predicates == or.getPredicates();
    }

}
