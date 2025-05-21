package whyxzee.blackboard.terms;

import whyxzee.blackboard.equations.EQMultiplication;
import whyxzee.blackboard.terms.variables.USub;
import whyxzee.blackboard.terms.variables.Variable;

/**
 * The package for an exponential term, where the term is constructed as
 * num*base^variable.
 * 
 * <p>
 * The package is constructed as an y=b^x equation.
 * 
 * <p>
 * The methods in this class have been checked on {@code 5/20/2025} and nothing
 * has changed.
 */
public class ExponentialTerm extends Term {
    /* Variables */
    private double base;

    /**
     * 
     * @param coefficient the constant in front of the exponent
     * @param var         the variable in the exponent
     * @param base        the base of the exponent
     */
    public ExponentialTerm(double coefficient, Variable var, double base) {
        super(coefficient, var, TermType.EXPONENTIAL);
        this.base = base;
    }

    @Override
    public String toString() {
        /* Decision via Coefficient */
        double coef = getCoefficient();
        if (coef == 0) {
            return "0";
        }

        /* Decision via Function */
        boolean isBaseE = base == Math.E;
        if (coef == 1) {
            return (isBaseE ? "e" : base) + "^(" + getVar().toString() + ")";
        } else {
            return Double.toString(coef) + "(" + (isBaseE ? "e" : base) + ")^(" + getVar().toString() + ")";
        }
    }

    @Override
    public String printConsole() {
        /* Decision via Coefficient */
        double coef = getCoefficient();
        if (coef == 0) {
            return "0";
        }

        /* Decision via Function */
        boolean isBaseE = base == Math.E;
        if (coef == 1) {
            return (isBaseE ? "e" : base) + "^(" + getVar().printConsole() + ")";
        } else {
            return Double.toString(coef) + "(" + (isBaseE ? "e" : base) + ")^(" + getVar().printConsole() + ")";
        }
    }

    //
    // Get and Set Methods
    //
    public double getBase() {
        return base;
    }

    //
    // Arithmetic Methods
    //
    @Override
    public double solve(double value) {
        return getCoefficient() * Math.pow(base, getVar().solve(value));
    }

    @Override
    public Term negate() {
        return new ExponentialTerm(-1 * getCoefficient(), getVar(), base);
    }

    @Override
    public Term derive() {
        /* Initiating variables */
        double coef = getCoefficient();
        Variable variable = getVar().clone();

        /* The coefficient */
        if (base != Math.E) {
            coef *= Math.log(base);
        }

        /* The variable */
        if (variable.needsChainRule()) {
            // chain rule
            EQMultiplication eq = new EQMultiplication(
                    // outer function (b^x)
                    new ExponentialTerm(1, variable, base),

                    // inner function (x)
                    variable.derive());

            return new PolynomialTerm(coef, new USub(eq), 1);
        } else {
            // no chain rule
            return new ExponentialTerm(coef, variable, base);
        }
    }

    @Override
    public double limInfSolve() {
        /* Without respect to the variable */

        /* Number */
        double coef = getCoefficient();
        if (coef == 0) {
            return 0;
        }
        boolean isNumberNegative = coef < 0;

        /* Function */
        if (base < 1) {
            // -1 < b < 1 so converging
            return isNumberNegative ? -0 : 0;
        } else if (base == 1) {
            // b = 1
            return coef;
        } else if (base >= -1) {
            // negative base less than than or equal to -1
            // alternating
            return Double.NaN;
        } else {
            return isNumberNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        }
    }

    public double limNegInfSolve() {
        /* Number */
        double coef = getCoefficient();
        if (coef == 0) {
            return 0;
        }
        boolean isNumberNegative = coef < 0;

        if (base < 1) {
            // -1 < b < 1
            return isNumberNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        } else if (base == 1) {
            // b = 1
            return coef;
        } else if (base > -1) {
            // negative base less than to -1
            // converging alternating
            return isNumberNegative ? -0 : 0;
        } else {
            // negative base greater than or equal to -1
            return Double.NaN;
        }
    }
}
