package whyxzee.blackboard.math.pure.equations.variables;

import whyxzee.blackboard.math.pure.equations.MathEQ;
import whyxzee.blackboard.math.pure.equations.terms.PowerTerm;

/**
 * The package for a polynomial variable. The variable class is modeled after
 * the variable x^n. Acceptable values of T include:
 * <ul>
 * <li>Strings
 * <li>
 * <li>
 * 
 * <p>
 * The functionalityof this class was checked on <b>5/10/2025</b>, and nothing
 * has changed since. However, the following remain unimplemented:
 * <ul>
 * <li>simplifyPower() unimplemented
 */
public class Variable<T> {
    /* General-use : static */
    public static final Variable<String> noVar = new Variable<String>("");

    /* Variable Identification */
    private T inner;

    // #region Constructors
    /**
     * The constructor class for a variable with a integer power.
     * 
     * @param inner
     * @param power
     */
    public Variable(T inner) {
        if (!validClass(inner)) {
            throw new UnsupportedOperationException("Class " + inner.getClass() + " in supported for Variable");
        }

        this.inner = inner;
    }

    public static final Variable<String> noVar() {
        return new Variable<String>("");
    }
    // #endregion

    // #region Inner Class Bools
    /**
     * Checks if the <b>obj</b> is an instance of one of the following classes:
     * <ul>
     * <li>MathEQ - {@link whyxzee.blackboard.math.pure.equations.MathEQ}
     * <li>Term - {@link whyxzee.blackboard.math.pure.terms.Term}
     * <li>String - {@link java.lang.String}
     * </ul>
     * 
     * @param obj
     * @return
     */
    public final boolean validClass(Object obj) {
        return obj instanceof MathEQ
                || obj instanceof PowerTerm
                || obj instanceof String;
    }
    // #endregion

    // #region String / Display
    @Override
    public String toString() {
        return inner.toString();
    }
    // #endregion

    // // #region Copying / Cloning
    @Override
    public Variable<?> clone() {
        if (inner instanceof String) {
            return new Variable<String>((String) inner);
        } else if (inner instanceof PowerTerm) {
            return new Variable<PowerTerm>(((PowerTerm) inner).clone());
        } else if (inner instanceof MathEQ) {
            return new Variable<MathEQ>(((MathEQ) inner).clone());
        }
        return new Variable<T>(inner);
    }
    // #endregion

    // #region Solve
    /**
     * Plugs in a term for the variable.
     * 
     * @param variable the variable to replace.
     * @param value
     * @return
     */
    public final PowerTerm solve(String variable, PowerTerm value) {
        if (inner instanceof String) {
            // String var = (String) inner; // dunno if this is needed
            if (inner.equals(variable)) {
                return value;
            } else {
                return new PowerTerm(1, this);
            }
        } else if (inner instanceof MathEQ) {
            return ((MathEQ) inner).solve(variable, value);
        } else if (inner instanceof PowerTerm) {
            return ((PowerTerm) inner).solve(variable, value);
        }

        return null;
    }
    // #endregion

    // #region Get/Set
    public final T getInner() {
        return inner;
    }

    public final Class<?> getInnerClass() {
        return inner.getClass();
    }

    public final void setInner(T inner) {
        this.inner = inner;
    }

    // #endregion

    // #region Comparison Bools
    @Override
    public boolean equals(Object arg) {
        if (arg == null) {
            return false;
        }

        if (arg instanceof Variable) {
            // the question mark means any class
            Variable<?> other = (Variable<?>) arg;
            if (getInnerClass() != other.getInnerClass()) {
                return false;
            }

            return getInner().equals(other.getInner());
        } else if (validClass(arg)) {
            // if object is an acceptable class of the inner
            return getInner().equals(arg);
        }
        return false;

    }
    // #endregion

    // #region Overlap Bools
    public final boolean containsVar(String var) {
        if (inner instanceof String) {
            return inner.equals(var);
        } else if (inner instanceof PowerTerm) {
            return ((PowerTerm) inner).containsVar(var);
        } else if (inner instanceof MathEQ) {
            return ((MathEQ) inner).getTerms().containsVar(var);
        }
        return false;
    }
    // #endregion

    // #region U-Sub Bools
    public final boolean isUSub() {
        return inner instanceof PowerTerm || inner instanceof MathEQ;
    }
    // #endregion
}
