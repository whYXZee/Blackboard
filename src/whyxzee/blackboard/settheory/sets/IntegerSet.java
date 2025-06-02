package whyxzee.blackboard.settheory.sets;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.settheory.SetAbstract;
import whyxzee.blackboard.utils.ArithmeticUtils;

public class IntegerSet extends SetAbstract {
    public IntegerSet() {
        super(Constants.Unicode.INTEGER_SET, "");
    }

    @Override
    public final String toString() {
        return getSetName() + " = {..., -3, -2, -1, 0, 1, 2, 3, ...}";
    }

    @Override
    public String printConsole() {
        return toString();
    }

    @Override
    public boolean inSet(double value) {
        return ArithmeticUtils.isInteger(value);
    }

}
