package whyxzee.blackboard.terms;

import whyxzee.blackboard.equations.MultiplicativeEQ;
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
 * the following has changed:
 * <ul>
 * <li>similarTo()
 * <li>equals()
 */
public class AbsoluteValTerm extends Term {

    public AbsoluteValTerm(double coefficient, Variable var) {
        super(coefficient, var, TermType.ABSOLUTE_VALUE);
    }

    @Override
    public String printConsole() {
        /* Coefficient */
        double coef = getCoef();
        if (coef == 0) {
            return "0";
        } else if (coef == 1) {
            return "|" + getVar().printConsole() + "|";
        } else {
            return Double.toString(getCoef()) + "|" + getVar().printConsole() + "|";
        }
    }

    @Override
    public String toString() {
        /* Coefficient */
        double coef = getCoef();
        if (coef == 0) {
            return "0";
        } else if (coef == 1) {
            return "|" + getVar().toString() + "|";
        } else {
            return Double.toString(getCoef()) + "|" + getVar().toString() + "|";
        }
    }

    //
    // Arithmetic Methods
    //
    @Override
    public double solve(double value) {
        return getCoef() * Math.abs(getVar().solve(value));
    }

    @Override
    public Term negate() {
        return new AbsoluteValTerm(-1 * getCoef(), getVar());
    }

    @Override
    public Term derive() {
        /* Initializing variables */
        Variable variable = getVar().clone();

        if (variable.needsChainRule()) {
            MultiplicativeEQ eq = new MultiplicativeEQ(
                    variable.derive(),
                    new SignumTerm(1, variable));
            return new PowerTerm(getCoef(), new USub(eq), 1);
        } else {
            return new SignumTerm(getCoef(), variable);
        }
    }

    @Override
    public double limInfSolve() {
        /* Number */
        double coef = getCoef();
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
        double number = getCoef();
        if (number == 0) {
            return 0;
        }
        boolean isNumberNegative = number < 0;

        /* Function */
        return isNumberNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
    }

    //
    // Boolean Methods
    //
    @Override
    public boolean similarTo(Term term) {
        switch (term.getTermType()) {
            case ABSOLUTE_VALUE:
                AbsoluteValTerm absValTerm = (AbsoluteValTerm) term;
                return (absValTerm.getVar().equals(getVar()));
            default:
                return false;
        }
    }

    @Override
    public boolean equals(Term other) {
        if ((other.getTermType() == TermType.ABSOLUTE_VALUE) && (getCoef() == other.getCoef())) {
            return getVar().equals(other.getVar());
        }
        return false;
    }

}
