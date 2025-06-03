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
    // Boolean Methods
    //
    @Override
    public boolean isInteger() {
        return false;
    }

}
