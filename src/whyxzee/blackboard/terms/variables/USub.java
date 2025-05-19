package whyxzee.blackboard.terms.variables;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.Constants.VariableConstants;
import whyxzee.blackboard.equations.EQMultiplication;
import whyxzee.blackboard.equations.MathFunction;
import whyxzee.blackboard.terms.PolynomialTerm;
import whyxzee.blackboard.terms.Term;
import whyxzee.blackboard.terms.TrigTerm;
import whyxzee.blackboard.terms.TrigTerm.TrigType;

/**
 * A type of variable where a function is subsituted into the Variable class.
 * 
 * The functionality of this class has not been checked.
 */
public class USub extends Variable {
    /* Variables */
    private MathFunction innerFunction;

    public USub(int power, MathFunction innerFunction) {
        super("u", power, VarType.U_SUB_EQ);
        this.innerFunction = innerFunction;

        setShouldChainRule(true);
    }

    public USub(int numPower, int denomPower, MathFunction innerFunction) {
        super("u", numPower, denomPower, VarType.U_SUB_EQ);
        this.innerFunction = innerFunction;

        setShouldChainRule(true);
    }

    //
    // Get and Set Methods
    //

    public final MathFunction getInnerFunction() {
        return innerFunction;
    }

    //
    // Arirthmetic Methods
    //
    @Override
    public double solve(double value) {
        return Math.pow(innerFunction.solve(value), (double) getNumeratorPower() / getDenominatorPower());
    }

    @Override
    public Term derive() {
        /* Variables */
        int nPower = getNumeratorPower(); // numerator power
        int dPower = getDenominatorPower(); // denominator power

        /* Chain rule */
        EQMultiplication eq = new EQMultiplication(
                // Outer function (u^n) - functional
                new PolynomialTerm((double) nPower / dPower, setPower(nPower - dPower, dPower)),

                // Inner function (u)
                new PolynomialTerm(1, new USub(1, innerFunction.derive())));
        return new PolynomialTerm(1, new USub(1, eq));
    }

    @Override
    public Term integrate() {
        /* Initiating Variables */
        int nPower = getNumeratorPower();
        int dPower = getDenominatorPower();

        /* Integration Algorithm */
        if (getShouldChainRule()) {

        } else {
            // no chain rule
            if (innerFunction.equals(Constants.EquationConstants.SEC_X_DERIVATIVE)) {
                // tan(x)sec(x)
                return new TrigTerm(1, VariableConstants.BASE_VAR, TrigType.SECANT);
            } else if (innerFunction.equals(Constants.EquationConstants.CSC_X_DERIVATIVE)) {
                return new TrigTerm(1, VariableConstants.BASE_VAR, TrigType.COSECANT);
            }
        }

        return null;
    }

    //
    // Boolean Methods
    //
    @Override
    public boolean varEquals(Variable other) {
        if (getVarType() == other.getVarType()) {
            // if both USub
            return innerFunction.equals(other.getInnerFunction());
        }
        return false;
    }

    @Override
    public String toString() {
        if (getNumeratorPower() == 0) {
            return "";
        } else if (getNumeratorPower() == 1 && getDenominatorPower() == 1) {
            return innerFunction.toString();
        } else {
            return "(" + innerFunction.toString() + ")" + getPowerUnicode();
        }
    }
}
