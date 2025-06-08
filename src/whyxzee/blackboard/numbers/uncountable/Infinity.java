package whyxzee.blackboard.numbers.uncountable;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.Uncountable;

/**
 * A package for the general infinity.
 * 
 * <p>
 * The functionality of this class has not been checked.
 */
public class Infinity extends Uncountable {
    /**
     * Creates a power infinity.
     */
    public Infinity() {
        super(1, InfType.INFINITY);
    }

    public Infinity(int size) {
        super(size, InfType.INFINITY);
    }

    public Infinity(boolean isNegative) {
        super(1, InfType.INFINITY, isNegative);
    }

    public Infinity(int size, boolean isNegative) {
        super(size, InfType.INFINITY, isNegative);
    }

    @Override
    public final String toString() {
        return (isNegative() ? "-" : "") + Constants.Unicode.INFINITY;
    }

    @Override
    public final String printConsole() {
        return isNegative() ? "Negative Infinity" : "Infinity";
    }
}
