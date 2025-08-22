package whyxzee.blackboard.math.number;

import whyxzee.blackboard.math.number.complex.BaseComplex;
import whyxzee.blackboard.utils.NumberUtils;

/**
 * A package which denotes a complex number. The type of complex number is
 * dependent on the <b>cmplxSys</b>.
 * 
 * <p>
 * The functionality of this class has not been checked.
 */
public class BNum {
    // #region Variables
    private BVal a; // real component
    private BVal b; // imaginary component
    private ComplexSys<?, ?> cmplxSys;
    // #endregion

    // #region Constructors
    /**
     * The constructor for a complex number from real and imaginary components.
     * 
     * @param a        the real component of a complex number.
     * @param b        the imaginary component of a complex number.
     * @param cmplxSys the type of Complex system (ie is it complex, split-complex,
     *                 etc?)
     */
    public BNum(BVal a, BVal b, ComplexSys<?, ?> cmplxSys) {
        this.a = a;
        this.b = b;
        this.cmplxSys = cmplxSys;
        calcComplex();
    }

    /**
     * The constructor for a complex number from any object. The number defaults to
     * the {@link whyxzee.blackboard.math.number.complex.BaseComplex}.
     * 
     * @param arg0 If an invalid class is inputted, then a DNE will be created. The
     *             following are accepted class types:
     *             <ul>
     *             <li>Number primitives and wrappers
     *             </ul>
     * 
     */
    public BNum(Object arg0) {
        if (NumberUtils.isNumPrimitive(arg0)) {
            this.a = new BVal(arg0);
            this.b = new BVal(0);
            this.cmplxSys = new BaseComplex();
        } else {
        }
    }

    /**
     * The factory method for a number using the complex system.
     * 
     * @param a the real component of a complex number.
     * @param b the imaginary component of a complex number.
     * @return
     */
    public static final BNum complex(BVal a, BVal b) {
        return new BNum(a, b, new BaseComplex());
    }
    // #endregion

    @Override
    public String toString() {
        String output = "";
        if (!a.isZero()) {
            output += a.toString();
        }
        if (!b.isZero()) {
            output += " + ";
            output += b.asCoef();
            output += cmplxSys.getImChar();
        }

        return output;
    }

    /**
     * @return a String that represents the number as if it was a coefficient for a
     *         term, variable, etc.
     */
    public String asCoef() {
        return "(" + toString() + ")";
    }

    // #region Copying/Cloning
    public BNum clone() {
        return new BNum(a.clone(), b.clone(), cmplxSys.clone());
    }
    // #endregion

    // #region Value
    /**
     * Checks if both the real and imaginary components are zero.
     * 
     * @return {@code true} if <b>a</b> and <b>b</b> are zero
     *         <li>{@code false} if at least one has a value
     */
    public boolean isZero() {
        return a.isZero() && b.isZero();
    }

    /**
     * Checks if the number is real—the real component is a non-zero value
     * while the imaginary component is zero.
     * 
     * @return {@code (a != 0) && (b == 0)}
     */
    public boolean isReal() {
        return !a.isZero() && b.isZero();
    }

    /**
     * Checks if the number is imaginary—the real component is zero while the
     * imaginary component is a non-zero value.
     * 
     * @return {@code (a == 0) && (b != 0)}
     */
    public boolean isImaginary() {
        return b.isZero() && !a.isZero();
    }

    /**
     * Checks if the number is complex—the real and imaginary components are
     * non-zero values.
     * 
     * @return {@code (a != 0) && (b != 0)}
     */
    public boolean isComplex() {
        return !a.isZero() && !b.isZero();
    }
    // #endregion

    // #region Rectangular
    /**
     * <em>Calling cloneA() will perform a deep copy of the real component of
     * <b>this</b>.</em>
     * 
     * @return a clone of the real component.
     */
    public final BVal cloneA() {
        return a.clone();
    }

    /**
     * <em>Calling getA() will return the reference of the real component of
     * <b>this</b>.</em>
     * 
     * @return the real component of the number.
     */
    public final BVal getA() {
        return a;
    }

    /**
     * Sets <b>this</b> number's real component to <b>a</b>.
     * 
     * <p>
     * <em>Calling setA() will set <b>this</b> number's real component to a deep
     * copy of <b>a</b>.</em>
     * 
     * @param a the new real component
     */
    public final void setA(BVal a) {
        this.a = a.clone();
    }

    /**
     * <em>Calling cloneB() will perform a deep copy of the imaginary component of
     * <b>this</b>.</em>
     * 
     * @return a clone of the imaginary component.
     */
    public final BVal cloneB() {
        return b.clone();
    }

    /**
     * <em>Calling getB() will return the reference of the imaginary component of
     * <b>this</b>.</em>
     * 
     * @return the imaginary component of the number.
     */
    public final BVal getB() {
        return b;
    }

    /**
     * Sets <b>this</b> number's imaginary component to <b>b</b>.
     * 
     * <p>
     * <em>Calling setB() will set <b>this</b> number's imaginary component to a
     * deep copy of <b>b</b>.</em>
     * 
     * @param b the new imaginary component
     */
    public final void setB(BVal b) {
        this.b = b.clone();
    }
    // #endregion

    // #region Complex System
    /**
     * <em>Calling getCmplxSys() will return the reference of <b>this</b> number's
     * complex system.</em>
     * 
     * @return the complex system of <b>this</b> number.
     */
    public ComplexSys<?, ?> getCmplxSys() {
        return cmplxSys;
    }

    public void calcComplex() {
        cmplxSys.calcModulus(a, b);
        cmplxSys.calcTheta(a, b);
    }
    // #endregion

    // #region Arithmetic
    /**
     * Adds <b>n</b> number of addends onto <b>this</b>. The addition is dependent
     * on the complex system of <b>this</b>.
     * 
     * <p>
     * <em>Calling add() does not change the data of <b>this</b> nor the data of the
     * <b>addends</b>.</em>
     * 
     * @param addends an array of addends that ideally have the same complex system
     *                as <b>this</b>
     * @return a number with the same complex system as <b>this</b>
     */
    public BNum add(BNum... addends) {
        return cmplxSys.add(this, addends);
    }

    /**
     * Multiplies <b>this</b> by <b>n</b> number of factors. The
     * multiplication is dependent on the complex system of <b>this</b>.
     * 
     * <p>
     * <em>Calling multiply() does not change the data of <b>this</b> nor the data
     * of the <b>factors</b>.</em>
     * 
     * @param factors an array of factors that ideally have the same complex system
     *                as <b>this</b>
     * @return a product with the same complex system as <b>this</b>
     */
    public BNum multiply(BNum... factors) {
        return cmplxSys.multiply(this, factors);
    }

    /**
     * Multiplies <b>this</b> by a <b>scalar</b> number. The scalar is multiplied in
     * the rectangular form (meaning {@code a*scalar} and {@code b*scalar})..
     * 
     * <p>
     * <em>Calling multiply() does not change the data of <b>this</b> nor the data
     * of the <b>scalar</b>.
     * 
     * @param scalar a scalar value that is multiplied to <b>this</b>.
     * @return a product with the same complex system as <b>this</b>.
     */
    public BNum multiply(BVal scalar) {
        return new BNum(a.multiply(scalar), b.multiply(scalar), cmplxSys.clone());
    }

    /**
     * Divides <b>this</b> by <b>n</b> number of dividends. The
     * division is dependent on the complex system of <b>this</b>.
     * 
     * <p>
     * <em>Calling divide() does not change the data of <b>this</b> nor the data
     * of the <b>dividends</b>.</em>
     * 
     * @param dividends an array of dividends that ideally have the same complex
     *                  system as <b>this</b>
     * @return a product with the same complex system as <b>this</b>
     */
    public BNum divide(BNum... dividends) {
        return cmplxSys.divide(this, dividends);
    }

    /**
     * Sets <b>this</b> to an <b>exponent</b>.
     * 
     * <p>
     * <em>Calling power() does not change the data of <b>this</b> nor the data of
     * <b>exponent</b>.</em>
     * 
     * @param exponent a number that is ideally the same complex system as
     *                 <b>this</b>.
     * @return a power with the same complex system as <b>this</b>.
     */
    public BNum power(BNum exponent) {
        return cmplxSys.power(this, exponent);
    }
    // #endregion

    // #region Comparison Bools
    /**
     * Checks if <b>this</b> has an equal value to <b>arg0</b>.
     * 
     * @param arg0 is an object with any type. If an object of any class other than
     *             a BNum is inputted into the function, then equals() is called
     *             with the argument of a BNum using the object constructor
     *             ({@link whyxzee.blackboard.math.number.BNum#BNum(Object)})
     * @return {@code true} if <b>this</b> and <b>arg0</b> have the same number
     *         system, equal value in the real component, and equal value in the
     *         imaginary component.
     *         <li>{@code false} if otherwise
     */
    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof BNum) {
            BNum o = (BNum) arg0;
            return (cmplxSys.getClass() == o.getCmplxSys().getClass())
                    && a.equals(o.getA())
                    && b.equals(o.getB());
        }
        return equals(new BNum(arg0));
    }
    // #endregion
}
