package whyxzee.blackboard.terms;

import whyxzee.blackboard.terms.variables.Variable;
import whyxzee.blackboard.utils.ArithmeticUtils;
import whyxzee.blackboard.utils.SpecialFunctions;

/**
 * The package for an absolute value term. The term is a*(x)!.
 * 
 * <p>
 * The package is constructed as an y=x! equation.
 * 
 * <p>
 * The functionality of this class has not been checked.
 */
public class FactorialTerm extends Term {

    public FactorialTerm(double coefficient, Variable var) {
        super(coefficient, var, TermType.FACTORIAL);
    }

    @Override
    public String toString() {
        double coefValue = getCoef();
        if (coefValue == 0) {
            return "0";
        }

        String coef = ArithmeticUtils.valueToString(coefValue);
        return coef + "(" + getVar().toString() + ")!";
    }

    @Override
    public String printConsole() {
        double coef = getCoef();
        if (coef == 0) {
            return "";
        }

        return Double.toString(coef) + "(" + getVar().printConsole() + ")!";
    }

    //
    // Arithmetic Methods
    //

    @Override
    public double solve(double value) {
        /* Declaring variables */
        double innerVal = getVar().solve(value);

        /* Factorial */
        if (ArithmeticUtils.isInteger(innerVal)) {
            return SpecialFunctions.factorial((int) innerVal);
        } else {
            return SpecialFunctions.gammaFunction(innerVal + 1);
        }
    }

    @Override
    public Term negate() {
        return new FactorialTerm(-1 * getCoef(), getVar());
    }

    @Override
    public Term derive() {
        // TODO implement derivative of factorial term
        throw new UnsupportedOperationException("Unimplemented method 'derive'");
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
        // TODO implement limNegInfSolve for factorial
        throw new UnsupportedOperationException("Unimplemented method 'limNegInfSolve'");
    }

    //
    // Boolean Methods
    //
    @Override
    public boolean similarTo(Term term) {
        switch (term.getTermType()) {
            case FACTORIAL:
                FactorialTerm factTerm = (FactorialTerm) term;
                return factTerm.getVar().equals(getVar());
            default:
                return false;
        }
    }

    @Override
    public boolean equals(Term other) {
        if ((other.getTermType() == TermType.FACTORIAL) && (getCoef() == other.getCoef())) {
            return getVar().equals(other.getVar());
        }
        return false;
    }
}
