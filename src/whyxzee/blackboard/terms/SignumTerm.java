package whyxzee.blackboard.terms;

import whyxzee.blackboard.terms.variables.*;

/**
 * A package for the signum function, with a*sgn(x).
 * 
 * <p>
 * The class is constructed as a y=sgn(x)
 * 
 * <p>
 * The functionality of this class has been checked on {@code 5/20/2025} and the
 * following has changed since:
 * <ul>
 * <li>similarTo()
 */
public class SignumTerm extends Term {

    public SignumTerm(double coefficient, Variable var) {
        super(coefficient, var, TermType.SIGNUM);
    }

    @Override
    public final String toString() {
        /* Initializing variables */
        double coef = getCoef();

        if (coef == 0) {
            return "0";
        } else if (coef == 1) {
            return "sgn(" + getVar().toString() + ")";
        } else {
            return Double.toString(coef) + "sgn(" + getVar().toString() + ")";
        }
    }

    @Override
    public final String printConsole() {
        /* Initializing variables */
        double coef = getCoef();

        if (coef == 0) {
            return "0";
        } else if (coef == 1) {
            return "sgn(" + getVar().printConsole() + ")";
        } else {
            return Double.toString(coef) + "sgn(" + getVar().printConsole() + ")";
        }
    }

    //
    // Arithmetic Methods (Object-related)
    //
    @Override
    public final double solve(double value) {
        return getCoef() * Math.signum(getVar().solve(value));
    }

    @Override
    public final Term negate() {
        return new SignumTerm(-1 * getCoef(), getVar());
    }

    @Override
    public final Term derive() {
        return PowerTerm.ZERO_TERM;
    }

    @Override
    public final double limInfSolve() {
        /* Number */
        double number = getCoef();
        if (number == 0) {
            return 0;
        }

        /* Function */
        return number;
    }

    @Override
    public final double limNegInfSolve() {

        /* Number */
        double number = getCoef();
        if (number == 0) {
            return 0;
        }

        /* Function */
        return -number;
    }

    //
    // Boolean Methods
    //
    @Override
    public final boolean similarTo(Term term) {
        switch (term.getTermType()) {
            case SIGNUM:
                SignumTerm signTerm = (SignumTerm) term;
                return getVar().equals(signTerm.getVar());
            default:
                return false;
        }
    }

    @Override
    public final boolean equals(Term other) {
        if ((other.getTermType() == TermType.SIGNUM) && (getCoef() == other.getCoef())) {
            SignumTerm signTerm = (SignumTerm) other;
            return getVar().equals(signTerm.getVar());
        }
        return false;
    }
}
