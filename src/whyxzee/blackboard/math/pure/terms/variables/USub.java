package whyxzee.blackboard.math.pure.terms.variables;

import whyxzee.blackboard.math.pure.equations.MathFunction;
import whyxzee.blackboard.math.pure.numbers.BNumber;
import whyxzee.blackboard.math.pure.terms.Term;

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
        this.innerTerm = null;
    }

    public USub(Term innerTerm) {
        super("u", VarType.U_SUB_TERM);
        this.innerTerm = innerTerm;
        this.innerFunction = null;
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
    @Override
    public final MathFunction getInnerFunction() {
        return innerFunction;
    }

    @Override
    public final Term getInnerTerm() {
        return innerTerm;
    }

    //
    // Arirthmetic Methods
    //
    @Override
    public BNumber solve(BNumber value) {
        switch (getVarType()) {
            case U_SUB_EQ:
                return innerFunction.solve(value);
            case U_SUB_TERM:
                return innerTerm.solve(value);
            default:
                return new BNumber(0, 0);
        }
    }

    //
    // Boolean Methods
    //
    @Override
    public final boolean containsVar(String var) {
        switch (getVarType()) {
            case U_SUB_EQ:
                return innerFunction.containsVar(var);
            case U_SUB_TERM:
                return innerTerm.getVar().containsVar(var);
            default:
                return false;
        }
    }

    @Override
    public boolean needsChainRule() {
        return true;
    }
}
