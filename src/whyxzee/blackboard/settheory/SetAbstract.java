package whyxzee.blackboard.settheory;

import whyxzee.blackboard.numbers.NumberAbstract;

public abstract class SetAbstract {
    /* Variables */
    private String setName;
    private SetType type;

    public enum SetType {
        BUILDER,
        DEFINED_LIST,
        AMBIGUOUS_LIST,
        SOLUTION,
        INTERVAL,

        NULL
    }

    public SetAbstract(String setName, SetType type) {
        this.setName = setName;
        this.type = type;
    }

    public abstract String printConsole();

    public abstract IntervalSet toInterval();

    public abstract DefinedList toDefinedList();

    public abstract SetBuilder toBuilder();

    //
    // Arithmetic Methods
    //
    public abstract SetAbstract union(SetAbstract other);

    public abstract SetAbstract disjunction(SetAbstract other);

    public abstract SetAbstract complement(SetAbstract universe);

    //
    // Get & Set Methods
    //
    public final String getSetName() {
        return setName;
    }

    public final void setSetName(String setName) {
        this.setName = setName;
    }

    public final SetType getType() {
        return type;
    }

    public final void setType(SetType type) {
        this.type = type;
    }

    //
    // Boolean Methods
    //
    public abstract boolean inSet(NumberAbstract number);

    public final boolean isType(SetType type) {
        return this.type == type;
    }

    public abstract boolean equals(SetAbstract other);
}
