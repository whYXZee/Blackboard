package whyxzee.blackboard.settheory.arithmetic;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.settheory.IntervalSet;
import whyxzee.blackboard.settheory.NullSet;
import whyxzee.blackboard.settheory.SetAbstract;
import whyxzee.blackboard.settheory.SetBuilder;
import whyxzee.blackboard.settheory.predicates.OrPredicate;
import whyxzee.blackboard.settheory.predicates.PredicateAbstract;
import whyxzee.blackboard.settheory.predicates.RangePredicate;
import whyxzee.blackboard.utils.BooleanUtils;
import whyxzee.blackboard.utils.SetUtils;

/**
 * A package that unions two intervals together.
 * 
 * <p>
 * The functionality of this class has been checked on {@code 6/7/2025}, and
 * nothing has changed since.
 */
public class UnionBounds {
    /* Variables */
    private static ArrayList<IntervalSet> uIntervals = new ArrayList<IntervalSet>();
    private static ArrayList<RangePredicate> uPredicates = new ArrayList<RangePredicate>();

    public static final SetAbstract performUnion(IntervalSet... intervals) {
        if (intervals == null || intervals.length == 0) {
            return new NullSet("null");
        } else if (intervals.length == 1) {
            return intervals[0];
        }

        /* Ensure that values are not carried over from previous operations */
        if (!uIntervals.isEmpty()) {
            uIntervals.clear();
        }

        uIntervals.add(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            int j = 0; // for use later in a for loop
            IntervalSet jInterval = uIntervals.get(j);
            IntervalSet iInterval = intervals[i];
            String unionString = SetUtils.unionString(jInterval, iInterval);

            // one set encompasses the other, making one redundant
            if (jInterval.isSuperset(iInterval)) {
                continue; // move onto the next interval
            } else if (iInterval.isSuperset(jInterval)) {
                iInterval.setSetName(unionString);
                uIntervals.set(j, iInterval);
                continue;
            }

            // [a,b] union [b,c]
            if (SetUtils.isRegionSplit(jInterval, iInterval)) {
                uIntervals.set(j, new IntervalSet(unionString, jInterval.getLowBound(), jInterval.isLowOpen(),
                        iInterval.isUpOpen(), iInterval.getUpBound()));
                continue;
            } else if (SetUtils.isRegionSplit(iInterval, jInterval)) {
                uIntervals.set(j, new IntervalSet(unionString, iInterval.getLowBound(), iInterval.isLowOpen(),
                        jInterval.isUpOpen(), jInterval.getUpBound()));
                continue;
            }

            // [a,b] union [c, d] where b > c
            if (smthn(jInterval, iInterval)) {
                uIntervals.set(j, new IntervalSet(unionString, jInterval.getLowBound(), jInterval.isLowOpen(),
                        iInterval.isUpOpen(), iInterval.getUpBound()));
                continue;
            } else if (smthn(iInterval, jInterval)) {
                uIntervals.set(j, new IntervalSet(unionString, iInterval.getLowBound(), iInterval.isLowOpen(),
                        jInterval.isUpOpen(), jInterval.getUpBound()));
                continue;
            }

            // none of the above, so no overlap
            uIntervals.add(iInterval);
        }

        return getSetAbstract(SetUtils.unionString(intervals));
    }

    public static final PredicateAbstract performUnion(ArrayList<RangePredicate> predicates) {
        RangePredicate[] arr = new RangePredicate[predicates.size()];
        for (int i = 0; i < predicates.size(); i++) {
            arr[i] = predicates.get(i);
        }
        return performUnion(arr);
    }

    public static final PredicateAbstract performUnion(RangePredicate... predicates) {
        if (predicates == null || predicates.length == 0) {
            return null;
        } else if (predicates.length == 1) {
            return predicates[0];
        }

        if (!uIntervals.isEmpty()) {
            uIntervals.clear();
        }

        uPredicates.add(predicates[0]);
        for (int i = 1; i < predicates.length; i++) {
            int j = 0; // for use later in a for loop
            RangePredicate jRange = uPredicates.get(j);
            RangePredicate iRange = predicates[i];

            // one set encompasses the other, making one redundant
            if (isSuperset(jRange, iRange)) {
                continue; // move onto the next interval
            } else if (isSuperset(iRange, jRange)) {
                uPredicates.set(j, iRange);
                continue;
            }

            // [a,b] union [b,c]
            if (SetUtils.isRegionSplit(jRange, iRange)) {
                uPredicates.set(j,
                        new RangePredicate(jRange.getVar(), jRange.getLowBound(), jRange.isLowOpen(),
                                iRange.isUpOpen(), iRange.getUpBound()));
                continue;
            } else if (SetUtils.isRegionSplit(iRange, jRange)) {
                uPredicates.set(j, new RangePredicate(iRange.getVar(), iRange.getLowBound(), iRange.isLowOpen(),
                        jRange.isUpOpen(), jRange.getUpBound()));
                continue;
            }

            // [a,b] union [c, d] where b > c
            if (smthn(jRange, iRange)) {
                uPredicates.set(j, new RangePredicate(jRange.getVar(), jRange.getLowBound(), jRange.isLowOpen(),
                        iRange.isUpOpen(), iRange.getUpBound()));
                continue;
            } else if (smthn(iRange, jRange)) {
                uPredicates.set(j, new RangePredicate(iRange.getVar(), iRange.getLowBound(), iRange.isLowOpen(),
                        jRange.isUpOpen(), jRange.getUpBound()));
                continue;
            }

            // none of the above, so no overlap
            uPredicates.add(iRange);
        }

        return getPredicates();
    }

    //
    // Get & Set Methods
    //
    private static final SetAbstract getSetAbstract(String setName) {
        if (uIntervals.size() == 1) {
            return uIntervals.get(0);
        }

        ArrayList<PredicateAbstract> predicates = new ArrayList<PredicateAbstract>();
        for (IntervalSet i : uIntervals) {
            predicates.add(new RangePredicate(Constants.NumberConstants.DEFAULT_VAR, i));
        }

        return new SetBuilder(setName, Constants.NumberConstants.DEFAULT_VAR,
                new OrPredicate(predicates).toPredicateList());
    }

    private static final PredicateAbstract getPredicates() {
        if (uPredicates.size() == 1) {
            return uPredicates.get(0);
        }

        return new OrPredicate(new ArrayList<PredicateAbstract>(uPredicates));
    }

    //
    // Boolean Methods
    //
    private static final boolean smthn(IntervalSet a, IntervalSet b) {
        boolean lower, upper;
        lower = a.getLowBound().lessThan(b.getLowBound());
        upper = a.getUpBound().lessThan(b.getUpBound());

        if (!lower || !upper) {
            return false;
        }

        return b.getLowBound().lessThan(a.getUpBound());
    }

    private static final boolean smthn(RangePredicate a, RangePredicate b) {
        boolean lower, upper;
        lower = a.getLowBound().lessThan(b.getLowBound());
        upper = a.getUpBound().lessThan(b.getUpBound());

        if (!lower || !upper) {
            return false;
        }

        return b.getLowBound().lessThan(a.getUpBound());
    }

    private static final boolean isSuperset(RangePredicate a, RangePredicate b) {
        if (boundCheck(a, b)) {
            // this.low < other.low < this.up
            // this.low < other.up < this.up
            return true;
        }

        /* [a,b] union (a,b) or some permutation */
        if (!a.getLowBound().equals(b.getLowBound()) || !a.getUpBound().equals(b.getUpBound())) {
            return false;
        }
        if (BooleanUtils.imply(a.isLowOpen(), b.isLowOpen())
                && BooleanUtils.imply(a.isUpOpen(), b.isUpOpen())) {
            return true;
        }

        return false;
    }

    private static final boolean boundCheck(RangePredicate a, RangePredicate b) {
        // TODO: name "boundCheck()" better
        boolean lower, upper;
        lower = a.getLowBound().lessThan(b.getLowBound()) && b.getLowBound().lessThan(a.getUpBound());
        if (a.getLowBound().isInfinite() && b.getLowBound().isInfinite()) {
            lower = true;
        }

        upper = a.getLowBound().lessThan(b.getUpBound()) && b.getUpBound().lessThan(a.getUpBound());
        if (a.getUpBound().isInfinite() && b.getUpBound().isInfinite()) {
            upper = true;
        }

        return lower && upper;
    }
}
