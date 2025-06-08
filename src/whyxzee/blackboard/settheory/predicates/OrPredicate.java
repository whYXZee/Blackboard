package whyxzee.blackboard.settheory.predicates;

import java.util.ArrayList;

import whyxzee.blackboard.numbers.NumberAbstract;

public class OrPredicate extends PredicateAbstract {
    /* Variables */
    private ArrayList<PredicateAbstract> predicates;

    public OrPredicate(ArrayList<PredicateAbstract> predicates) {
        super("", PredicateType.OR);
        this.predicates = predicates;
    }

    public OrPredicate(PredicateAbstract... predicates) {
        super("", PredicateType.OR);
        this.predicates = new ArrayList<PredicateAbstract>();
        for (PredicateAbstract i : predicates) {
            this.predicates.add(i);
        }
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

    /**
     * Condenses down predicates so there are no redundancies.
     */
    public final void simplify() {

    }

    //
    // Get & Set Methods
    //
    public final ArrayList<PredicateAbstract> getPredicates() {
        return predicates;
    }

    public final void addPredicate(PredicateAbstract predicate) {
        if (!contains(predicate)) {
            predicates.add(predicate);
        }
    }

    //
    // Boolean Methods
    //
    @Override
    public boolean checkPredicate(NumberAbstract number) {
        for (PredicateAbstract i : predicates) {
            if (i.checkPredicate(number)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean equals(PredicateAbstract other) {
        if (!other.isType(PredicateType.OR)) {
            return false;
        }

        OrPredicate or = (OrPredicate) other;
        return predicates == or.getPredicates();
    }

    public final boolean contains(PredicateAbstract predicate) {
        for (PredicateAbstract i : predicates) {
            if (i.equals(predicate)) {
                return true;
            }
        }
        return false;
    }

}
