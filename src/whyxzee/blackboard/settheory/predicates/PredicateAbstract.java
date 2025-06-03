package whyxzee.blackboard.settheory.predicates;

import whyxzee.blackboard.numbers.NumberAbstract;

public abstract class PredicateAbstract {
    /* Variables */
    private String var;

    public PredicateAbstract(String var) {
        this.var = var;
    }

    public abstract String printConsole();

    //
    // Boolean Methods
    //
    public abstract boolean checkPredicate(NumberAbstract number);
}
