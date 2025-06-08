package whyxzee.blackboard.numbers.uncountable;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.Uncountable;
import whyxzee.blackboard.utils.UnicodeUtils;

/**
 * A package for the smallest form of infinity. Aleph naught represents
 * countable infinity, while higher numbers represented larger uncountable
 * infinities.
 * 
 * <p>
 * The functionality of this class has not been checked.
 */
public class Aleph extends Uncountable {
    /**
     * Creates a new Aleph Naught.
     */
    public Aleph() {
        super(0, InfType.ALEPH);
    }

    public Aleph(boolean isNegative) {
        super(0, InfType.ALEPH, isNegative);
    }

    public Aleph(int size) {
        super(size, InfType.ALEPH);
    }

    public Aleph(int size, boolean isNegative) {
        super(size, InfType.ALEPH, isNegative);
    }

    @Override
    public final String toString() {
        return isNegative() ? "-" : "" + Constants.Unicode.ALEPH + UnicodeUtils.intToSubscript(getSize());
    }

    @Override
    public String printConsole() {
        return (isNegative() ? "Negative " : "") + "Aleph" + "_" + getSize();
    }
}
