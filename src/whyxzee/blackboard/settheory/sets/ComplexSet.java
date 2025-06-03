package whyxzee.blackboard.settheory.sets;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.settheory.*;

public class ComplexSet extends SetAbstract {

    public ComplexSet() {
        super(Constants.Unicode.COMPLEX_SET, "", SetType.COMPLEX);
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
        return number.isComplex();
    }

    @Override
    public final boolean equals(SetAbstract other) {
        if (!similarSetType(other)) {
            return false;
        }

        return getSetName().equals(other.getSetName());
    }

}
