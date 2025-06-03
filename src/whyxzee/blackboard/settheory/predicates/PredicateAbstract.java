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

    //
    // Boolean Methods
    //
    public abstract boolean checkPredicate(NumberAbstract number);
}
