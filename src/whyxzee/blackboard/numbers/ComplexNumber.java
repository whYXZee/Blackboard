package whyxzee.blackboard.numbers;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.utils.ArithmeticUtils;

public class ComplexNumber extends NumberAbstract {
    /* Variables */
    private double b;

    public ComplexNumber(double a, double b) {
        super(a, NumType.IMAGINARY);
        this.b = b;
    }

    @Override
    public final String toString() {
        if (getValue() == 0) {
            return ArithmeticUtils.valueToString(b) + Constants.Unicode.IMAGINARY_NUMBER;
        }

        String output = "";
        if (Math.abs(getValue()) == 1) {
            output += (int) getValue();
        } else {
            output += ArithmeticUtils.valueToString(getValue());
        }
        output += (b > 0) ? " + " : " - ";
        output += ArithmeticUtils.valueToString(b > 0 ? b : -b);
        output += Constants.Unicode.IMAGINARY_NUMBER;
        return output;
    }

    @Override
    public final String printConsole() {
        return toString();
    }

    //
    // Get & Set Methods
    //
    public final double getB() {
        return b;
    }

    public final void setB(double b) {
        this.b = b;
    }

    //
    // Boolean Methods
    //
    @Override
    public final boolean isImaginary() {
        return b != 0;
    }

    @Override
    public final boolean isComplex() {
        return getValue() != 0 && isImaginary();
    }

    @Override
    public final boolean isRational() {
        return false;
    }

    @Override
    public final boolean isInteger() {
        return false;
    }

}
