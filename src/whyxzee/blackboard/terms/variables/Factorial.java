package whyxzee.blackboard.terms.variables;

import whyxzee.blackboard.equations.MathFunction;
import whyxzee.blackboard.utils.ArithmeticUtils;
import whyxzee.blackboard.utils.SpecialFunctions;

public class Factorial extends Variable {
    /* Variables */
    private MathFunction innerFunction;

    public Factorial(String var, MathFunction innerFunction) {
        super("u", 1);
        this.innerFunction = innerFunction;
    }

    @Override
    public double solve(double value) {
        /* */
        double innerVal = innerFunction.solve(value);

        /* */
        if (ArithmeticUtils.isInteger(innerVal)) {
            return SpecialFunctions.factorial((int) innerVal);
        } else {
            return SpecialFunctions.gammaFunction(innerVal);
        }
    }

    @Override
    public String toString() {
        return "(" + innerFunction.toString() + ")!";
    }

}
