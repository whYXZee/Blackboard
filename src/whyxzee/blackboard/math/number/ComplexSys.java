package whyxzee.blackboard.math.number;

/**
 * A package for creating custom complex systems (ie: complex vs split-complex).
 * 
 * <p>
 * The functionality of this class has not been checked.
 * 
 * @param M is the class for the modulus
 * @param T is the class for the theta
 */
public abstract class ComplexSys<M, T> {
    // #region Variables
    private M modulus;
    private T theta;
    private final char imChar; // the char that represents the im portion
    // #endregion

    // #region Constructor
    /**
     * A constructor for a new Complex system.
     * 
     * @param imChar is the character that represents the imaginary component. ie:
     *               in the Complex system, the <b>imChar</b> would be "i"
     */
    public ComplexSys(char imChar) {
        this.imChar = imChar;
    }
    // #endregion

    // #region Display
    /**
     * @return the character that represents the imaginary portion of the complex
     *         number.
     */
    public final char getImChar() {
        return imChar;
    }
    // #endregion

    // #region Copying/Cloning
    public abstract ComplexSys<M, T> clone();
    // #endregion

    // #region System Definition
    /**
     * Sets the <b>modulus</b> of <b>this</b> to a value depending on the
     * rectangular information provided. The calculations are based on the
     * implementations of this class.
     * 
     * @param a is the real component of the complex number
     * @param b is the imaginary component of the complex number
     */
    public abstract void calcModulus(BVal a, BVal b);

    /**
     * @return the modulus of <b>this</b> complex system.
     */
    public final M getModulus() {
        return modulus;
    }

    /**
     * 
     * @param modulus
     */
    public final void setModulus(M modulus) {
        this.modulus = modulus;
    }

    /**
     * Sets the <b>theta</b> of <b>this</b> to a value depending on the rectangular
     * information provided. The calculations are based on the implementations of
     * this class.
     * 
     * @param a is the real component of the complex number
     * @param b is the imaginary component of the complex number
     */
    public abstract void calcTheta(BVal a, BVal b);

    /**
     * @return the theta of <b>this</b> complex system.
     */
    public final T getTheta() {
        return theta;
    }

    /**
     * 
     * @param theta
     */
    public final void setTheta(T theta) {
        this.theta = theta;
    }

    /**
     * Creates a rectangular definition of a complex number with <b>this</b> system.
     * The calculations are based on the implementations of this class.
     * 
     * @return
     */
    public abstract BNum toRectangular();
    // #endregion

    // #region Arithmetic
    /**
     * Adds n number of <b>addends</b> to the <b>arg0</b>.
     * 
     * <p>
     * <em>Calling add() does not change the data of <b>arg0</b> nor the data of
     * <b>addends</b>.</em>
     * 
     * @param arg0    the number which contains the information to <b>this</b>
     *                system.
     * @param addends an array of BNum that ideally have the same complex system as
     *                <b>this</b>.
     * @return a sum of the <b>arg0</b> and the <b>addends</b> in the same complex
     *         system as <b>this</b>.
     */
    public abstract BNum add(BNum arg0, BNum... addends);

    /**
     * Multiplies <b>arg0</b> by n number of <b>factors</b>.
     * 
     * <p>
     * <em>Calling multiply() does not change the data of <b>arg0</b> nor the data
     * of <b>factors</b>.</em>
     * 
     * @param arg0    the number which contains the information to <b>this</b>
     *                system.
     * @param factors an array of BNum that ideally have the same complex system as
     *                <b>this</b>.
     * @return a product of the <b>arg0</b> and the <b>factors</b> in the same
     *         complex system as <b>this</b>.
     */
    public abstract BNum multiply(BNum arg0, BNum... factors);

    /**
     * Divides <b>arg0</b> by n number of <b>dividends</b>.
     * 
     * <p>
     * <em>Calling multiply() does not change the data of <b>arg0</b> nor the data
     * of <b>dividends</b>.</em>
     * 
     * @param arg0      the number which contains the information to <b>this</b>
     *                  system.
     * @param dividends an array of BNum that ideally have the same complex system
     *                  as <b>this</b>.
     * @return a quotient of the <b>arg0</b> and the <b>dividends</b> in the same
     *         complex system as <b>this</b>.
     */
    public abstract BNum divide(BNum arg0, BNum... dividends);

    /**
     * Sets <b>base</b> to an <b>exponent</b>.
     * 
     * <p>
     * <em>Calling power() does not change the data of <b>base</b> nor the data of
     * <b>exponent</b>.</em>
     * 
     * 
     * @param base
     * @param exponent
     * @return
     */
    public abstract BNum power(BNum base, BNum exponent);
    // #endregion

    // #region Comparison
    /**
     * Checks if <b>this</b> is equal to any object.
     * 
     * @param arg0 is an object of any type.
     * @return {@code true} if <b>arg0</b> has the same class as <b>this</b>, has an
     *         equal modulus, and an equal theta.
     *         <li>{@code false} if otherwise
     */
    @Override
    public boolean equals(Object arg0) {
        if (arg0.getClass() != this.getClass()) {
            // same class, so the modulus and theta should be the same type
            return false;
        }

        ComplexSys<?, ?> o = (ComplexSys<?, ?>) arg0;
        return modulus.equals(o.getModulus()) && theta.equals(o.getTheta());

    }
    // #endregion
}
