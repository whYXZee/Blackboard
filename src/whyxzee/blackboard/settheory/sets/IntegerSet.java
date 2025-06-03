package whyxzee.blackboard.settheory.sets;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.settheory.SetAbstract;
import whyxzee.blackboard.settheory.SetBuilder;

public class IntegerSet extends SetAbstract {
    public IntegerSet() {
        super(Constants.Unicode.INTEGER_SET, "", SetType.INTEGER);
    }

    @Override
    public final String toString() {
        return getSetName() + " = {..., -3, -2, -1, 0, 1, 2, 3, ...}";
    }

    @Override
    public String printConsole() {
        return toString();
    }

    //
    // Arithmetic Methods
    //
    @Override
    public final SetAbstract union(SetAbstract other) {
        if (equals(other)) {
            return this;
        }

        if (other.isSetType(SetType.SET_BUILDER)) {
            return other.union(this);
        }

        SetAbstract[] domains = { this, other };
        return new SetBuilder("", "n", domains);
    }

    //
    // Boolean Methods
    //
    @Override
    public boolean inSet(NumberAbstract number) {
        return number.isInteger();
    }

    @Override
    public final boolean equals(SetAbstract other) {
        if (!similarSetType(other)) {
            return false;
        }

        return getSetName().equals(other.getSetName());
    }

}
