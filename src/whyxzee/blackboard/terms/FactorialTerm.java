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
    public String printConsole() {
        return Double.toString(getNum()) + "(" + getVar().printConsole() + ")";
    }

    @Override
    public String toString() {
        return Double.toString(getNum()) + "(" + getVar().toString() + ")";
    }

    //
    // Arithmetic Methods
    //

    @Override
    public double solve(double value) {
        /* Declaring variables */
        double innerVal = getVar().solve(value);

        /* Factorial Algorithm */
        if (ArithmeticUtils.isInteger(innerVal)) {
            return SpecialFunctions.factorial((int) innerVal);
        } else {
            return SpecialFunctions.gammaFunction(innerVal);
        }
    }

    @Override
    public Term negate() {
        return new FactorialTerm(-1 * getNum(), getVar());
    }

    @Override
    public Term derive() {
        // TODO implement derivative of factorial term
        throw new UnsupportedOperationException("Unimplemented method 'derive'");
    }

    @Override
    public double limInfSolve() {
        /* Number */
        double number = getNum();
        if (number == 0) {
            return 0;
        }
        boolean isNumberNegative = number < 0;

        return isNumberNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
    }

    @Override
    public double limNegInfSolve() {
        // TODO implement limNegInfSolve for factorial
        throw new UnsupportedOperationException("Unimplemented method 'limNegInfSolve'");
    }

}
