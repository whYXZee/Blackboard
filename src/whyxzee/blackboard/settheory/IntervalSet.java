package whyxzee.blackboard.settheory;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.numbers.uncountable.Infinity;
import whyxzee.blackboard.settheory.arithmetic.UnionBounds;
import whyxzee.blackboard.settheory.predicates.OrPredicate;
import whyxzee.blackboard.settheory.predicates.PredicateAbstract;
import whyxzee.blackboard.settheory.predicates.RangePredicate;
import whyxzee.blackboard.utils.BooleanUtils;
import whyxzee.blackboard.utils.SetUtils;

/**
 * <p>
 * The functionality of this package has been checked on {@code 6/7/2025} and
 * nothing has changed since.
 */
public class IntervalSet extends SetAbstract {
    /* Variables */
    private NumberAbstract lowBound;
    private boolean isLowOpen;
    private NumberAbstract upBound;
    private boolean isUpOpen;

    /**
     * Creates an interval that ranges from (-infty, infty)
     */
    public IntervalSet(String setName) {
        super(setName, SetType.INTERVAL);
        lowBound = new Infinity(true);
        isLowOpen = true;
        isUpOpen = true;
        upBound = new Infinity(false);
    }

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

        if (upBound.lessThan(lowBound)) {
            this.lowBound = upBound;
            this.isLowOpen = isUpOpen;
            this.isUpOpen = isLowOpen;
            this.upBound = lowBound;
        }
    }

    @Override
    public final String toString() {
        String output = getSetName() + " = ";
        output += isLowOpen ? "(" : "[";
        output += lowBound + "," + upBound;
        output += isUpOpen ? ")" : "]";

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

    public final void setLowBound(NumberAbstract lowBound) {
        this.lowBound = lowBound;
    }

    public final boolean isLowOpen() {
        return isLowOpen;
    }

    public final void setLowOpen(boolean isLowOpen) {
        this.isLowOpen = isLowOpen;
    }

    public final NumberAbstract getUpBound() {
        return upBound;
    }

    public final void setUpBound(NumberAbstract upBound) {
        this.upBound = upBound;
    }

    public final boolean isUpOpen() {
        return isUpOpen;
    }

    public final void setUpOpen(boolean isUpOpen) {
        this.isUpOpen = isUpOpen;
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

        switch (other.getAmbiguousList()) {
            case AMBIGUOUS_LIST:
                predicates.add(((AmbiguousList) other).toPredicate());
                predicates.add(new RangePredicate(Constants.NumberConstants.DEFAULT_VAR, this));
                return new SetBuilder(other.getSetName(), Constants.NumberConstants.DEFAULT_VAR,
                        new OrPredicate(predicates).toPredicateList());

            case BUILDER:
                SetBuilder builder = (SetBuilder) other;
                builder.unionPredicate(new RangePredicate(builder.getVar(), this));
                return builder;

            case DEFINED_LIST:
                return new SetBuilder(SetUtils.unionString(this, other), Constants.NumberConstants.DEFAULT_VAR,
                        SetUtils.UnionHelper.numbersInInterval((DefinedList) other, this));

            case INTERVAL:
                return UnionBounds.performUnion(this, (IntervalSet) other);
            case NULL:
                return this;
            default:
                return null;

        }
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
    public final boolean isSuperset(SetAbstract other) {
        switch (other.getAmbiguousList()) {
            case AMBIGUOUS_LIST:
                break;
            case BUILDER:
                break;
            case DEFINED_LIST:
                break;
            case INTERVAL:
                IntervalSet interval = (IntervalSet) other;
                if (boundCheck(interval)) {
                    // this.low < other.low < this.up
                    // this.low < other.up < this.up
                    return true;
                }

                /* [a,b] union (a,b) or some permutation */
                if (!lowBound.equals(interval.getLowBound()) || !upBound.equals(interval.getUpBound())) {
                    return false;
                }
                if (BooleanUtils.imply(isLowOpen, interval.isLowOpen())
                        && BooleanUtils.imply(isUpOpen, interval.isUpOpen())) {
                    return true;
                }

                return false;
            case NULL:
                return true;
            default:
                return false;
        }
        return false;
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

    private final boolean boundCheck(IntervalSet interval) {
        // TODO: name "boundCheck()" better
        boolean lower, upper;
        lower = lowBound.lessThan(interval.getLowBound()) && interval.getLowBound().lessThan(upBound);
        if (lowBound.isInfinite() && interval.getLowBound().isInfinite()) {
            lower = true;
        }

        upper = lowBound.lessThan(interval.getUpBound()) && interval.getUpBound().lessThan(upBound);
        if (upBound.isInfinite() && interval.getUpBound().isInfinite()) {
            upper = true;
        }

        return lower && upper;
    }
}
