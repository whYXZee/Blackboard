package whyxzee.blackboard.numbers.uncountable;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.Uncountable;

/**
 * A package for the general infinity.
 */
public class Infinity extends Uncountable {
    /* Variables */
    private boolean isNegative;

    public Infinity() {
        super(10, Double.POSITIVE_INFINITY);
        this.isNegative = false;
    }

    public Infinity(boolean isNegative) {
        super(10, isNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
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
}
