package whyxzee.blackboard.math.pure.numbers.uncountables;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.pure.numbers.BUncountable;

public class Infinitesimal extends BUncountable {
    // #region Constructors
    /**
     * Constructor for an Infinitesimal number with String d<b>var</b>.
     */
    public Infinitesimal(String var) {
        super(Constants.Number.INFINITESIMAL_VAL, 0, UncountableType.INFINITESIMAL,
                Constants.Unicode.INFINITESIMAL + var);
    }

    public Infinitesimal(String var, boolean isPartial) {
        super(Constants.Number.INFINITESIMAL_VAL, 0, UncountableType.INFINITESIMAL,
                ((isPartial) ? Constants.Unicode.LOWERCASE_DELTA : Constants.Unicode.INFINITESIMAL) + var);
    }
    // #endregion
}
