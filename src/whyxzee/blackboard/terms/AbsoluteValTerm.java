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
 * The functionality of this class has not been checked.
 */
public class AbsoluteValTerm extends Term {

    public AbsoluteValTerm(double num, Variable var) {
        super(num, var, TermType.ABSOLUTE_VALUE);
    }

    @Override
    public String printConsole() {
        return Double.toString(getNum()) + "|" + getVar().printConsole() + "|";
    }

    @Override
    public String toString() {
        /* Initializing variables */
        double number = getNum();
        if (number == 0) {
            return "";
        } else if (number == 1) {
            return "|" + getVar().toString() + "|";
        } else {
            return Double.toString(getNum()) + "|" + getVar().toString() + "|";
        }
    }

    @Override
    public double solve(double value) {
        return getNum() * Math.abs(getVar().solve(value));
    }

    @Override
    public Term negate() {
        return new AbsoluteValTerm(-1 * getNum(), getVar());
    }

    @Override
    public Term derive() {
        /* Initializing variables */
        Variable variable = getVar().clone();

        if (variable.needsChainRule()) {
            EQMultiplication eq = new EQMultiplication(
                    variable.derive(),
                    new SignumTerm(1, variable));
            return new PolynomialTerm(getNum(), new USub(1, eq));
        } else {
            return new SignumTerm(getNum(), variable);
        }
    }

    @Override
    public double limInfSolve() {
        /* Number */
        double number = getNum();
        if (number == 0) {
            return 0;
        }
        boolean isNumberNegative = number < 0;

        /* Function */
        return isNumberNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
    }

    @Override
    public double limNegInfSolve() {
        /* Number */
        double number = getNum();
        if (number == 0) {
            return 0;
        }
        boolean isNumberNegative = number < 0;

        /* Function */
        return isNumberNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
    }

}
