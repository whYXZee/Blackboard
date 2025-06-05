package whyxzee.blackboard.settheory.arithmetic;

import whyxzee.blackboard.settheory.IntervalSet;
import whyxzee.blackboard.settheory.NullSet;
import whyxzee.blackboard.settheory.SetAbstract;

public class UnionBounds {
    /* Variables */

    public static final SetAbstract performUnion(IntervalSet... interval) {
        if (interval.length == 0) {
            return new NullSet("null");
        }

        return null;
    }

}
