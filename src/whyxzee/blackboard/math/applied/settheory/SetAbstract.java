package whyxzee.blackboard.math.applied.settheory;

import whyxzee.blackboard.math.pure.numbers.BNum;
import whyxzee.blackboard.math.pure.numbers.Complex;

/**
 * An abstract package for generic sets in Set Theory.
 * 
 * <p>
 * The functionality of this class has not been checked.
 */
public abstract class SetAbstract {
    /* Variables */
    private String setName;
    private SetType type;

    public enum SetType {
        BUILDER,
        DEFINED_LIST,
        AMBIGUOUS_LIST,
        INTERVAL,

        NULL
    }

    public SetAbstract(String setName, SetType type) {
        this.setName = setName;
        this.type = type;
    }

    public abstract String printConsole();

    // #region Conversions
    // public abstract IntervalSet toInterval();

    public abstract DefinedList toDefinedList();

    // public abstract SetBuilder toBuilder();
    // #endregion

    // #region Operations
    public abstract SetAbstract union(SetAbstract other);

    public abstract SetAbstract intersection(SetAbstract other);

    public abstract SetAbstract complement(SetAbstract universe);
    // #endregion

    // #region Get / Set
    public abstract BNum cardinality();

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
    // #endregion

    // #region Overlap Bools
    public abstract boolean inSet(Complex number);

    /**
     * Checks if the two sets have no terms in common.
     * 
     * @param other
     * @return
     */
    public final boolean isDisjoint(SetAbstract other) {
        return intersection(other).isType(SetType.NULL);
    }

    /**
     * Checks if <b>this</b> is a superset of <b>other</b> (conversely, is
     * <b>other</b> is a subset of <b>this</b>).
     * 
     * <p>
     * Essentially, does <b>this</b> encompass <b>other</b>?
     * 
     * @param other
     * @return
     */
    public abstract boolean isSuperset(SetAbstract other);
    // #endregion

    // #region Comparison Bools
    public final boolean isType(SetType type) {
        return this.type == type;
    }

    public abstract boolean equals(SetAbstract other);
    // #endregion
}
