package whyxzee.blackboard.settheory.predicates;

import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.settheory.SetUtils;

public class OrPredicate extends PredicateAbstract {
    /* Variables */
    private PredicateAbstract[] predicates;

    public OrPredicate(String var, PredicateAbstract... predicates) {
        super(var, PredicateType.OR);
        this.predicates = predicates;
    }

    @Override
    public final String toString() {
        String output = "";
        for (int i = 0; i < predicates.length; i++) {
            if (i != 0) {
                output += " or ";
            }
            output += predicates[i];
        }
        return output;
    }

    @Override
    public String printConsole() {
        String output = "";
        for (int i = 0; i < predicates.length; i++) {
            if (i != 0) {
                output += " or ";
            }
            output += predicates[i].printConsole();
        }
        return output;
    }

    //
    // Get & Set Methods
    //
    public final PredicateAbstract[] getPredicates() {
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
    public boolean equals(PredicateAbstract other) {
        if (!other.isType(PredicateType.INEQUALITY) || !other.getVar().equals(getVar())) {
            return false;
        }

        OrPredicate or = (OrPredicate) other;
        return SetUtils.equalOrPredicates(predicates, or.getPredicates());
    }

}
