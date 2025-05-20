package whyxzee.blackboard.terms;

import whyxzee.blackboard.equations.EQMultiplication;
import whyxzee.blackboard.equations.EQSequence;
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
 * The methods in this class has been checked on {@code 5/20/2025} and the
 * following has changed:
 * <ul>
 * <li>derive()
 */
public class LogarithmicTerm extends Term {
    /* Variables */
    private double base;

    public LogarithmicTerm(double num, Variable var, double base) {
        super(num, var, TermType.LOGARITHMIC);
        this.base = base;
    }

    @Override
    public String toString() {
        /* Initializing variables */
        double number = getNum();

        if (base == Math.E) {
            // natural log
            if (number == 0) {
                return "0";
            } else if (number == 1) {
                return "ln(" + getVar().toString() + ")";
            } else {
                return Double.toString(getNum()) + "ln(" + getVar().toString() + ")";
            }
        } else {
            // base shouldn't be int
            if (number == 0) {
                return "0";
            } else if (number == 1) {
                if (ArithmeticUtils.isInteger(base)) {
                    // integer base
                    return "log" + UnicodeUtils.intToSubscript((int) base) + "(" + getVar().toString() + ")";
                } else {
                    return "log" + UnicodeUtils.doubleToSubscript(base) + "(" + getVar().toString() + ")";
                }
            } else {
                if (ArithmeticUtils.isInteger(base)) {
                    // integer base
                    return Double.toString(getNum()) + "log" + UnicodeUtils.intToSubscript((int) base) + "("
                            + getVar().toString() + ")";
                } else {
                    return Double.toString(getNum()) + "log" + UnicodeUtils.doubleToSubscript(base) + "("
                            + getVar().toString() + ")";
                }
            }
        }
    }

    @Override
    public String printConsole() {
        /* Initializing variables */
        double number = getNum();

        if (base == Math.E) {
            // natural log
            if (number == 0) {
                return "0";
            } else if (number == 1) {
                return "ln(" + getVar().printConsole() + ")";
            } else {
                return Double.toString(getNum()) + "ln(" + getVar().printConsole() + ")";
            }
        } else {
            if (number == 0) {
                return "0";
            } else if (number == 1) {
                if (ArithmeticUtils.isInteger(base)) {
                    return "log" + UnicodeUtils.intToSubscript((int) base) + "(" + getVar().printConsole() + ")";
                } else {
                    return "log" + UnicodeUtils.doubleToSubscript(base) + "(" + getVar().printConsole() + ")";
                }
            } else {
                return Double.toString(getNum()) + "log_" + Double.toString(base) + "("
                        + getVar().printConsole() + ")";
            }
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
        double number = getNum();
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
        return new LogarithmicTerm(-1 * getNum(), getVar(), base);
    }

    @Override
    public Term derive() {
        /* Initiating variables */
        double number = getNum();
        Variable variable = getVar().clone();

        /* Derivative Algorithm */
        if (base == Math.E) {
            if (variable.needsChainRule()) {
                // chain rule
                EQMultiplication eq = new EQMultiplication(
                        // outer function (log_b (x))
                        new PolynomialTerm(number, variable.exponentiate(-1)),

                        // inner function (x)
                        variable.derive());
                return new PolynomialTerm(number, new USub(1, eq));
            } else {
                // no chain rule
                return new PolynomialTerm(number, variable.exponentiate(-1));
            }
        } else {
            // change of bases formula and then expansion of logarithmics
            EQSequence innerEq = new EQSequence(
                    new LogarithmicTerm(1, variable, Math.E),
                    new PolynomialTerm(-Math.log(base)));
            return new PolynomialTerm(number, new USub(1, innerEq)).derive();

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
        return Double.NaN;
    }

}
