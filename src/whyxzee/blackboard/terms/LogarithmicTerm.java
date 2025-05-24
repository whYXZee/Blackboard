package whyxzee.blackboard.terms;

import whyxzee.blackboard.equations.EQMultiplication;
import whyxzee.blackboard.terms.variables.USub;
import whyxzee.blackboard.terms.variables.Variable;
import whyxzee.blackboard.utils.ArithmeticUtils;
import whyxzee.blackboard.utils.UnicodeUtils;

/**
 * The package for a logarithmic term, where the term is constructed as
 * log_b(variable)
 * 
 * <p>
 * The package is constructed as a y=log_b(x) equation.
 * 
 * <p>
 * The methods in this class has been checked on {@code 5/20/2025} and nothing
 * has changed
 */
public class LogarithmicTerm extends Term {
    /* Variables */
    private double base;

    public LogarithmicTerm(double coefficient, Variable var, double base) {
        super(coefficient, var, TermType.LOGARITHMIC);
        this.base = base;
    }

    @Override
    public String toString() {
        /* Initializing variables */
        double coef = getCoefficient();

        if (base == Math.E) {
            // natural log

            /* Coefficient */
            if (coef == 0) {
                return "0";
            } else if (coef == 1) {
                return "ln(" + getVar().toString() + ")";
            } else {
                return Double.toString(coef) + "ln(" + getVar().toString() + ")";
            }
        } else {
            // base shouldn't be int
            if (coef == 0) {
                return "0";
            }

            /* Base */
            boolean hasIntegerBase = ArithmeticUtils.isInteger(base);
            if (coef == 1) {
                return "log" + (hasIntegerBase ? UnicodeUtils.intToSubscript((int) base)
                        : UnicodeUtils.doubleToSubscript(base)) + "(" + getVar().toString() + ")";
            } else {
                return Double.toString(coef) + "log" + (hasIntegerBase ? UnicodeUtils.intToSubscript((int) base)
                        : UnicodeUtils.doubleToSubscript(base)) + "(" + getVar().toString() + ")";
            }
        }
    }

    @Override
    public String printConsole() {
        /* Initializing variables */
        double coef = getCoefficient();

        if (base == Math.E) {
            // natural log

            /* Coefficient */
            if (coef == 0) {
                return "0";
            } else if (coef == 1) {
                return "ln(" + getVar().toString() + ")";
            } else {
                return Double.toString(coef) + "ln(" + getVar().toString() + ")";
            }
        } else {
            // numeric base

            /* Coefficient */
            if (coef == 0) {
                return "0";
            } else if (coef == 1) {

                return "log_" + base + "(" + getVar().toString() + ")";
            } else {
                return Double.toString(coef) + "log_" + base + "(" + getVar().toString() + ")";
            }
        }
    }

    //
    // Get and Set Methods
    //
    @Override
    public final double getBase() {
        return base;
    }

    @Override
    public final void setBase(double base) {
        this.base = base;
    }

    //
    // Arithmetic Methods
    //
    @Override
    public double solve(double value) {
        /* Initializing variables */
        double number = getCoefficient();

        /* Decision based on Base */
        if (base == 10) {
            // log10 operation
            return number * Math.log10(getVar().solve(value));
        } else if (base == Math.E) {
            // ln operation
            return number * Math.log(getVar().solve(value));
        } else {
            // change of base formula
            return number * (Math.log(getVar().solve(value)) / Math.log(base));
        }
    }

    @Override
    public Term negate() {
        return new LogarithmicTerm(-1 * getCoefficient(), getVar(), base);
    }

    @Override
    public Term derive() {
        /* Initializing terms */
        double coef = getCoefficient();
        Variable variable = getVar();

        if (getVar().needsChainRule()) {
            /* Chain rule */
            if (base == Math.E) {
                EQMultiplication eq = new EQMultiplication(
                        new PolynomialTerm(1, variable, -1),
                        variable.derive());
                return new PolynomialTerm(coef, new USub(eq), 1);
            } else {
                /* base is not e */
                return new LogarithmicTerm(coef / Math.log(base), variable, Math.E).derive();
            }
        } else {
            /* No Chain rule */

            if (base == Math.E) {
                /* base is e */
                return new PolynomialTerm(getCoefficient(), variable, -1);
            } else {
                /* base is not e */
                return new LogarithmicTerm(coef / Math.log(base), variable, Math.E).derive();

            }
        }
    }

    @Override
    public double limInfSolve() {
        /* Number */
        double number = getCoefficient();
        if (number == 0) {
            return 0;
        }
        boolean isNumberNegative = number < 0;

        /* Function */
        return isNumberNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
    }

    @Override
    public double limNegInfSolve() {
        return Double.NaN;
    }

}
