package whyxzee.blackboard.math.pure.terms.variables;

import whyxzee.blackboard.math.pure.equations.MathEQ;
import whyxzee.blackboard.math.pure.numbers.ComplexNum;
import whyxzee.blackboard.math.pure.terms.Term;

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
                || obj instanceof Term
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
    // @Override
    // public Variable<T> clone() {
    // // TODO: how to clone T?
    // return new Variable<T>(var);
    // }
    // #endregion

    /**
     * Solves the variable.
     * 
     * @param value
     * @return
     */
    public ComplexNum solve(ComplexNum value) {
        if (inner instanceof MathEQ) {
            return ((MathEQ) inner).solve(value);
        } else if (inner instanceof Term) {
            return ((Term) inner).solve(value);
        }

        return value;
    }

    // #region Get/Set
    public final T getInner() {
        return inner;
    }

    public final Class<?> getInnerClass() {
        return inner.getClass();
    }

    // #endregion

    // #region Comparison Bools
    @Override
    public boolean equals(Object var1) {
        if (var1 == null) {
            return false;
        }

        if (var1 instanceof Variable) {
            // the question mark means any class
            Variable<?> other = (Variable<?>) var1;
            if (getInnerClass() != other.getInnerClass()) {
                return false;
            }

            return getInner().equals(other.getInner());
        } else if (validClass(var1)) {
            // if object is an acceptable class of the inner
            return getInner().equals(var1);
        }
        return false;

    }
    // #endregion

    // #region Overlap Bools
    /**
     * Checks if an object is inside of a variable
     * 
     * @param var1
     * @return
     */
    public final boolean contains(Object var1) {
        if (var1 == null) {
            return false;
        }

        if (var1 instanceof Variable || validClass(var1)) {
            if (this.equals(var1)) {
                // checked if it's a string
                return true;
            }

            /* Check the inner */
            if (inner instanceof Term) {
                return ((Term) inner).contains(var1);
            } else if (inner instanceof MathEQ) {
                return ((MathEQ) inner).contains(var1);
            }
        }

        return false;
    }
    // #endregion

    // #region U-Sub Bools
    public boolean isUSub() {
        return inner instanceof Term || inner instanceof MathEQ;
    }
    // #endregion
}
