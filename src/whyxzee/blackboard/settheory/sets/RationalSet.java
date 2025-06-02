package whyxzee.blackboard.settheory.sets;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.numbers.NumberAbstract.NumType;
import whyxzee.blackboard.settheory.SetAbstract;
import whyxzee.blackboard.utils.ArithmeticUtils;

public class RationalSet extends SetAbstract {

    public RationalSet() {
        super(Constants.Unicode.RATIONAL_SET, "", SetType.COMMON);
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
    public boolean inSet(NumberAbstract number) {
        if (number.isNumType(NumType.IMAGINARY)) {
            return false;
        }

        return ArithmeticUtils.isRational(number.getValue()) || ArithmeticUtils.isInteger(number.getValue());
    }

}
