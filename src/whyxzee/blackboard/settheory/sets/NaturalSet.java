package whyxzee.blackboard.settheory.sets;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.settheory.SetAbstract;
import whyxzee.blackboard.utils.ArithmeticUtils;

public class NaturalSet extends SetAbstract {

    public NaturalSet() {
        super(Constants.Unicode.NATURAL_SET, "");
    }

    @Override
    public final String toString() {
        return getSetName() + " = {0, 1, 2, 3, ...}";
    }

    @Override
    public String printConsole() {
        return toString();
    }

    @Override
    public boolean inSet(double value) {
        return ArithmeticUtils.isInteger(value) && value > 0;
    }

}
