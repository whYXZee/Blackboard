package whyxzee.blackboard.math.pure.numbers.uncountables;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.pure.numbers.BUncountable;

public class Aleph extends BUncountable {

    /**
     * Constructor for an Aleph Naught.
     * 
     * @param isNegative
     */
    public Aleph(boolean isNegative) {
        super(isNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY, 0, UncountableType.ALEPH,
                Constants.Unicode.ALEPH);
    }

    /**
     * Constructor for an Aleph Naught.
     * 
     * @param isNegative
     */
    public Aleph(boolean isNegative, int size) {
        super(isNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY, size, UncountableType.ALEPH,
                Constants.Unicode.ALEPH);
    }
}
