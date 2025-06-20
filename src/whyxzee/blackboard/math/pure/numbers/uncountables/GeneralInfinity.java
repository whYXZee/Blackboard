package whyxzee.blackboard.math.pure.numbers.uncountables;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.pure.numbers.BUncountable;

/**
 * Sizes are as follows:
 * <ul>
 * <li>0: log(infinity)
 * <li>1: infinity
 * <li>2 b^(infinity)
 * <li>3: (infinity)!
 */
public class GeneralInfinity extends BUncountable {

    // #region
    /**
     * Constructor for a general Infinity.
     * 
     * @param isNegative
     */
    public GeneralInfinity(boolean isNegative) {
        super(isNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY, 1, UncountableType.ALEPH,
                Constants.Unicode.INFINITY);
    }

    /**
     * Constructor for a general Infinity.
     * 
     * @param isNegative
     */
    public GeneralInfinity(boolean isNegative, int size) {
        super(isNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY, size, UncountableType.ALEPH,
                Constants.Unicode.INFINITY);
    }
    // #endregion
}
