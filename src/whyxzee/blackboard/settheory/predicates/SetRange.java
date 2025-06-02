package whyxzee.blackboard.settheory.predicates;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.utils.ArithmeticUtils;

/**
 * A package for a boolean predicate for a set.
 * 
 * <p>
 * This package is built similar to a <b>var</b> <b>inequalityType</b> number
 * (example: x < 5)
 * 
 * <p>
 * The functionality of this class has been checked on {@code 6/2/2025} and
 * nothing has changed since.
 */
public class SetRange extends PredicateAbstract {
    /* Variables */
    private double lowerBound;
    private boolean isLowerOpen;
    private double upperBound;
    private boolean isUpperOpen;

    public SetRange(double lowerBound, boolean isLowerOpen, String var, boolean isUpperOpen,
            double upperBound) {
        super(var, PredicateType.INEQUALITY);
        if (upperBound < lowerBound) {
            throw new ArithmeticException("lowerRange is greater than upperRange, thus the range is invalid.");
        }

        /* Declarations */
        this.lowerBound = lowerBound;
        this.isLowerOpen = isLowerOpen;
        this.isUpperOpen = isUpperOpen;
        this.upperBound = upperBound;
    }

    @Override
    public final String toString() {
        /* Lower Bound */
        String output = Double.toString(lowerBound);
        if (ArithmeticUtils.isInteger(lowerBound)) {
            output = Integer.toString((int) lowerBound);
        }
        if (isLowerOpen) {
            output += Constants.Unicode.LESS_THAN;
        } else {
            output += Constants.Unicode.LESS_THAN_EQUAL;
        }

        /* Variables */
        output += getVar();

        /* UpperBound */
        if (isUpperOpen) {
            output += Constants.Unicode.LESS_THAN;
        } else {
            output += Constants.Unicode.LESS_THAN_EQUAL;
        }

        if (ArithmeticUtils.isInteger(upperBound)) {
            return output + (int) upperBound;
        }
        return output + upperBound;
    }

    @Override
    public final String printConsole() {
        return toString();
    }

    //
    // Boolean Methods
    //
    @Override
    public boolean checkPredicate(double value) {
        /* Lower Bound */
        if (isLowerOpen && !(lowerBound < value)) {
            return false;
        } else if (!isLowerOpen && !(lowerBound <= value)) {
            return false;
        }

        /* Upper Bound */
        if (isUpperOpen && !(value < upperBound)) {
            return false;
        } else if (!isUpperOpen && !(value <= upperBound)) {
            return false;
        }

        return true;
    }

}
