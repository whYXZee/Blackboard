package whyxzee.blackboard.math.pure.terms.variables;

import whyxzee.blackboard.math.pure.equations.MathEQ;
import whyxzee.blackboard.math.pure.numbers.BNumber;
import whyxzee.blackboard.math.pure.terms.Term;

/**
 * The package for a polynomial variable. The variable class is modeled after
 * the variable x^n.
 * 
 * <p>
 * The functionalityof this class was checked on {@code 5/10/2025}, and nothing
 * has changed since. However, the following remain unimplemented:
 * <ul>
 * <li>simplifyPower() unimplemented
 */
public class Variable {
    /* General Use : static */
    public static final Variable noVar = new Variable("");

    /* Variable Identification */
    private String var;
    private VarType varType;

    public enum VarType {
        VARIABLE,
        U_SUB_TERM,
        U_SUB_EQ
    }

    /**
     * The constructor class for a variable with a integer power.
     * 
     * @param var
     * @param power
     */
    public Variable(String var) {
        /* Variable identification */
        this.var = var;
        varType = VarType.VARIABLE;
    }

    /**
     * The constructor class for a specialized variable with a integer power.
     * 
     * @param var
     * @param power
     */
    public Variable(String var, VarType varType) {
        /* Variable identification */
        this.var = var;
        this.varType = varType;
    }

    @Override
    public String toString() {
        return var;
    }

    public String printConsole() {
        return var;
    }

    @Override
    public Variable clone() {
        return new Variable(getVar(), getVarType());
    }

    //
    // Arithmetic Methods
    //

    /**
     * Solves the polynomial.
     * 
     * @param value
     * @return
     */
    public BNumber solve(BNumber value) {
        return value;
    }

    //
    // Get & Set Methods
    //
    public final String getVar() {
        return var;
    }

    public final void setVarType(VarType varType) {
        this.varType = varType;
    }

    public final VarType getVarType() {
        return varType;
    }

    public MathEQ getInnerFunction() {
        return null;
    }

    public Term getInnerTerm() {
        return null;
    }

    //
    // Boolean Methods
    //
    public boolean containsVar(String var) {
        return this.var.equals(var);
    }

    public boolean needsChainRule() {
        return false;
    }

    public final boolean isVarType(VarType varType) {
        return this.varType == varType;
    }

    public final boolean isUSub() {
        switch (varType) {
            case VARIABLE:
                return false;
            default:
                return true;
        }
    }

    public boolean equals(Variable other) {
        if (varType == other.getVarType()) {
            switch (varType) {
                case U_SUB_EQ:
                    return getInnerFunction().equals(other.getInnerFunction());
                case U_SUB_TERM:
                    return getInnerTerm() == other.getInnerTerm();
                case VARIABLE:
                    return var == other.getVar();
                default:
                    break;
            }
        }

        return false;
    }
}
