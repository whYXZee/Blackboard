package whyxzee.blackboard.settheory.predicates;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.settheory.IntervalSet;

public class RangePredicate extends PredicateAbstract {
    /* Variables */
    private NumberAbstract lowBound;
    private boolean isLowOpen;
    private NumberAbstract upBound;
    private boolean isUpOpen;

    public RangePredicate(String var, IntervalSet interval) {
        super(var, PredicateType.RANGE);
        this.lowBound = interval.getLowBound();
        this.isLowOpen = interval.isLowOpen();
        this.upBound = interval.getUpBound();
        this.isUpOpen = interval.isUpOpen();
    }

    public RangePredicate(String var, NumberAbstract lowBound, boolean isLowOpen, boolean isUpOpen,
            NumberAbstract upBound) {
        super(var, PredicateType.RANGE);
        this.lowBound = lowBound;
        this.isLowOpen = isLowOpen;
        this.upBound = upBound;
        this.isUpOpen = isUpOpen;
    }

    @Override
    public final String toString() {
        if (lowBound.isInfinite() && !upBound.isInfinite()) {
            return getVar() + (isUpOpen ? Constants.Unicode.LESS_THAN : Constants.Unicode.LESS_THAN_EQUAL)
                    + upBound.toString();

        } else if (!lowBound.isInfinite() && upBound.isInfinite()) {
            return getVar() + (isLowOpen ? Constants.Unicode.GREATER_THAN : Constants.Unicode.GREATER_THAN_EQUAL)
                    + lowBound.toString();
        }

        String output = lowBound.toString();
        output += isLowOpen ? Constants.Unicode.LESS_THAN : Constants.Unicode.LESS_THAN_EQUAL;
        output += getVar();
        output += isUpOpen ? Constants.Unicode.LESS_THAN : Constants.Unicode.LESS_THAN_EQUAL;
        output += upBound.toString();
        return output;
    }

    @Override
    public String printConsole() {
        String output = lowBound.printConsole();
        output += isLowOpen ? Constants.Unicode.LESS_THAN : Constants.Unicode.LESS_THAN + "=";
        output += getVar();
        output += isUpOpen ? Constants.Unicode.LESS_THAN : Constants.Unicode.LESS_THAN + "=";
        output += upBound.printConsole();

        return output;
    }

    public final IntervalSet toInterval() {
        return new IntervalSet("", this);
    }

    //
    // Get & Set Methods
    //
    public final NumberAbstract getLowBound() {
        return lowBound;
    }

    public final boolean isLowOpen() {
        return isLowOpen;
    }

    public final NumberAbstract getUpBound() {
        return upBound;
    }

    public final boolean isUpOpen() {
        return isUpOpen;
    }

    //
    // Boolean Methods
    //
    @Override
    public boolean checkPredicate(NumberAbstract number) {
        boolean lower = isLowOpen ? lowBound.lessThan(number) : lowBound.lessThanEqual(number);
        boolean upper = isUpOpen ? number.lessThan(upBound) : number.lessThanEqual(upBound);

        return lower && upper;
    }

    @Override
    public final boolean equals(PredicateAbstract other) {
        if (!other.isType(PredicateType.RANGE) || !(getVar().equals(other.getVar()))) {
            return false;
        }

        RangePredicate range = (RangePredicate) other;

        return (lowBound.equals(range.getLowBound()))
                && (upBound.equals(range.getUpBound()))
                && (isLowOpen == range.isLowOpen())
                && (isUpOpen == range.isUpOpen());
    }
}
