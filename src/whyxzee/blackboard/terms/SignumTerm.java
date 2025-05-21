package whyxzee.blackboard.terms;

import whyxzee.blackboard.terms.variables.Variable;

/**
 * A package for the signum function, with a*sgn(x).
 * 
 * <p>
 * The class is constructed as a y=sgn(x)
 * 
 * <p>
 * The functionality of this class has been checked on {@code 5/20/2025} and
 * nothing has changed since.
 */
public class SignumTerm extends Term {

    public SignumTerm(double num, Variable var) {
        super(num, var, TermType.SIGNUM);
    }

    @Override
    public String toString() {
        /* Initializing variables */
        double number = getCoefficient();

        if (number == 0) {
            return "";
        } else if (number == 1) {
            return "sgn(" + getVar().toString() + ")";
        } else {
            return Double.toString(number) + "sgn(" + getVar().toString() + ")";
        }
    }

    @Override
    public String printConsole() {
        /* Initializing variables */
        double number = getCoefficient();

        if (number == 0) {
            return "";
        } else if (number == 1) {
            return "sgn(" + getVar().printConsole() + ")";
        } else {
            return Double.toString(number) + "sgn(" + getVar().printConsole() + ")";
        }
    }

    //
    // Arithmetic Methods
    //
    @Override
    public double solve(double value) {
        return getCoefficient() * Math.signum(getVar().solve(value));
    }

    @Override
    public Term negate() {
        return new SignumTerm(-1 * getCoefficient(), getVar());
    }

    @Override
    public Term derive() {
        return PolynomialTerm.ZERO_TERM;
    }

    @Override
    public double limInfSolve() {
        /* Number */
        double number = getCoefficient();
        if (number == 0) {
            return 0;
        }

        /* Function */
        return number;
    }

    @Override
    public double limNegInfSolve() {

        /* Number */
        double number = getCoefficient();
        if (number == 0) {
            return 0;
        }

        /* Function */
        return -number;
    }

}
