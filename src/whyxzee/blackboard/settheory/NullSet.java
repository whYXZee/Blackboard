package whyxzee.blackboard.settheory;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.numbers.RealNumber;

/**
 * A package for sets with a cardinality of zero.
 */
public class NullSet extends SetAbstract {
    public NullSet(String setName) {
        super(setName, SetType.NULL);
    }

    @Override
    public final String toString() {
        return getSetName() + " = " + Constants.Unicode.NULL_SET;
    }

    @Override
    public final String printConsole() {
        return toString();
    }

    @Override
    public IntervalSet toInterval() {
        throw new UnsupportedOperationException("Unimplemented method 'toDefinedList'");
    }

    @Override
    public DefinedList toDefinedList() {
        return new DefinedList(getSetName(), new ArrayList<NumberAbstract>());
    }

    @Override
    public SetBuilder toBuilder() {
        return new SetBuilder(getSetName(), Constants.NumberConstants.DEFAULT_VAR, null);
    }

    //
    // Arithmetic Methods
    //
    @Override
    public final NumberAbstract cardinality() {
        return new RealNumber(0);
    }

    @Override
    public SetAbstract union(SetAbstract other) {
        return other;
    }

    @Override
    public SetAbstract intersection(SetAbstract other) {
        return this;
    }

    @Override
    public SetAbstract complement(SetAbstract universe) {
        return universe;
    }

    //
    // Boolean Methods
    //
    @Override
    public final boolean inSet(NumberAbstract number) {
        return false;
    }

    @Override
    public final boolean isSuperset(SetAbstract other) {
        return false;
    }

    @Override
    public final boolean equals(SetAbstract other) {
        return other.isType(SetType.NULL);
    }

}
