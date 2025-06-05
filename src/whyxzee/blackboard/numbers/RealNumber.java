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
    public String printConsole() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printConsole'");
    }

    //
    // Boolean Methods
    //
    @Override
    public final boolean isInteger() {
        return ArithmeticUtils.isInteger(getValue());
    }

    @Override
    public final boolean isRational() {
        if (ArithmeticUtils.isInteger(getValue())) {
            return true;
        }

        return ArithmeticUtils.isRational(getValue());
    }

    @Override
    public final boolean isReal() {
        return true;
    }

    @Override
    public final boolean isImaginary() {
        return false;
    }

    @Override
    public final boolean isComplex() {
        return true;
    }

}
