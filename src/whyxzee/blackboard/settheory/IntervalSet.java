package whyxzee.blackboard.settheory;

import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.settheory.predicates.InequalityPredicate;
import whyxzee.blackboard.settheory.predicates.PredicateAbstract;
import whyxzee.blackboard.settheory.predicates.RangePredicate;
import whyxzee.blackboard.settheory.predicates.InequalityPredicate.InequalityType;

public class IntervalSet extends SetAbstract {
    /* Variables */
    private Bound lowerBound;
    private Bound upperBound;

    public IntervalSet(String setName, String var, Bound lowerBound, Bound upperBound) {
        super(setName, var, SetType.INTERVAL);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.lowerBound.setLower(true);
    }

    @Override
    public final String toString() {
        return getVar() + " = " + lowerBound.toString() + "," + upperBound.toString();
    }

    @Override
    public String printConsole() {
        return getVar() + " = " + lowerBound.printConsole() + "," + upperBound.printConsole();
    }

    public final PredicateAbstract toPredicate() {
        if (lowerBound.isInfinite() && upperBound.isInfinite()) {
            // the range/inequality predicate is redundant
            return null;
        }

        if (lowerBound.isInfinite()) {
            return new InequalityPredicate(getVar(),
                    upperBound.isOpen() ? InequalityType.LESS_THAN : InequalityType.LESS_THAN_EQUAL,
                    upperBound.getValue());
        } else if (upperBound.isInfinite()) {
            return new InequalityPredicate(getVar(),
                    lowerBound.isOpen() ? InequalityType.GREATER_THAN : InequalityType.GREATER_THAN_EQUAL,
                    lowerBound.getValue());
        }

        return new RangePredicate(lowerBound, getVar(), upperBound);
    }

    //
    // Get & Set Methods
    //
    public final Bound getLowerBound() {
        return lowerBound;
    }

    public final Bound getUpperBound() {
        return upperBound;
    }

    //
    // Arithmetic Methods (Object-oriented)
    //
    @Override
    public SetAbstract union(SetAbstract other) {
        if (!other.isSetType(SetType.INTERVAL)) {
            return null;
        }

        IntervalSet interval = (IntervalSet) other;
        if (this.infiniteRange() || interval.infiniteRange()) {
            return new IntervalSet("", getVar(), Bound.negInfBound, Bound.posInfBound);
        }

        IntervalSet union = unionOne(this, interval);
        if (union != null) {
            return union;
        }

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'union'");
    }

    //
    // Arithmetic Methods (Static)
    //
    /**
     * Union One is where the inequalities are deemed redudnant, as their union
     * reachs from (-infty, infty). Returns a non-null value iff:
     * <ul>
     * <li>one of the lower bounds is infinite, and the other upper bound is
     * infinite.
     * <li>a comparison between the lower bounds and a comparison between the upper
     * bounds reveal that they are both the same. (meaning L1 < L2 and U1 < U2 are
     * true, or L1 > L2 and U1 > U2 are true)
     * <li>the other lower bound < upper bound of another
     * 
     * @return {@code null} if union one criteria is not met.
     */
    public static final IntervalSet unionOne(IntervalSet one, IntervalSet two) {
        /* Ensuring that one is infinite */
        if ((!one.getLowerBound().isInfinite() && !two.lowerBound.isInfinite()) ||
                (!one.getUpperBound().isInfinite() && !two.upperBound.isInfinite())) {
            return null;
        }

        /* Comparing bounds */
        Bound lOne = one.getLowerBound();
        Bound uOne = one.getUpperBound();
        Bound lTwo = two.getLowerBound();
        Bound uTwo = two.getUpperBound();

        int lCompare = lOne.compareTo(lTwo);
        if (lCompare != uOne.compareTo(uTwo)) {
            return null;
        }

        if (lCompare == 1) {
            return (lOne.compareTo(uTwo) == -1) ? new IntervalSet("", one.getVar(), lTwo, uOne) : null;
        } else if (lCompare == -1) {
            return (lTwo.compareTo(uOne) == -1) ? new IntervalSet("", one.getVar(), lOne, uTwo) : null;
        } else {
            return one;
        }
    }

    //
    // Boolean Methods
    //
    @Override
    public boolean inSet(NumberAbstract number) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'inSet'");
    }

    @Override
    public boolean equals(SetAbstract other) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'equals'");
    }

    public boolean infiniteRange() {
        return lowerBound.isInfinite() && upperBound.isInfinite();
    }
}
