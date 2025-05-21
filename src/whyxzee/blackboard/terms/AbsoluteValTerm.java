package whyxzee.blackboard.terms;

import whyxzee.blackboard.equations.EQMultiplication;
import whyxzee.blackboard.terms.variables.USub;
import whyxzee.blackboard.terms.variables.Variable;

/**
 * The package for an absolute value term. The term is a*|x|.
 * 
 * <p>
 * The package is constructed as an y=|x| equation.
 * 
 * <p>
 * The functionality of this class has been checked on {@code 5/20/2025} and
 * nothing has changed since.
 */
public class AbsoluteValTerm extends Term {

    public AbsoluteValTerm(double coefficient, Variable var) {
        super(coefficient, var, TermType.ABSOLUTE_VALUE);
    }

    @Override
    public String printConsole() {
        /* Coefficient */
        double coef = getCoefficient();
        if (coef == 0) {
            return "0";
        } else if (coef == 1) {
            return "|" + getVar().printConsole() + "|";
        } else {
            return Double.toString(getCoefficient()) + "|" + getVar().printConsole() + "|";
        }
    }

    @Override
    public String toString() {
        /* Coefficient */
        double coef = getCoefficient();
        if (coef == 0) {
            return "0";
        } else if (coef == 1) {
            return "|" + getVar().toString() + "|";
        } else {
            return Double.toString(getCoefficient()) + "|" + getVar().toString() + "|";
        }
    }

    @Override
    public double solve(double value) {
        return getCoefficient() * Math.abs(getVar().solve(value));
    }

    @Override
    public Term negate() {
        return new AbsoluteValTerm(-1 * getCoefficient(), getVar());
    }

    @Override
    public Term derive() {
        /* Initializing variables */
        Variable variable = getVar().clone();

        if (variable.needsChainRule()) {
            EQMultiplication eq = new EQMultiplication(
                    variable.derive(),
                    new SignumTerm(1, variable));
            return new PolynomialTerm(getCoefficient(), new USub(eq), 1);
        } else {
            return new SignumTerm(getCoefficient(), variable);
        }
    }

    @Override
    public double limInfSolve() {
        /* Number */
        double coef = getCoefficient();
        if (coef == 0) {
            return 0;
        }
        boolean isNumberNegative = coef < 0;

        /* Function */
        return isNumberNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
    }

    @Override
    public double limNegInfSolve() {
        /* Number */
        double number = getCoefficient();
        if (number == 0) {
            return 0;
        }
        boolean isNumberNegative = number < 0;

        /* Function */
        return isNumberNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
    }

}
