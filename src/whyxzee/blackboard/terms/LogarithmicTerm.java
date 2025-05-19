package whyxzee.blackboard.terms;

import whyxzee.blackboard.equations.EQMultiplication;
import whyxzee.blackboard.equations.EQSequence;
import whyxzee.blackboard.terms.variables.USub;
import whyxzee.blackboard.terms.variables.Variable;
import whyxzee.blackboard.utils.UnicodeUtils;

public class LogarithmicTerm extends Term {
    /* Variables */
    private double base;

    public LogarithmicTerm(double num, Variable var, double base) {
        super(num, var, TermType.LOGARITHMIC);
        this.base = base;
    }

    @Override
    public String toString() {
        if (base == Math.E) {
            // natural log
            return Double.toString(getNum()) + "ln(" + getVar().toString() + ")";
        } else {
            // base shouldn't be int
            return Double.toString(getNum()) + "log" + UnicodeUtils.intToSubscript((int) base) + "("
                    + getVar().toString() + ")";
        }
    }

    @Override
    public String printConsole() {
        if (base == Math.E) {
            // natural log
            return Double.toString(getNum()) + "ln(" + getVar().toString() + ")";
        } else {
            return Double.toString(getNum()) + "log_" + base + "(" + getVar().toString() + ")";
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
            if (variable.getShouldChainRule()) {
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
        if (getNum() > 0) {
            // positive number
            return Double.POSITIVE_INFINITY;
        } else {
            // negative number
            return Double.NEGATIVE_INFINITY;
        }
    }

}
