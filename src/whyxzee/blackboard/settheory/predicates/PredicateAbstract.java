package whyxzee.blackboard.settheory.predicates;

import java.util.ArrayList;

import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.settheory.IntervalSet;

public abstract class PredicateAbstract {
    /* Variables */
    private String var;
    private PredicateType type;

    public enum PredicateType {
        EQUAL,
        RANGE,
        OR,
        AND,
        ELEMENT_OF
    }

    public PredicateAbstract(String var, PredicateType type) {
        this.var = var;
        this.type = type;
    }

    public abstract String printConsole();

    public abstract IntervalSet toInterval();

    public final ArrayList<PredicateAbstract> toPredicateList() {
        ArrayList<PredicateAbstract> output = new ArrayList<PredicateAbstract>();
        output.add(this);
        return output;
    }

    //
    // Get & Set Methods
    //
    public final String getVar() {
        return var;
    }

    public final PredicateType getType() {
        return type;
    }

    //
    // Boolean Methods
    //
    public final boolean isType(PredicateType type) {
        return this.type == type;
    }

    public abstract boolean checkPredicate(NumberAbstract number);

    public abstract boolean equals(PredicateAbstract other);
}
