package whyxzee.blackboard.numbers;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.utils.ArithmeticUtils;

public class ComplexNumber extends NumberAbstract {
    /* Variables */
    private double a;
    private double b;

    public ComplexNumber(double a, double b) {
        super(0, NumType.IMAGINARY);
        this.a = a;
        this.b = b;
        setValue(getModulus());
    }

    @Override
    public final String toString() {
        if (getValue() == 0) {
            return ArithmeticUtils.valueToString(b) + Constants.Unicode.IMAGINARY_NUMBER;
        }

        String output = "";
        if (Math.abs(a) == 1) {
            output += (int) a;
        } else {
            output += ArithmeticUtils.valueToString(a);
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
    public final double getModulus() {
        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

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
    @Override
    public final boolean equals(NumberAbstract other) {
        if (!other.isNumType(getNumType())) {
            return false;
        }

        ComplexNumber num = (ComplexNumber) other;
        return (num.getValue() == getValue()) && (num.getB() == getB());
    }

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
