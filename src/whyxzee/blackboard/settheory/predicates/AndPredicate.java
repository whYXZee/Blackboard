package whyxzee.blackboard.settheory.predicates;

import java.util.ArrayList;

import whyxzee.blackboard.numbers.NumberAbstract;

public class AndPredicate extends PredicateAbstract {
    /* Variables */
    private ArrayList<PredicateAbstract> predicates;

    public AndPredicate(ArrayList<PredicateAbstract> predicates) {
        super("", PredicateType.AND);
        this.predicates = predicates;
    }

    @Override
    public final String toString() {
        if (predicates.size() == 0) {
            return "";
        }

        String output = "(" + predicates.get(0).toString();
        for (int i = 1; i < predicates.size(); i++) {
            output += ", " + predicates.get(i);
        }
        output += ")";
        return output;
    }

    @Override
    public String printConsole() {
        if (predicates.size() == 0) {
            return "";
        }

        String output = "(" + predicates.get(0).printConsole();
        for (int i = 1; i < predicates.size(); i++) {
            output += ", " + predicates.get(i).printConsole();
        }
        output += ")";
        return output;
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
        if (!other.isType(PredicateType.AND)) {
            return false;
        }

        AndPredicate and = (AndPredicate) other;
        return predicates == and.getPredicates();
    }

}
