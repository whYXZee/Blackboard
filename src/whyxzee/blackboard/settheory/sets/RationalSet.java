package whyxzee.blackboard.settheory.sets;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.settheory.SetAbstract;
import whyxzee.blackboard.utils.ArithmeticUtils;

public class RationalSet extends SetAbstract {

    public RationalSet() {
        super(Constants.Unicode.RATIONAL_SET, "");
    }

    @Override
    public final String toString() {
        return getSetName();
    }

    @Override
    public String printConsole() {
        return toString();
    }

    @Override
    public boolean inSet(double value) {
        return ArithmeticUtils.isRational(value) || ArithmeticUtils.isInteger(value);
    }

}
