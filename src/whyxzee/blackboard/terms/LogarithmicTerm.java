package whyxzee.blackboard.terms;

import whyxzee.blackboard.equations.MultiplicativeEQ;
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
 * <li>equals()
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
        double coef = getCoef();
        if (coef == 0) {
            return "0";
        }

        if (base == Math.E) {
            // natural log

            /* Coefficient */
            if (coef == 1) {
                return "ln(" + getVar().toString() + ")";
            } else {
                return Double.toString(coef) + "ln(" + getVar().toString() + ")";
            }
        } else {
            // base shouldn't be int

            /* Base */
            String baseSubscript;
            boolean hasIntegerBase = ArithmeticUtils.isInteger(base);
            if (hasIntegerBase) {
                baseSubscript = UnicodeUtils.intToSubscript((int) base);
            } else {
                baseSubscript = UnicodeUtils.doubleToSubscript(base);
            }

            if (coef == 1) {
                return "log" + baseSubscript + "(" + getVar().toString() + ")";
            } else {
                return Double.toString(coef) + "log" + baseSubscript + "(" + getVar().toString() + ")";
            }
        }
    }

    @Override
    public String printConsole() {
        /* Coefficient */
        double coef = getCoef();
        if (coef == 0) {
            return "0";
        }

        if (base == Math.E) {
            // natural log
            if (coef == 1) {
                return "ln(" + getVar().toString() + ")";
            } else {
                return Double.toString(coef) + "ln(" + getVar().toString() + ")";
            }
        } else {
            // numeric base
            if (coef == 1) {
                return "log_" + base + "(" + getVar().toString() + ")";
            } else {
                return Double.toString(coef) + "log_" + base + "(" + getVar().toString() + ")";
            }
        }
    }

    //
    // Get and Set Methods
    //
    public final double getBase() {
        return base;
    }

    public final void setBase(double base) {
        this.base = base;
    }

    //
    // Arithmetic Methods (Object-related)
    //
    @Override
    public double solve(double value) {
        /* Initializing variables */
        double number = getCoef();

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
        return new LogarithmicTerm(-1 * getCoef(), getVar(), base);
    }

    @Override
    public Term derive() {
        /* Initializing terms */
        double coef = getCoef();
        Variable variable = getVar();

        if (getVar().needsChainRule()) {
            // Chain rule
            if (base == Math.E) {
                MultiplicativeEQ eq = new MultiplicativeEQ(
                        new PowerTerm(1, variable, -1),
                        variable.derive());
                return new PowerTerm(coef, new USub(eq), 1);
            } else {
                /* base is not e */
                return new LogarithmicTerm(coef / Math.log(base), variable, Math.E).derive();
            }
        } else {
            // No chain rule
            if (base == Math.E) {
                /* base is e */
                return new PowerTerm(getCoef(), variable, -1);
            } else {
                /* base is not e */
                return new LogarithmicTerm(coef / Math.log(base), variable, Math.E).derive();

            }
        }
    }

    @Override
    public double limInfSolve() {
        /* Number */
        double number = getCoef();
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

    //
    // Boolean Methods
    //
    @Override
    public boolean similarTo(Term term) {
        switch (term.getTermType()) {
            case LOGARITHMIC:
                LogarithmicTerm logTerm = (LogarithmicTerm) term;
                return (logTerm.getBase() == base) && (getVar().equals(logTerm.getVar()));
            default:
                return false;
        }
    }

    @Override
    public final boolean equals(Term other) {
        if ((other.getTermType() == TermType.LOGARITHMIC) && (getCoef() == other.getCoef())) {
            LogarithmicTerm logTerm = (LogarithmicTerm) other;
            return (base == logTerm.getBase()) && (getVar().equals(logTerm.getVar()));
        }
        return false;
    }

    public final boolean isBaseE() {
        return base == Math.E;
    }
}
