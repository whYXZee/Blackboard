package whyxzee.blackboard.terms.variables;

import whyxzee.blackboard.equations.MathFunction;
import whyxzee.blackboard.terms.PolynomialTerm;
import whyxzee.blackboard.terms.Term;

/**
 * A type of variable where a function is subsituted into the Variable class.
 * 
 * <p>
 * The functionality of this class has been checked on {@code 5/20/2025} and
 * nothing has changed since.
 */
public class USub extends Variable {
    /* Variables */
    private MathFunction innerFunction;
    private Term innerTerm;

    public USub(MathFunction innerFunction) {
        super("u", VarType.U_SUB_EQ);
        this.innerFunction = innerFunction;
    }

    public USub(Term innerTerm) {
        super("u", VarType.U_SUB_TERM);
        this.innerTerm = innerTerm;
    }

    @Override
    public String printConsole() {
        switch (getVarType()) {
            case U_SUB_EQ:
                return innerFunction.printConsole();
            case U_SUB_TERM:
                return innerTerm.printConsole();
            default:
                return "";
        }
    }

    @Override
    public String toString() {
        switch (getVarType()) {
            case U_SUB_EQ:
                return innerFunction.toString();
            case U_SUB_TERM:
                return innerTerm.toString();
            default:
                return "";
        }
    }

    @Override
    public USub clone() {
        switch (getVarType()) {
            case U_SUB_EQ:
                return new USub(innerFunction);
            case U_SUB_TERM:
                return new USub(innerTerm);
            default:
                return null;
        }
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
        switch (getVarType()) {
            case U_SUB_EQ:
                return innerFunction.solve(value);
            case U_SUB_TERM:
                return innerTerm.solve(value);
            default:
                return 0;
        }
    }

    /**
     * Derives just the inner term or inner function.
     */
    @Override
    public Term derive() {
        /* Chain rule */
        switch (getVarType()) {
            case U_SUB_EQ:
                /* Chain rule */
                return new PolynomialTerm(1, new USub(innerFunction.derive()), 1);
            case U_SUB_TERM:
                /* Chain rule */
                return new PolynomialTerm(1, new USub(innerTerm.derive()), 1);
            default:
                return null;
        }

    }

    //
    // Boolean Methods
    //

    @Override
    public boolean needsChainRule() {
        return true;
    }
}
