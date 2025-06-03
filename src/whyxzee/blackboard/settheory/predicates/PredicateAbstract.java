package whyxzee.blackboard.settheory.predicates;

import whyxzee.blackboard.numbers.NumberAbstract;

/**
 * A package for defining which numbers are in a set.
 */
public abstract class PredicateAbstract implements Comparable<PredicateAbstract> {
    /* Variables */
    private String var;
    private PredicateType type;

    public enum PredicateType {
        INEQUALITY(4), // 1
        RANGE(3), // 2
        EVEN_ODD(2), // 3
        OR(1); // 4

        public final int orderNum;

        private PredicateType(int orderNum) {
            this.orderNum = orderNum;
        }
    }

    public PredicateAbstract(String var, PredicateType type) {
        this.var = var;
        this.type = type;
    }

    public abstract String printConsole();

    //
    // Get & Set Methods
    //
    public final PredicateType getType() {
        return type;
    }

    public final void getType(PredicateType type) {
        this.type = type;
    }

    public final String getVar() {
        return var;
    }

    public final void setVar(String var) {
        this.var = var;
    }

    //
    // Boolean Methods
    //
    public final boolean isType(PredicateType type) {
        return this.type == type;
    }

    public abstract boolean checkPredicate(NumberAbstract number);

    public abstract boolean equals(PredicateAbstract other);

    //
    // Comparable Interface
    //
    @Override
    public int compareTo(PredicateAbstract other) {
        return Integer.compare(type.orderNum, other.getType().orderNum);
    }
}
