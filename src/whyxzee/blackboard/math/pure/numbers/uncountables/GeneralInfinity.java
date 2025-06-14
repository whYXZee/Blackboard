package whyxzee.blackboard.math.pure.numbers.uncountables;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.pure.numbers.BUncountable;

public class GeneralInfinity extends BUncountable {

    /**
     * Constructor for an Aleph Naught.
     * 
     * @param isNegative
     */
    public GeneralInfinity(boolean isNegative) {
        super(isNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY, 0, UncountableType.ALEPH,
                Constants.Unicode.INFINITY);
    }

    /**
     * Constructor for an Aleph Naught.
     * 
     * @param isNegative
     */
    public GeneralInfinity(boolean isNegative, int size) {
        super(isNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY, size, UncountableType.ALEPH,
                Constants.Unicode.INFINITY);
    }
}
