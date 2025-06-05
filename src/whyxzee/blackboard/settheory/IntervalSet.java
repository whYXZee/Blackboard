package whyxzee.blackboard.settheory;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.settheory.predicates.OrPredicate;
import whyxzee.blackboard.settheory.predicates.PredicateAbstract;
import whyxzee.blackboard.settheory.predicates.RangePredicate;
import whyxzee.blackboard.utils.SetUtils;

/**
 * <p>
 * The functionality of this package has been checkced on {@code 6/3/2025} and
 * the following has changed since:
 * <ul>
 * <li>union()
 */
public class IntervalSet extends SetAbstract {
    /* Variables */
    private NumberAbstract lowBound;
    private boolean isLowOpen;
    private NumberAbstract upBound;
    private boolean isUpOpen;

    public IntervalSet(String setName, RangePredicate range) {
        super(setName, SetType.INTERVAL);
        this.lowBound = range.getLowBound();
        this.isLowOpen = range.isLowOpen();
        this.isUpOpen = range.isUpOpen();
        this.upBound = range.getUpBound();
    }

    public IntervalSet(String setName, NumberAbstract lowBound, boolean isLowOpen, boolean isUpOpen,
            NumberAbstract upBound) {
        super(setName, SetType.INTERVAL);
        this.lowBound = lowBound;
        this.isLowOpen = isLowOpen;
        this.isUpOpen = isUpOpen;
        this.upBound = upBound;
    }

    @Override
    public final String toString() {
        String output = isLowOpen ? "(" : "[";
        output += lowBound + "," + upBound;
        output += isUpOpen ? "(" : "[";

        return output;
    }

    @Override
    public String printConsole() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printConsole'");
    }

    @Override
    public IntervalSet toInterval() {
        return this;
    }

    @Override
    public DefinedList toDefinedList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toDefinedList'");
    }

    @Override
    public SetBuilder toBuilder() {
        throw new UnsupportedOperationException("Unimplemented method 'toDefinedList'");
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
    // Arithmetic Methods
    //
    @Override
    public final NumberAbstract cardinality() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SetAbstract union(SetAbstract other) {
        ArrayList<PredicateAbstract> predicates = new ArrayList<PredicateAbstract>();

        switch (other.getType()) {
            case AMBIGUOUS_LIST:
                predicates.add(((AmbiguousList) other).toPredicate());
                predicates.add(new RangePredicate(Constants.NumberConstants.DEFAULT_VAR, this));
                return new SetBuilder(other.getSetName(), Constants.NumberConstants.DEFAULT_VAR,
                        new OrPredicate(predicates).toPredicateList());

            case BUILDER:
                break;
            case DEFINED_LIST:
                return new SetBuilder(SetUtils.unionString(this, other), Constants.NumberConstants.DEFAULT_VAR,
                        SetUtils.UnionHelper.numbersInInterval((DefinedList) other, this));
            case INTERVAL:
                break;
            case NULL:
                return this;
            default:
                return null;

        }
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'union'");
    }

    @Override
    public SetAbstract disjunction(SetAbstract other) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disjoint'");
    }

    @Override
    public SetAbstract complement(SetAbstract universe) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'complement'");
    }

    //
    // Boolean Methods
    //
    @Override
    public boolean inSet(NumberAbstract number) {
        boolean lower, upper;

        lower = isLowOpen ? lowBound.lessThan(number) : lowBound.lessThanEqual(number);
        upper = isUpOpen ? number.lessThan(upBound) : number.lessThanEqual(upBound);

        return lower && upper;
    }

    @Override
    public boolean equals(SetAbstract other) {
        if (!other.isType(SetType.INTERVAL)) {
            return false;
        }

        IntervalSet interval = (IntervalSet) other;

        return (lowBound.equals(interval.getLowBound()))
                && (upBound.equals(interval.getUpBound()))
                && (isLowOpen == interval.isLowOpen())
                && (isUpOpen == interval.isUpOpen());
    }

}
