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
    private static ArrayList<IntervalSet> unionedIntervals = new ArrayList<IntervalSet>();

    public static final SetAbstract performUnion(IntervalSet... intervals) {
        if (intervals == null || intervals.length == 0) {
            return new NullSet("null");
        } else if (intervals.length == 1) {
            return intervals[0];
        }

        /* Ensure that values are not carried over from previous operations */
        if (!unionedIntervals.isEmpty()) {
            unionedIntervals.clear();
        }

        unionedIntervals.add(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            int j = 0; // for use later in a for loop
            IntervalSet jInterval = unionedIntervals.get(j);
            IntervalSet iInterval = intervals[i];
            String unionString = SetUtils.unionString(jInterval, iInterval);

            // one set encompasses the other, making one redundant
            if (jInterval.isSuperset(iInterval)) {
                continue; // move onto the next interval
            } else if (iInterval.isSuperset(jInterval)) {
                iInterval.setSetName(unionString);
                unionedIntervals.set(j, iInterval);
                continue;
            }

            // [a,b] union [b,c]
            if (SetUtils.isRegionSplit(jInterval, iInterval)) {
                unionedIntervals.set(j, new IntervalSet(unionString, jInterval.getLowBound(), jInterval.isLowOpen(),
                        iInterval.isUpOpen(), iInterval.getUpBound()));
                continue;
            } else if (SetUtils.isRegionSplit(iInterval, jInterval)) {
                unionedIntervals.set(j, new IntervalSet(unionString, iInterval.getLowBound(), iInterval.isLowOpen(),
                        jInterval.isUpOpen(), jInterval.getUpBound()));
                continue;
            }

            // [a,b] union [c, d] where b > c
            if (smthn(jInterval, iInterval)) {
                unionedIntervals.set(j, new IntervalSet(unionString, jInterval.getLowBound(), jInterval.isLowOpen(),
                        iInterval.isUpOpen(), iInterval.getUpBound()));
                continue;
            } else if (smthn(iInterval, jInterval)) {
                unionedIntervals.set(j, new IntervalSet(unionString, iInterval.getLowBound(), iInterval.isLowOpen(),
                        jInterval.isUpOpen(), jInterval.getUpBound()));
                continue;
            }

            // none of the above, so no overlap
            unionedIntervals.add(iInterval);
        }

        return getSet(SetUtils.unionString(intervals));
    }

    //
    // Get & Set Methods
    //
    private static final SetAbstract getSet(String setName) {
        if (unionedIntervals.size() == 1) {
            return unionedIntervals.get(0);
        }

        ArrayList<PredicateAbstract> predicates = new ArrayList<PredicateAbstract>();
        for (IntervalSet i : unionedIntervals) {
            predicates.add(new RangePredicate(Constants.NumberConstants.DEFAULT_VAR, i));
        }

        return new SetBuilder(setName, Constants.NumberConstants.DEFAULT_VAR,
                new OrPredicate(predicates).toPredicateList());
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
}
