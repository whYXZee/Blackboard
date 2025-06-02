package whyxzee.blackboard.numbers;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.Constants.Unicode;
import whyxzee.blackboard.utils.ArithmeticUtils;

public class ComplexNumber {
    /* Variables */
    private double a;
    private double b;

    public ComplexNumber(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public final String toString() {
        if (a == 0) {
            return ArithmeticUtils.valueToString(b) + Constants.Unicode.IMAGINARY_NUMBER;
        }

        String output = "";
        output += ArithmeticUtils.valueToString(a);
        output += (b > 0) ? " + " : " - ";
        output += ArithmeticUtils.valueToString(b > 0 ? b : -b);
        output += Constants.Unicode.IMAGINARY_NUMBER;
        return output;
    }

    //
    // Get & Set Methods
    //
    public final double getA() {
        return a;
    }

    public final void setA(double a) {
        this.a = a;
    }

    public final double getB() {
        return b;
    }

    public final void setB(double b) {
        this.b = b;
    }

    //
    // Boolean Methods
    //
    public final boolean isImaginary() {
        return b != 0;
    }

    public final boolean isComplex() {
        return a != 0 && isImaginary();
    }

}
