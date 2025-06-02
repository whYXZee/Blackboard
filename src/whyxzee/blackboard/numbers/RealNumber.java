package whyxzee.blackboard.numbers;

import whyxzee.blackboard.utils.ArithmeticUtils;

public class RealNumber extends NumberAbstract {

    public RealNumber(double value) {
        super(value, NumType.REAL);
    }

    @Override
    public final String toString() {
        return ArithmeticUtils.valueToString(getValue());
    }

    @Override
    public final String printConsole() {
        return printConsole();
    }

    //
    // Boolean Methods
    //

    @Override
    public final boolean isImaginary() {
        return false;
    }

    @Override
    public final boolean isComplex() {
        return false;
    }

    @Override
    public final boolean isRational() {
        return ArithmeticUtils.isRational(getValue());
    }

    @Override
    public final boolean isInteger() {
        return ArithmeticUtils.isInteger(getValue());
    }

}
