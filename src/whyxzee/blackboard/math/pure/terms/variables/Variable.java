package whyxzee.blackboard.math.pure.terms.variables;

import whyxzee.blackboard.math.pure.equations.MathEQ;
import whyxzee.blackboard.math.pure.numbers.BNumber;
import whyxzee.blackboard.math.pure.terms.Term;

/**
 * The package for a polynomial variable. The variable class is modeled after
 * the variable x^n.
 * 
 * <p>
 * The functionalityof this class was checked on <b>5/10/2025</b>, and nothing
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

    // #region Constructors
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
    // #endregion

    // #region String / Display
    @Override
    public String toString() {
        return var;
    }
    // #endregion

    // #region Copying / Cloning
    @Override
    public Variable clone() {
        return new Variable(getVar(), getVarType());
    }
    // #endregion

    /**
     * Solves the variable.
     * 
     * @param value
     * @return
     */
    public BNumber solve(BNumber value) {
        return value;
    }

    // #region Var Get/Set
    public final String getVar() {
        return var;
    }

    public final void setVarType(VarType varType) {
        this.varType = varType;
    }

    public final VarType getVarType() {
        return varType;
    }
    // #endregion

    // #region U-Sub Get/Set
    public MathEQ getInnerFunction() {
        return null;
    }

    public Term getInnerTerm() {
        return null;
    }

    // #endregion

    // #region Comparison Bools
    /**
     * 
     * @param var
     * @return
     */
    public boolean containsVar(Variable var) {
        switch (varType) {
            case U_SUB_EQ:
                return getInnerFunction().contains(var);
            case U_SUB_TERM:
                return getInnerTerm().contains(var);
            case VARIABLE:
                if (var.isVarType(VarType.VARIABLE)) {
                    return this.var.equals(var.getVar());
                }
                return false;
            default:
                return false;

        }
    }

    public final boolean isVarType(VarType varType) {
        return this.varType == varType;
    }

    @Override
    public boolean equals(Object var1) {
        if (var1 == null || !(var1 instanceof Variable)) {
            return false;
        }

        Variable other = (Variable) var1;
        if (varType != other.getVarType()) {
            return false;
        }

        switch (varType) {
            case U_SUB_EQ:
                return getInnerFunction().equals(other.getInnerFunction());
            case U_SUB_TERM:
                return getInnerTerm().equals(other.getInnerTerm());
            case VARIABLE:
                return var.equals(other.getVar());
            default:
                return false;
        }
    }
    // #endregion

    // #region Overlap Bools
    public final boolean contains(Object var1) {
        if (var1 == null) {
            return false;
        }

        return false;

    }
    // #endregion

    // #region U-Sub Bools
    public boolean isUSub() {
        return this instanceof USub;
    }

    public boolean hasInnerTerm() {
        return false;
    }

    public boolean hasInnerFunction() {
        return false;
    }
    // #endregion
}
