package whyxzee.blackboard.settheory.predicates;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.settheory.Bound;

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
public class RangePredicate extends PredicateAbstract {
    /* Variables */
    private NumberAbstract lowerBound;
    private boolean isLowerOpen;
    private NumberAbstract upperBound;
    private boolean isUpperOpen;

    public RangePredicate(
            NumberAbstract lowerBound, boolean isLowerOpen, String var, boolean isUpperOpen,
            NumberAbstract upperBound) {
        super(var, PredicateType.INEQUALITY);
        if (upperBound.lessThanEqual(lowerBound)) {
            throw new ArithmeticException("lowerRange is greater than upperRange, thus the range is invalid.");
        }

        /* Declarations */
        this.lowerBound = lowerBound;
        this.isLowerOpen = isLowerOpen;
        this.isUpperOpen = isUpperOpen;
        this.upperBound = upperBound;
    }

    public RangePredicate(Bound lowerBound, String var, Bound upperBound) {
        super(var, PredicateType.INEQUALITY);
        this.lowerBound = lowerBound.getValue();
        this.isLowerOpen = lowerBound.isOpen();
        this.upperBound = upperBound.getValue();
        this.isUpperOpen = upperBound.isOpen();

        if (this.upperBound.lessThanEqual(this.lowerBound)) {
            throw new ArithmeticException("lowerRange is greater than upperRange, thus the range is invalid.");
        }
    }

    @Override
    public final String toString() {
        /* Lower Bound */
        String output = lowerBound.toString();
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

        return output + upperBound.toString();
    }

    @Override
    public final String printConsole() {
        return toString();
    }

    //
    // Get & Set Methods
    //
    public final NumberAbstract getLowerBound() {
        return lowerBound;
    }

    public final void setLowerBound(NumberAbstract lowerBound) {
        this.lowerBound = lowerBound;
    }

    public final boolean isLowerOpen() {
        return isLowerOpen;
    }

    public final void setLowerOpen(boolean isLowerOpen) {
        this.isLowerOpen = isLowerOpen;
    }

    public final NumberAbstract getUpperBound() {
        return upperBound;
    }

    public final void setUpperBound(NumberAbstract upperBound) {
        this.upperBound = upperBound;
    }

    public final boolean isUpperOpen() {
        return isUpperOpen;
    }

    public final void setUpperOpen(boolean isUpperOpen) {
        this.isUpperOpen = isUpperOpen;
    }

    //
    // Boolean Methods
    //
    @Override
    public boolean checkPredicate(NumberAbstract number) {
        /* Lower Bound */
        if (isLowerOpen && !lowerBound.lessThan(number)) {
            return false;
        } else if (!isLowerOpen && !lowerBound.lessThanEqual(number)) {
            return false;
        }

        /* Upper Bound */
        if (isUpperOpen && !number.lessThan(upperBound)) {
            return false;
        } else if (!isUpperOpen && !number.lessThanEqual(upperBound)) {
            return false;
        }

        return true;
    }

    @Override
    public final boolean equals(PredicateAbstract other) {
        if (!other.isType(PredicateType.INEQUALITY) || !other.getVar().equals(getVar())) {
            return false;
        }

        RangePredicate range = (RangePredicate) other;
        return (lowerBound == range.getLowerBound()) &&
                (isLowerOpen == range.isLowerOpen()) &&
                (upperBound == range.getUpperBound()) &&
                (isUpperOpen == range.isUpperOpen());
    }

}
