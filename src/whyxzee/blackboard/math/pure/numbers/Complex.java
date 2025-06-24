package whyxzee.blackboard.math.pure.numbers;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.pure.equations.terms.PowerTerm;
import whyxzee.blackboard.math.utils.pure.NumberUtils;
import whyxzee.blackboard.utils.ObjectUtils;

public class Complex {
    /* Variables */
    private BNum a;
    private BNum b;
    private Object modulus;
    private double theta;
    private ComplexType type;

    public enum ComplexType {
        COMPLEX('i');

        private final char imChar;

        private ComplexType(char imChar) {
            this.imChar = imChar;
        }

        public final char getImChar() {
            return imChar;
        }
    }

    // #region Constants
    public static final double NEG_ONE_TO_I = Math.exp(-Math.PI);
    // #endregion

    // #region Constructors
    public Complex(Object a, Object b, ComplexType type) {
        this.a = BNum.fromObj(a);
        this.b = BNum.fromObj(b);
        this.type = type;
        calcPolar();
    }

    /**
     * Static constructor for a complex number.
     * 
     * @param a the real component
     * @param b the imaginary component
     * @return
     */
    public static final Complex cmplx(Object a, Object b) {
        return new Complex(a, b, ComplexType.COMPLEX);
    }

    public static final Complex DNE() {
        return new Complex(BNum.DNE, 0, ComplexType.COMPLEX);
    }

    public static final Complex negOneToPower(double power) {
        return Complex.fromPolar(ComplexType.COMPLEX, 1, Math.PI * power);
    }

    /**
     * Creates a complex number of the type <b>type</b> utilizing its polar
     * definition.
     * 
     * @param type
     * @param modulus
     * @param theta
     * @return
     */
    public static final Complex fromPolar(ComplexType type, Object modulus, double theta) {
        switch (type) {
            case COMPLEX:
                BNum mod = BNum.fromObj(modulus);
                BNum re = mod.clone();
                re.multiply(Math.cos(theta));
                BNum im = mod.clone();
                im.multiply(Math.sin(theta));

                return new Complex(re, im, ComplexType.COMPLEX);
            default:
                return null;
        }
    }
    // #endregion

    // #region String/Display
    @Override
    public final String toString() {
        if (isZero()) {
            return "0";
        } else if (isDNE()) {
            return "DNE";
        } else if (isReal()) {
            return a.toString();
        } else if (isImaginary()) {
            return b.toString() + type.getImChar();
        }

        /* Complex */
        String output = "";
        output += a.toString();
        output += b.isNegative() ? " - " : " + ";
        output += b.isNegative() ? b.negate().toString() : b.toString();
        output += type.getImChar();
        return output;
    }
    // #endregion

    public final ComplexType getType() {
        return type;
    }

    // #region Cartesian Def
    public final BNum getA() {
        return a;
    }

    public final void setA(Object a) {
        this.a = BNum.fromObj(a);
    }

    public final BNum getB() {
        return b;
    }

    public final void setB(Object b) {
        this.b = BNum.fromObj(b);
    }
    // #endregion

    // #region Polar Def
    /**
     * Calculates the polar definition based on the given cartesian information.
     */
    public final void calcPolar() {
        calcModulus();
        calcTheta();
    }

    public final Object getModulus() {
        return modulus;
    }

    public final void setModulus(Object modulus) {
        this.modulus = modulus;
    }

    /**
     * Calculates the modulus in the polar definition based on the given cartesian
     * information.
     */
    public final void calcModulus() {
        switch (type) {
            case COMPLEX:
                if (a.isDNE() || b.isDNE()) {
                    modulus = BNum.DNE;
                    return;
                }

                if (a.isInfinity() || b.isInfinity()) {
                    // TODO: get largest infinity
                    modulus = BNum.infinity(false, 1);
                    return;
                }

                double modVal;
                if (a.equals(0)) {
                    modVal = b.abs();
                } else if (b.equals(0)) {
                    modVal = a.abs();
                } else {
                    modVal = Math.sqrt(Math.pow(a.getValue(), 2) + Math.pow(b.getValue(), 2));
                }
                modulus = new BNum(modVal);
                break;
        }
    }

    public final double getTheta() {
        return theta;
    }

    public final void setTheta(double theta) {
        this.theta = theta;
    }

    /**
     * Calculates the theta in the polar definition based on the given cartesian
     * information.
     */
    public final void calcTheta() {
        switch (type) {
            case COMPLEX:
                double aTan = BNum.statDivide(b, a).atan().getValue();
                if ((b.isNegative() && a.isNegative()) || a.isNegative()) {
                    aTan += Math.PI;
                }
                theta = aTan;
                break;
        }
    }
    // #endregion

    // #region Copying/Cloning
    /**
     * Copies the data from <b>other</b> onto <b>this</b>.
     * 
     * <p>
     * <em>Calling copy() changes the data of <b>this</b>, but not the data of
     * <b>other</b></em>.
     * 
     * @param other
     */
    public final void copy(Complex other) {
        this.a = other.getA();
        this.b = other.getB();
        this.modulus = other.getModulus();
        this.theta = other.getTheta();
        this.type = other.getType();
    }

    public final Complex clone() {
        return null;
    }
    // #endregion

    // #region Conversion Methods
    public static final Complex fromObj(ComplexType defaultType, Object arg) {
        if (arg instanceof Complex) {
            return (Complex) arg;
        }
        return Complex.DNE();
    }

    public static final Complex[] fromObjArr(ComplexType defaultType, Object[] args) {
        Complex[] out = new Complex[args.length];
        for (int i = 0; i < args.length; i++) {
            out[i] = Complex.fromObj(defaultType, args[i]);
        }
        return out;
    }

    public final PowerTerm toTerm() {
        return new PowerTerm(this);
    }

    public final ArrayList<Complex> toArrayList() {
        ArrayList<Complex> out = new ArrayList<Complex>();
        out.add(this);
        return out;
    }

    public final Complex[] toArr() {
        Complex[] out = { this };
        return out;
    }
    // #endregion

    // #region Number Bools
    /**
     * A number is complex if both <b>a</b> and <b>b</b> are non-zero values.
     * 
     * @return {@code a != 0 && b != 0}
     */
    public boolean isComplex() {
        return !a.isZero() && !b.isZero();
    }

    /**
     * A number is imaginary if the <b>a</b> value is zero, while the <b>b</b> value
     * is a non-zero value.
     * 
     * @return {@code a == 0 && b != 0}
     */
    public boolean isImaginary() {
        return a.isZero() && !b.isZero();
    }

    /**
     * A number is real if the <b>a</b> value is a non-zero value while the <b>b</b>
     * value is a zero.
     * 
     * @return {@code a != 0 && b == 0};
     */
    public boolean isReal() {
        return !a.isZero() && b.isZero();
    }

    /**
     * 
     * @param sigFigs the accuracy that should be checked, used for
     *                {@link whyxzee.blackboard.math.pure.numbers.NumberTheory#withinEpsilon(double, double, double)}.
     * @return {@code false} if the number is imaginary, if the number is complex,
     *         or if it is not rational. {@code true} if otherwise.
     */
    public boolean isRational(int sigFigs) {
        if (isImaginary() || isComplex()) {
            return false;
        }
        return a.isRational(sigFigs);
    }

    /**
     * Checks if the number is a rational number using the
     * {@link whyxzee.blackboard.Constants.Number#SIG_FIGS} number of sig
     * figs.
     * 
     * @return {@code false} if the number is imaginary, if the number is complex,
     *         or if it is not rational.
     *         <li>{@code true} if otherwise.
     */
    public boolean isRational() {
        return isRational(Constants.Number.SIG_FIGS);
    }

    /**
     * 
     * @return
     */
    public boolean hasInfinity() {
        return (!a.hasValue() || !b.hasValue()) && (!a.isInfinitesimal() && !b.isInfinitesimal());
    }

    public boolean isDNE() {
        return a.isDNE() || b.isDNE();
    }
    // #endregion

    // #region isZero Bools
    /**
     * Checks if both the real and the imaginary component are zero.
     * 
     * @return
     */
    public final boolean isZero() {
        return a.isZero() && b.isZero();
    }

    public final boolean isAZero() {
        return a.isZero();
    }

    public final boolean isBZero() {
        return b.isZero();
    }
    // #endregion

    // #region isNegative Bools
    public final boolean isANegative() {
        return a.isNegative();
    }

    public final boolean isBNegative() {
        return b.isNegative();
    }
    // #endregion

    /**
     * Performs a deep copy of the BNumber, and then negates the copy.
     * 
     * @return
     */
    public final Complex negate() {
        return new Complex(a.negate(), b.negate(), type);
    }

    // #region Addition
    /**
     * Adds <b>n</b> complex numbers. Adds all of the real parts together, and then
     * all of the imaginary parts together. The following is implemented into the
     * method:
     * <ul>
     * <li>Uncountable addition
     * <li>DNE
     * <li>TODO: what if args not the same type?
     * </ul>
     * 
     * <em>Calling add() does modify the data of <b>this</b>, but not the data of
     * any of the addends.</em>
     * 
     * @param addends
     * @return
     */
    public final Complex add(Object... args) {
        Complex[] addends = Complex.fromObjArr(type, args);

        switch (type) {
            case COMPLEX:
                for (Complex i : addends) {
                    a.add(i.getA());
                    b.add(i.getB());
                }

                calcPolar();
                return this;
            default:
                return null;
        }
    }
    // #endregion

    // #region Multiplication
    /**
     * Multiplies <b>n</b> complex numbers together.
     * <p>
     * The following are implemented:
     * <ul>
     * <li>Multiplication with infinites and infinitesimal: treated as limits
     * <li>DNE
     * <li>TODO: what if args not the same type?
     * </ul>
     * 
     * <p>
     * <em>Calling multiply() changes the data of <b>this</b>, but not the data of
     * any of the factors</em>.
     * 
     * @param args values, BNumbers, or number primitives
     * @return
     */
    public final Complex multiply(Object... args) {
        Complex[] factors = Complex.fromObjArr(type, args);

        switch (type) {
            case COMPLEX:
                for (Complex i : factors) {
                    double cTheta = this.getTheta() + i.getTheta(); // combinedTheta
                    BNum cMod = BNum.statMultiply(modulus, BNum.fromObj(i.getModulus())); // combinedModulus
                    this.copy(Complex.fromPolar(ComplexType.COMPLEX, cMod, cTheta));
                }
                return this;
            default:
                return Complex.DNE();
        }
    }

    /**
     * Multiplies the real and imaginary parts of the complex number by a real
     * value.
     * 
     * @param value
     */
    public final void multiplyScalar(double value) {
        a.multiply(value);
        b.multiply(value);
        calcPolar();
    }
    // #endregion

    // #region Division
    /**
     * Divides the divisor by <b>n</b> dividends. A deep copy is not required to
     * call this method. The following are implemented:
     * 
     * <ul>
     * <li>Infinite division (infinites are treated as if it was a limit)
     * <li>Division over zero (treated as if it were a limit)
     * <li>DNE
     * <li>TODO: what if args not the same type?
     * </ul>
     * 
     * <em>Calling divide() does not change the data of the arguments, as deep
     * copies are created</em>.
     * 
     * @param dividends
     * @return
     */
    public final Complex divide(Object... args) {
        Complex[] dividends = Complex.fromObjArr(type, args);

        switch (type) {
            case COMPLEX:
                for (Complex i : dividends) {
                    /* Variables */
                    double cTheta = this.getTheta() - i.getTheta(); // combinedTheta
                    BNum cModulus = BNum.statDivide(modulus, BNum.fromObj(i.getModulus())); // combinedModulus
                    this.copy(Complex.fromPolar(ComplexType.COMPLEX, cModulus, cTheta));
                }
                return this;
            default:
                return Complex.DNE();
        }
    }
    // #endregion

    // #region Power
    /**
     * <em>Calling reciprocal will change the data of <b>this</b></em>.
     * 
     * @return
     */
    public final Complex reciprocal() {
        this.copy(new Complex(1, 0, type).divide(this));
        return this;
    }

    /**
     * Gets the complex number of a real, imaginary, or complex base to a real,
     * imaginary, or complex power. The following are implemented:
     * <ul>
     * <li>Infinite base and/or infinite power (infinites are treated as if it was a
     * limit)
     * <li>infinitesimal (treated like a limit)
     * <li>Irrational powers
     * <li>Negative powers
     * <li>DNE
     * </ul>
     * <em>Calling power() will change the data of <b>this</b> so it reflects the
     * data of the output, but not the data of <b>power</b></em>.
     * 
     * @param power
     * @return
     */
    public final Complex power(Complex power) {
        switch (type) {
            case COMPLEX:
                this.copy(CmplxUtils.pow(this, power));
                return this;
            default:
                return Complex.DNE();
        }
    }
    // #endregion

    // #region Circle Trig
    /**
     * Returns the sine value of <b>value</b>.
     * 
     * @param value
     * @return
     */
    public static final Complex sin(Complex value) {
        if (value.isDNE()) {
            return Complex.DNE();
        }

        BNum a = value.getA(), b = value.getB();
        BNum re, im;
        switch (value.getType()) {
            case COMPLEX:
                if (value.isReal()) {
                    // real argument
                    re = a.sin();
                    im = new BNum(0);
                } else if (value.isImaginary()) {
                    // imaginary argument
                    re = new BNum(0);
                    im = b.sinh();
                } else {
                    // complex argument
                    if (value.isANegative()) {
                        return Complex.sin(Complex.cmplx(a.negate(), b.negate())).negate();
                    }
                    re = BNum.statMultiply(a.sin(), b.cosh());
                    im = BNum.statMultiply(a.cos(), b.sinh());
                    if (b.isNegative()) {
                        im = im.negate();
                    }
                }
                break;
            default:
                re = im = BNum.DNE;
                break;
        }
        return new Complex(re, im, value.getType());
    }
    // #endregion

    // #region Comparison Bools
    /**
     * Check if <b>this</b> is equal to any object.
     * 
     */
    @Override
    public boolean equals(Object arg) {
        if (ObjectUtils.isNumPrimitive(arg)) {
            double other = ObjectUtils.doubleFromObj(arg);
            return NumberUtils.precisionCheck(a.getValue(), other) && b.isZero();
        }

        if (this.getClass() != arg.getClass()) {
            return false;
        }

        Complex other = Complex.fromObj(type, arg);
        return NumberUtils.precisionCheck(a.getValue(), other.getA().getValue())
                && NumberUtils.precisionCheck(b.getValue(), other.getB().getValue());
    }
    // #endregion
}
