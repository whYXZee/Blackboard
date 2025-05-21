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

    public FactorialTerm(double num, Variable var) {
        super(num, var, TermType.FACTORIAL);
    }

    @Override
    public String toString() {
        return Double.toString(getCoefficient()) + "(" + getVar().toString() + ")";
    }

    @Override
    public String printConsole() {
        return Double.toString(getCoefficient()) + "(" + getVar().printConsole() + ")";
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
            return SpecialFunctions.gammaFunction(innerVal);
        }
    }

    @Override
    public Term negate() {
        return new FactorialTerm(-1 * getCoefficient(), getVar());
    }

    @Override
    public Term derive() {
        // TODO implement derivative of factorial term
        throw new UnsupportedOperationException("Unimplemented method 'derive'");
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
        // TODO implement limNegInfSolve for factorial
        throw new UnsupportedOperationException("Unimplemented method 'limNegInfSolve'");
    }

}
