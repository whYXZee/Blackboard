package whyxzee.blackboard.numbers;

import whyxzee.blackboard.Constants;

public class Infinity extends NumberAbstract {
    /* Variables */
    private boolean isNegative;

    public Infinity() {
        super(Double.POSITIVE_INFINITY, NumType.INFINITY);
        this.isNegative = false;
    }

    public Infinity(boolean isNegative) {
        super(isNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY, NumType.INFINITY);
        this.isNegative = isNegative;
    }

    @Override
    public final String toString() {
        return (isNegative ? "-" : "") + Constants.Unicode.INFINITY;
    }

    @Override
    public final String printConsole() {
        return isNegative ? "Negative Infinity" : "Infinity";
    }

    //
    // Get & Set Mehtods
    //
    public final boolean isNegative() {
        return isNegative;
    }

    public final void setNegative(boolean isNegative) {
        this.isNegative = isNegative;
    }

    //
    // Boolean Methods
    //
    @Override
    public final boolean equals(NumberAbstract other) {
        if (!other.isNumType(getNumType())) {
            return false;
        }

        Infinity num = (Infinity) other;
        return num.isNegative() == isNegative();
    }

    @Override
    public final boolean isImaginary() {
        return false;
    }

    @Override
    public final boolean isComplex() {
        return false;
    }

    @Override
    public final boolean isRational() {
        return false;
    }

    @Override
    public final boolean isInteger() {
        return false;
    }
}
