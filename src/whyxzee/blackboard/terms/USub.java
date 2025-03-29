package whyxzee.blackboard.terms;

import whyxzee.blackboard.equations.MathFunction;

/**
 * A type of
 */
public class USub extends Variable {
    //
    // Variables
    //
    private MathFunction innerFunction;

    public USub(int power, MathFunction innerFunction) {
        super('u', power);
        this.innerFunction = innerFunction;
    }

    public USub(int numPower, int denomPower, MathFunction innerFunction) {
        super('u', numPower, denomPower);
        this.innerFunction = innerFunction;
    }

    @Override
    public double solve(double value) {
        return Math.pow(innerFunction.solve(value), (double) getNumeratorPower() / getDenominatorPower());
    }

    @Override
    public String toString() {
        if (getNumeratorPower() == 0) {
            return "";
        } else if (getDenominatorPower() == 1) {
            return innerFunction.toString();
        } else {
            return "(" + innerFunction.toString() + ")" + getPowerUnicode();
        }
    }
}
