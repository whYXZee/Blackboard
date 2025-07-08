package whyxzee.blackboard.math.pure.numbers;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.pure.equations.terms.PowerTerm;
import whyxzee.blackboard.utils.NumberUtils;

/**
 * All Complex objects default to COMPLEX unless specificied.
 */
public class Complex implements Comparable<Complex> {
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
        return new Complex(a.clone(), b.clone(), type);
    }
    // #endregion

    // #region Conversion Methods
    public static final Complex fromObj(Object arg) {
        if (arg instanceof Complex) {
            return (Complex) arg;
        } else if (NumberUtils.isNumPrimitive(arg)) {
            return Complex.cmplx(NumberUtils.doubleFromObj(arg), 0);
        }
        return Complex.DNE();
    }

    public static final Complex[] fromObjArr(Object[] args) {
        Complex[] out = new Complex[args.length];
        for (int i = 0; i < args.length; i++) {
            out[i] = Complex.fromObj(args[i]);
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
        Complex[] addends = Complex.fromObjArr(args);

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

    /**
     * Performs
     * {@link whyxzee.blackboard.math.pure.numbers.Complex#add(Object...)}
     * without the use of an object.
     * 
     * <p>
     * <em>Calling statAdd() does not change the data of any of the
     * addends</em>.
     * 
     * @param addendA
     * @param addends
     * @return
     */
    public static final Complex statAdd(Complex addendA, Object... addends) {
        return addendA.clone().add(addends);
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
        Complex[] factors = Complex.fromObjArr(args);

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

    /**
     * Performs
     * {@link whyxzee.blackboard.math.pure.numbers.Complex#multiply(Object...)}
     * without the use of an object.
     * 
     * <p>
     * <em>Calling statMultiply() does not change the data of any of the
     * factors</em>.
     * 
     * @param factorA
     * @param factors
     * @return
     */
    public static final Complex statMultiply(Complex factorA, Object... args) {
        return factorA.clone().multiply(args);
    }
    // #endregion

    // #region Division
    /**
     * Divides the dividend by <b>n</b> divisors. A deep copy is not required to
     * call this method. The following are implemented:
     * 
     * <ul>
     * <li>Infinite division (infinites are treated as if it was a limit)
     * <li>Division over zero (treated as if it were a limit)
     * <li>DNE
     * <li>TODO: what if args not the same type?
     * </ul>
     * 
     * <em>Calling divide() changes the data of <b>this</b>, but not the data of the
     * arguments</em>.
     * 
     * @param args
     * @return
     */
    public final Complex divide(Object... args) {
        Complex[] divisors = Complex.fromObjArr(args);

        switch (type) {
            case COMPLEX:
                for (Complex i : divisors) {
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

    /**
     * Performs
     * {@link whyxzee.blackboard.math.pure.numbers.Complex#divide(Object...)}
     * without the use of an object.
     * 
     * <p>
     * <em>Calling statPower() does not change the data of the base nor the
     * power</em>.
     * 
     * @param dividend
     * @param divisors
     * @return
     */
    public static final Complex statDivide(Object dividend, Object... divisors) {
        return Complex.fromObj(dividend).clone().divide(divisors);
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
    public final Complex power(Object arg) {
        Complex power = Complex.fromObj(arg);

        switch (type) {
            case COMPLEX:
                this.copy(CmplxUtils.pow(this, power));
                return this;
            default:
                return Complex.DNE();
        }
    }

    /**
     * Performs
     * {@link whyxzee.blackboard.math.pure.numbers.Complex#power(Object)}
     * without the use of an object.
     * 
     * <p>
     * <em>Calling statPower() does not change the data of the base nor the
     * power</em>.
     * 
     * @param base
     * @param power
     * @return
     */
    public static final Complex statPower(Object base, Object power) {
        return Complex.fromObj(base).clone().power(power);
    }
    // #endregion

    // #region Modulo
    public final Complex mod(Object arg) {
        Complex modulo = Complex.fromObj(arg);

        if (this.isReal() && modulo.isReal()) {
            return new Complex(a.mod(modulo.getA().getValue()), 0, type);
        }
        return Complex.DNE();
    }
    // #endregion

    // #region Circle Trig
    /**
     * Returns a Complex that is the sine of <b>value</b>.
     * 
     * <p>
     * <em>Calling sin() does not change the data of <b>value</b></em>.
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
                re = BNum.statMultiply(a.sin(), b.cosh());
                im = BNum.statMultiply(a.cos(), b.sinh());
                break;
            default:
                re = im = BNum.DNE;
                break;
        }
        return new Complex(re, im, value.getType());
    }

    /**
     * Returns a Complex that is the cosine of <b>value</b>.
     * 
     * <p>
     * <em>Calling cos() does not change the data of <b>value</b></em>.
     * 
     * @param value
     * @return
     */
    public static final Complex cos(Complex value) {
        if (value.isDNE()) {
            return Complex.DNE();
        }

        BNum a = value.getA(), b = value.getB();
        BNum re, im;
        switch (value.getType()) {
            case COMPLEX:
                // complex argument
                re = BNum.statMultiply(a.cos(), b.cosh());
                im = BNum.statMultiply(a.sin(), b.sinh()).negate();
                break;
            default:
                re = im = BNum.DNE;
                break;
        }
        return new Complex(re, im, value.getType());
    }

    /**
     * Returns a Complex that is the tangent of <b>value</b>.
     * 
     * <p>
     * <em>Calling tan() does not change the data of <b>value</b></em>.
     * 
     * @param value
     * @return
     */
    public static final Complex tan(Complex value) {
        if (value.isDNE()) {
            return Complex.DNE();
        }

        BNum a = value.getA(), b = value.getB();
        BNum re, im;
        switch (value.getType()) {
            case COMPLEX:
                // complex argument
                if (value.isANegative()) {
                    return Complex.tan(Complex.cmplx(a.negate(), b.negate())).negate();
                }

                Complex num, denom;
                if (b.isNegative()) {
                    num = Complex.cmplx(a.tan(), b.tanh());
                    denom = Complex.cmplx(1, BNum.statMultiply(a.tan(), b.tanh()).negate());
                } else {
                    num = Complex.cmplx(a.tan(), b.tanh().negate());
                    denom = Complex.cmplx(1, BNum.statMultiply(a.tan(), b.tanh()));
                }
                return num.divide(denom);
            default:
                re = im = BNum.DNE;
                break;
        }
        return new Complex(re, im, value.getType());
    }

    /**
     * Returns a Complex that is the cosecant of <b>value</b>.
     * 
     * <p>
     * <em>Calling csc() does not change the data of <b>value</b></em>.
     * 
     * @param value
     * @return
     */
    public static final Complex csc(Complex value) {
        if (value.isDNE()) {
            return Complex.DNE();
        }

        // sin already performs appropiate ops based on the ComplexType
        Complex sinVal = Complex.sin(value);
        if (sinVal.isZero()) {
            return Complex.DNE();
        }
        return sinVal.reciprocal(); // reciprocal is always based on the type
    }

    /**
     * Returns a Complex that is the secant of <b>value</b>.
     * 
     * <p>
     * <em>Calling sec() does not change the data of <b>value</b></em>.
     * 
     * @param value
     * @return
     */
    public static final Complex sec(Complex value) {
        if (value.isDNE()) {
            return Complex.DNE();
        }

        Complex cosVal = Complex.cos(value);
        if (cosVal.isZero()) {
            return Complex.DNE();
        }
        return cosVal.reciprocal();
    }

    /**
     * Returns a Complex that is the cotangent of <b>value</b>.
     * 
     * <p>
     * <em>Calling cot() does not change the data of <b>value</b></em>.
     * 
     * @param value
     * @return
     */
    public static final Complex cot(Complex value) {
        if (value.isDNE()) {
            return Complex.DNE();
        }

        Complex tanVal = Complex.tan(value);
        if (tanVal.isZero()) {
            return Complex.DNE();
        }
        return tanVal.reciprocal();
    }
    // #endregion

    // #region Inv Circle Trig
    /**
     * Returns a Complex that is the arc-sine of <b>value</b>.
     * 
     * <p>
     * <em>Calling asin() does not change the data of <b>value</b></em>.
     * 
     * TODO: this method is not a thing sob
     * 
     * @param value
     * @return
     */
    public static final Complex asin(Complex value) {
        if (value.isDNE()) {
            return Complex.DNE();
        }

        switch (value.getType()) {
            case COMPLEX:
                if (value.isReal()) {
                    BNum asin = value.getA().asin();
                    if (value.isANegative()) {
                        asin.add(Math.PI);
                    }
                    return Complex.cmplx(asin, 0);
                } else if (value.isImaginary()) {

                } else {
                    // cry >:D
                }
            default:
                return Complex.DNE();
        }
    }

    /**
     * Returns a Complex that is the arc-sine of <b>value</b>.
     * 
     * <p>
     * <em>Calling asin() does not change the data of <b>value</b></em>.
     * 
     * TODO: this method is not a thing sob
     * 
     * @param value
     * @return
     */
    public static final Complex acos(Complex value) {
        if (value.isDNE()) {
            return Complex.DNE();
        }

        switch (value.getType()) {
            case COMPLEX:
                if (value.isReal()) {
                    BNum acos = value.getA().acos();
                    if (value.isBNegative()) {
                        acos.add(Math.PI);
                    }
                    return Complex.cmplx(acos, 0);
                }
            default:
                return Complex.DNE();
        }
    }

    /**
     * Returns a Complex that is the arc-sine of <b>value</b>.
     * 
     * <p>
     * <em>Calling asin() does not change the data of <b>value</b></em>.
     * 
     * TODO: this method is not a thing sob
     * 
     * @param value
     * @return
     */
    public static final Complex atan(Complex value) {
        if (value.isDNE()) {
            return Complex.DNE();
        }

        switch (value.getType()) {
            default:
                return Complex.DNE();
        }
    }

    /**
     * Returns a Complex that is the arc-sine of <b>value</b>.
     * 
     * <p>
     * <em>Calling asin() does not change the data of <b>value</b></em>.
     * 
     * TODO: this method is not a thing sob
     * 
     * @param value
     * @return
     */
    public static final Complex acsc(Complex value) {
        if (value.isDNE()) {
            return Complex.DNE();
        }

        switch (value.getType()) {
            default:
                return Complex.DNE();
        }
    }

    /**
     * Returns a Complex that is the arc-sine of <b>value</b>.
     * 
     * <p>
     * <em>Calling asin() does not change the data of <b>value</b></em>.
     * 
     * TODO: this method is not a thing sob
     * 
     * @param value
     * @return
     */
    public static final Complex asec(Complex value) {
        if (value.isDNE()) {
            return Complex.DNE();
        }

        switch (value.getType()) {
            default:
                return Complex.DNE();
        }
    }

    /**
     * Returns a Complex that is the arc-sine of <b>value</b>.
     * 
     * <p>
     * <em>Calling asin() does not change the data of <b>value</b></em>.
     * 
     * TODO: this method is not a thing sob
     * 
     * @param value
     * @return
     */
    public static final Complex acot(Complex value) {
        if (value.isDNE()) {
            return Complex.DNE();
        }

        switch (value.getType()) {
            default:
                return Complex.DNE();
        }
    }
    // #endregion

    // #region Comparison Bools
    /**
     * Check if <b>this</b> is equal to any object.
     * 
     */
    @Override
    public boolean equals(Object arg) {
        if (NumberUtils.isNumPrimitive(arg)) {
            double other = NumberUtils.doubleFromObj(arg);
            return NumberUtils.precisionCheck(a.getValue(), other) && b.isZero();
        }

        if (this.getClass() != arg.getClass()) {
            return false;
        }

        Complex other = Complex.fromObj(arg);
        return NumberUtils.precisionCheck(a.getValue(), other.getA().getValue())
                && NumberUtils.precisionCheck(b.getValue(), other.getB().getValue());
    }

    public static final boolean inClosedRange(Object argLBound, Object argMiddle, Object argUBound) {
        // TODO: what if not the same type?
        Complex lBound = Complex.fromObj(argLBound);
        Complex middle = Complex.fromObj(argMiddle);
        Complex uBound = Complex.fromObj(argUBound);

        boolean reRange = BNum.inClosedRange(lBound.getA(), middle.getA(), uBound.getA());
        boolean imRange = BNum.inClosedRange(lBound.getB(), middle.getB(), uBound.getB());
        return reRange && imRange;
    }

    @Override
    public int compareTo(Complex arg0) {
        if (a.compareTo(arg0.getA()) < 0 || b.compareTo(arg0.getB()) < 0) {
            return -1;
        } else if (a.compareTo(arg0.getA()) > 0 || b.compareTo(arg0.getB()) > 0) {
            return 1;
        }
        return 0;
    }
    // #endregion

}
