package whyxzee.blackboard.terms.variables;

import whyxzee.blackboard.equations.EQMultiplication;
import whyxzee.blackboard.equations.MathFunction;
import whyxzee.blackboard.terms.PolynomialTerm;
import whyxzee.blackboard.terms.Term;

/**
 * A type of variable where a function is subsituted into the Variable class.
 * 
 * <p>
 * The functionality of this class has been checked on {@code 5/19/2025} and
 * nothing has changed since.
 */
public class USub extends Variable {
    /* Variables */
    private MathFunction innerFunction;

    public USub(int power, MathFunction innerFunction) {
        super("u", power, VarType.U_SUB_EQ);
        this.innerFunction = innerFunction;

        setShouldChainRule(true);
    }

    public USub(int numPower, int denomPower, MathFunction innerFunction) {
        super("u", numPower, denomPower, VarType.U_SUB_EQ);
        this.innerFunction = innerFunction;

        setShouldChainRule(true);
    }

    //
    // Get and Set Methods
    //

    public final MathFunction getInnerFunction() {
        return innerFunction;
    }

    //
    // Arirthmetic Methods
    //
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

    //
    // Boolean Methods
    //

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
