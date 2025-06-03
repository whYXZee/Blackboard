package whyxzee.blackboard.settheory.sets;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.numbers.NumberAbstract.NumType;
import whyxzee.blackboard.settheory.SetAbstract;
import whyxzee.blackboard.settheory.SetBuilder;
import whyxzee.blackboard.utils.ArithmeticUtils;

public class RationalSet extends SetAbstract {

    public RationalSet() {
        super(Constants.Unicode.RATIONAL_SET, "", SetType.RATIONAL);
    }

    @Override
    public final String toString() {
        return getSetName();
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
        if (number.isNumType(NumType.IMAGINARY)) {
            return false;
        }

        return ArithmeticUtils.isRational(number.getValue()) || ArithmeticUtils.isInteger(number.getValue());
    }

    @Override
    public final boolean equals(SetAbstract other) {
        if (!similarSetType(other)) {
            return false;
        }

        return getSetName().equals(other.getSetName());
    }

}
