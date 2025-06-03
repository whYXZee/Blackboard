package whyxzee.blackboard.settheory;

import whyxzee.blackboard.numbers.NumberAbstract;

public abstract class SetAbstract implements Comparable<SetAbstract> {
    /* Variables */
    private String setName;
    private String var;
    private SetType setType;

    public enum SetType {
        INTERVAL(-3),
        SET_BUILDER(-2),
        SET_LIST(-1),

        /* Common Sets */
        REAL(5),
        NATURAL(4),
        INTEGER(3),
        RATIONAL(2),
        IMAGINARY(1),
        COMPLEX(0);

        public final int orderNum;

        private SetType(int orderNum) {
            this.orderNum = orderNum;

        }
    }

    public SetAbstract(String setName, String var, SetType setType) {
        this.setName = setName;
        this.var = var;
        this.setType = setType;
    }

    public abstract String printConsole();

    public void simplify() {
    }

    //
    // Arithmetic Methods
    //
    public abstract SetAbstract union(SetAbstract other);

    //
    // Get & Set Methods
    //
    public final String getSetName() {
        return setName;
    }

    public final void setSetName(String setName) {
        this.setName = setName;
    }

    public final String getVar() {
        return var;
    }

    public final void setVar(String var) {
        this.var = var;
    }

    public final SetType getSetType() {
        return setType;
    }

    public final void setSetType(SetType setType) {
        this.setType = setType;
    }

    //
    // Boolean Methods
    //
    public final boolean similarSetType(SetAbstract other) {
        return setType == other.getSetType();
    }

    public final boolean isSetType(SetType setType) {
        return this.setType == setType;
    }

    public abstract boolean inSet(NumberAbstract number);

    public abstract boolean equals(SetAbstract other);

    @Override
    public final int compareTo(SetAbstract other) {
        return Integer.compare(setType.orderNum, other.getSetType().orderNum);
    }
}
