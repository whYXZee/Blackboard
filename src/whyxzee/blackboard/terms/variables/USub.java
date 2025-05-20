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
    private Term innerTerm;

    public USub(int power, MathFunction innerFunction) {
        super("u", power, VarType.U_SUB_EQ);
        this.innerFunction = innerFunction;
    }

    public USub(int numPower, int denomPower, MathFunction innerFunction) {
        super("u", numPower, denomPower, VarType.U_SUB_EQ);
        this.innerFunction = innerFunction;
    }

    public USub(int power, Term innerTerm) {
        super("u", power, VarType.U_SUB_TERM);
        this.innerTerm = innerTerm;
    }

    public USub(int numPower, int denomPower, Term innerTerm) {
        super("u", numPower, denomPower, VarType.U_SUB_TERM);
        this.innerTerm = innerTerm;
    }

    @Override
    public String printConsole() {
        switch (getVarType()) {
            case U_SUB_EQ:
                if (getNumeratorPower() == 0) {
                    return "";
                } else if (getNumeratorPower() == 1 && getDenominatorPower() == 1) {
                    return innerFunction.printConsole();
                } else {
                    return "(" + innerFunction.printConsole() + ")^(" + getNumeratorPower() + "/"
                            + getDenominatorPower() + ")";
                }
            case U_SUB_TERM:
                if (getNumeratorPower() == 0) {
                    return "";
                } else if (getNumeratorPower() == 1 && getDenominatorPower() == 1) {
                    return innerTerm.printConsole();
                } else {
                    return "(" + innerTerm.printConsole() + ")^(" + getNumeratorPower() + "/" + getDenominatorPower()
                            + ")";
                }
            default:
                return "";
        }
    }

    @Override
    public String toString() {
        switch (getVarType()) {
            case U_SUB_EQ:
                if (getNumeratorPower() == 0) {
                    return "";
                } else if (getNumeratorPower() == 1 && getDenominatorPower() == 1) {
                    return innerFunction.toString();
                } else {
                    return "(" + innerFunction.toString() + ")" + getPowerUnicode();
                }
            case U_SUB_TERM:
                if (getNumeratorPower() == 0) {
                    return "";
                } else if (getNumeratorPower() == 1 && getDenominatorPower() == 1) {
                    return innerTerm.toString();
                } else {
                    return "(" + innerTerm.toString() + ")" + getPowerUnicode();
                }
            default:
                return "";
        }
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
        switch (getVarType()) {
            case U_SUB_EQ:
                return Math.pow(innerFunction.solve(value), (double) getNumeratorPower() / getDenominatorPower());
            case U_SUB_TERM:
                return Math.pow(innerTerm.solve(value), (double) getNumeratorPower() / getDenominatorPower());
            default:
                return 0;
        }
    }

    @Override
    public Term derive() {
        /* Variables */
        int nPower = getNumeratorPower(); // numerator power
        int dPower = getDenominatorPower(); // denominator power

        /* Chain rule */
        EQMultiplication eq;
        switch (getVarType()) {
            case U_SUB_EQ:
                /* Chain rule */
                eq = new EQMultiplication(
                        // Outer function (u^n)
                        new PolynomialTerm((double) nPower / dPower, setPower(nPower - dPower, dPower)),

                        // Inner function (u)
                        new PolynomialTerm(1, new USub(1, innerFunction.derive())));
                return new PolynomialTerm(1, new USub(1, eq));
            case U_SUB_TERM:
                /* Chain rule */
                eq = new EQMultiplication(
                        // Outer function (u^n)
                        new PolynomialTerm((double) nPower / dPower, setPower(nPower - dPower, dPower)),

                        // Inner function (u)
                        new PolynomialTerm(1, new USub(1, innerTerm.derive())));
                return new PolynomialTerm(1, new USub(1, eq));
            default:
                return null;
        }

    }

    //
    // Boolean Methods
    //

    @Override
    public boolean needsChainRule() {
        return true;
    }
}
