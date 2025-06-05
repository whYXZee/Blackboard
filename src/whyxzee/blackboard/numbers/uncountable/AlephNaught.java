package whyxzee.blackboard.numbers.uncountable;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.Uncountable;

/**
 * A package for the smallest form of infinity.
 */
public class AlephNaught extends Uncountable {
    public AlephNaught() {
        super(0);
    }

    @Override
    public final String toString() {
        return Constants.Unicode.ALEPH + Constants.Unicode.SUBSCRIPT_0;
    }

    @Override
    public String printConsole() {
        return Constants.Unicode.ALEPH + "_0";
    }

}
