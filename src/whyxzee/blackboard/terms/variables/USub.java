package whyxzee.blackboard.terms.variables;

import whyxzee.blackboard.equations.EQMultiplication;
import whyxzee.blackboard.equations.MathFunction;
import whyxzee.blackboard.terms.PolynomialTerm;
import whyxzee.blackboard.terms.Term;

/**
 * A type of variable where a function is subsituted into the Variable class.
 * 
 * The functionality of this class has not been checked.
 */
public class USub extends Variable {
    /* Variables */
    private MathFunction innerFunction;

    public USub(int power, MathFunction innerFunction) {
        super("u", power);
        this.innerFunction = innerFunction;

        setShouldChainRule(true);
    }

    public USub(int numPower, int denomPower, MathFunction innerFunction) {
        super("u", numPower, denomPower);
        this.innerFunction = innerFunction;

        setShouldChainRule(true);
    }

    @Override
    public double solve(double value) {
        return Math.pow(innerFunction.solve(value), (double) getNumeratorPower() / getDenominatorPower());
    }

    @Override
    public Term derive() {
        /* Variables */
        int nPower = getNumeratorPower(); // numerator power
        int dPower = getDenominatorPower(); // denominator power

        /* Chain rule */
        EQMultiplication eq = new EQMultiplication(
                // Outer function (u^n) - functional
                new PolynomialTerm((double) nPower / dPower, setPower(nPower - dPower, dPower)),

                // Inner function (u)
                new PolynomialTerm(1, new USub(1, innerFunction.derive())));
        return new PolynomialTerm(1, new USub(1, eq));
    }

    @Override
    public String toString() {
        if (getNumeratorPower() == 0) {
            return "";
        } else if (getNumeratorPower() == 1 && getDenominatorPower() == 1) {
            return innerFunction.toString();
        } else {
            return "(" + innerFunction.toString() + ")" + getPowerUnicode();
        }
    }
}
