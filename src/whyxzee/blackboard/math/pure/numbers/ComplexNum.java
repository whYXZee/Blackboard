package whyxzee.blackboard.math.pure.numbers;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.pure.equations.terms.PowerTerm;
import whyxzee.blackboard.utils.Loggy;

/**
 * A generic package made for real, imaginary, and complex numbers.
 * The package is built for the polar definition of complex numbers.
 * 
 * <p>
 * The functionality of this class has been checked on <b>6/21/2025</b> and
 * the following has changed:
 */
public class ComplexNum {
    private static final Loggy loggy = new Loggy(Constants.Loggy.COMPLEX_NUM_LOGGY);

    /* Polar Definition of Complex Numbers */
    private double modulus;
    private double theta;

    /* Rectangular Definition */
    private Value a; // real part of a number
    private Value b; // imaginary part

    // #region Constructors
    /**
     * Constructor for a DNE.
     */
    public ComplexNum() {
        a = new Value();
        b = new Value();
        refreshPolar();
    }

    /**
     * Constructor for a complex number, formatted in rectangular form.
     * ({@code a+bi}).
     * 
     * @param a the "real part" of a number
     * @param b the "imaginary part" of a number
     */
    public ComplexNum(double a, double b) {
        /* Rectangular Definition */
        this.a = new Value(a);
        this.b = new Value(b);

        /* Polar Definition */
        refreshPolar();
    }

    public ComplexNum(Value a, Value b) {
        /* Rectangular Definition */
        this.a = a;
        this.b = b;

        /* Polar Definition */
        refreshPolar();
    }

    public ComplexNum(Object a, Object b) {
        if (a instanceof Value) {
            this.a = (Value) a;
        } else if (a instanceof Double) {
            this.a = new Value((double) a);
        } else if (a instanceof Integer) {
            this.a = new Value((int) a);
        } else {
            this.a = new Value();
        }

        if (b instanceof Value) {
            this.b = (Value) b;
        } else if (b instanceof Double) {
            this.b = new Value((double) b);
        } else if (b instanceof Integer) {
            this.b = new Value((int) b);
        } else {
            this.b = new Value();
        }

        refreshPolar();
    }

    /**
     * Static constructor for a complex number, formatted in the polar form
     * ({@code r*cis(theta)}) or the exponential form ({@code r*e^(theta*i)}).
     * 
     * @param modulus
     * @param theta
     * @return
     */
    public static final ComplexNum fromPolar(double modulus, double theta) {
        return new ComplexNum(modulus * Math.cos(theta), modulus * Math.sin(theta));
    }

    /**
     * Static constructor for a complex number with modulus infinity. Formatted in
     * the polar form.
     * 
     * @return
     */
    private static final ComplexNum uncountableCis(Value inf, double theta) {
        Value a, b;

        /* Real Component */
        double cosVal = Math.cos(theta);
        if (!NumberUtils.precisionCheck(cosVal, 0)) {
            a = inf.clone();
            a.multiply(Math.signum(cosVal));
        } else {
            a = new Value(0);
        }

        /* Imaginary Component */
        double sinVal = Math.sin(theta);
        if (!NumberUtils.precisionCheck(sinVal, 0)) {
            b = inf.clone();
            b.multiply(Math.signum(sinVal));
        } else {
            b = new Value(0);
        }
        return new ComplexNum(a, b);
    }

    /**
     * Static constructor for a complex number that has a value of zero.
     * 
     * @return
     */
    public static final ComplexNum zero() {
        return new ComplexNum(0, 0);
    }

    /**
     * Static constructor for a number that is the equivalent of (-1)^power.
     * 
     * @param power
     * @return {@code cis(PI * power)}
     */
    public static final ComplexNum negOneToPower(double power) {
        return fromPolar(1, Math.PI * power);
    }

    // #endregion

    // #region String/Display
    @Override
    public String toString() {
        if (isZero()) {
            return "0";
        } else if (a.isDNE() || b.isDNE()) {
            return "DNE";
        } else if (isReal()) {
            return a.toString();
        } else if (isImaginary()) {
            return b.toString() + Constants.Unicode.IMAGINARY_NUMBER;
        }

        /* Complex */
        String output = "";
        output += a.toString();
        output += (b.isNegative()) ? " - " : " + ";
        output += b.isNegative() ? b.negate().toString() : b.toString();
        output += Constants.Unicode.IMAGINARY_NUMBER;
        return output;
    }

    /**
     * Prints out a string in the polar form ({@code r*cis(theta)}).
     * 
     * @return
     */
    public String polarString() {
        if (isZero()) {
            return "0";
        }
        if (isReal()) {
            return NumberUtils.valueToString(modulus) + "cis(0)";
        }

        return NumberUtils.valueToString(modulus) + "cis(" + NumberUtils.valueToString(theta) + ")";
    }
    // #endregion

    // #region Copying / Cloning
    /**
     * Sets <b>this</b> to contain the a, b, modulus, and theta of <b>other</b>.
     * 
     * @param other
     */
    public void copy(ComplexNum other) {
        a = other.getA();
        b = other.getB();
        refreshPolar();
    }

    /**
     * Provides a deep copy of the BNumber.
     */
    @Override
    public ComplexNum clone() {
        return new ComplexNum(a.clone(), b.clone());
    }
    // #endregion

    // #region Conversion Methods
    public static final ComplexNum fromObj(Object arg) {
        if (arg instanceof Value) {
            return new ComplexNum((Value) arg, new Value(0));
        } else if (arg instanceof ComplexNum) {
            return (ComplexNum) arg;
        } else if (arg instanceof Double) {
            return new ComplexNum((double) arg, new Value(0));
        } else if (arg instanceof Integer) {
            return new ComplexNum((int) arg, new Value(0));
        }

        /* DNE */
        return new ComplexNum();
    }

    public final PowerTerm toTerm() {
        return new PowerTerm(this);
    }

    public final ArrayList<ComplexNum> toArrayList() {
        ArrayList<ComplexNum> out = new ArrayList<ComplexNum>();
        out.add(this);
        return out;
    }
    // #endregion

    // #region Rectangular Get/Set
    public final Value getA() {
        return a;
    }

    /**
     * Sets the real component of a complex number to a value.
     * 
     * @param a
     */
    public final void setA(double a) {
        this.a = new Value(a);
    }

    public final void setA(Value a) {
        this.a = a;
    }

    public final Value getB() {
        return b;
    }

    public final void setB(double b) {
        this.b = new Value(b);
    }

    public final void setB(Value b) {
        this.b = b;
    }
    // #endregion

    // #region Polar Get/Set
    public final void refreshPolar() {
        refreshModulus();
        refreshTheta();
    }

    public final double getModulus() {
        return modulus;
    }

    public final void setModulus(double modulus) {
        this.modulus = modulus;
    }

    public final void refreshModulus() {
        if (a.isDNE() || b.isDNE()) {
            modulus = Double.NaN;
            return;
        }

        if (a.isInfinity() || b.isInfinity()) {
            modulus = Double.POSITIVE_INFINITY;
            return;
        }

        if (a.equals(0)) {
            modulus = b.abs();
        } else if (b.equals(0)) {
            modulus = a.abs();
        } else {
            modulus = Math.sqrt(Math.pow(a.getValue(), 2) + Math.pow(b.getValue(), 2));
        }
    }

    public final double getTheta() {
        return theta;
    }

    public final void setTheta(double theta) {
        this.theta = theta;
    }

    /**
     * Ensures that correct theta values are used for operations with complex
     * numbers by normalizing the theta.
     */
    public final void refreshTheta() {
        /* DNE */
        if (a.isDNE() || b.isDNE()) {
            theta = Double.NaN;
        }

        /* Values */
        double aVal = a.getValue(), bVal = b.getValue();
        if (a.hasValue() && b.hasValue()) {
            if (!NumberUtils.precisionCheck(aVal, 0.0)) {
                theta = Math.atan(bVal / aVal);
                if (b.isNegative() && a.isNegative()) {
                    // Quadrant three
                    theta += Math.PI;
                } else if (a.isNegative()) {
                    // Quadrant two
                    theta -= Math.PI;
                }
            } else {
                theta = b.signum() * Math.PI / 2;
            }
            return;
        }

        /* Infinity */
        boolean realInf = a.isInfinity(), imInf = b.isInfinity();
        // infinitesimals would make it approach 0 or infinity faster
        if (realInf && imInf) {
            // Complex Infinity
            theta = Math.atan(b.signum() / a.signum());
            if (b.isNegative() && a.isNegative()) {
                // Quadrant three
                theta += Math.PI;
            } else if (a.isNegative()) {
                // Quadrant two
                theta -= Math.PI;
            }
            return;

        } else if (realInf) {
            // Only Real Infinity
            theta = 0;
            if (a.isNegative()) {
                theta += Math.PI;
            }
            return;

        } else if (imInf) {
            // Only Imaginary Infinity
            theta = b.signum() * Math.PI / 2;
            return;
        }

        /* Infinitesimals */
        boolean realDx = a.isInfinitesimal(), imDx = b.isInfinitesimal();
        if (realDx && imDx) {
            theta = Math.PI / 4;
        } else if (realDx) {
            theta = b.signum() * Math.PI / 2;
        } else if (imDx) {
            theta = 0;
        }

    }
    // #endregion

    // #region Number Type Bools
    /**
     * A number is complex if both <b>a</b> and <b>b</b> are non-zero values.
     * 
     * @return
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
     *         or if it is not rational. {@code true} if otherwise.
     */
    public boolean isRational() {
        return isRational(Constants.Number.SIG_FIGS);
    }

    /**
     * 
     * @return
     */
    public boolean hasUncountable() {
        return !a.hasValue() || !b.hasValue();
    }

    public boolean isDNE() {
        return a.isDNE() || b.isDNE();
    }
    // #endregion

    // #region isZero Bools
    /**
     * Checks if both a and b are zero.
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

    public final boolean isNegative() {
        // TODO: rework
        return lessThan(new ComplexNum(0, 0));
    }

    /**
     * Performs a deep copy of the complex number, and then negates the copy.
     * 
     * @return
     */
    public final ComplexNum negate() {
        return new ComplexNum(a.negate(), b.negate());
    }

    // #region Addition
    private final void add(ComplexNum other) {
        a.add(other.getA());
        b.add(other.getB());
    }

    /**
     * Adds <b>n</b> complex numbers. Adds all of the real parts together, and then
     * all of the imaginary parts together. The following is implemented into the
     * method:
     * <ul>
     * <li>Uncountable addition
     * <li>DNE
     * </ul>
     * <p>
     * <em>Does not modify any of the addends' data</em>
     * 
     * @param addends
     * @return
     */
    public static final ComplexNum add(ComplexNum... addends) {
        if (addends == null || addends.length == 0) {
            return new ComplexNum(0, 0);
        } else if (addends.length == 1) {
            return addends[0];
        } else if (NumberUtils.containsDNE(addends)) {
            return new ComplexNum();
        }

        ComplexNum output = addends[0];
        for (int i = 1; i < addends.length; i++) {
            output.add(addends[i]);
        }

        /* Ensure correct Polar Form data */
        output.refreshModulus();
        output.refreshTheta();

        return output;
    }

    // #endregion

    // #region Multiplication
    /**
     * Multiplies one number to <b>this</b>.
     * <ul>
     * <li>if two numbers are real, it performs normal multiplication.
     * <li>if two numbers are imaginary, it performs normal
     * multiplication -> ai*bi
     * = -ab
     * <li>if there is a mismatch or one number is complex, then it
     * performs the
     * following equation:
     * {@code (r_1 * r_2 * cos(theta_1 + theta_2)) + (r_1 * r_2 * i * sin(theta_1 + theta_2))}
     * </ul>
     * 
     * 
     * @param factors a list of BNumbers.
     */
    private final void multiply(ComplexNum factor) {
        double cTheta = getTheta() + factor.getTheta(); // combinedTheta

        /* Uncountable */
        if (hasUncountable() || factor.hasUncountable()) {
            // TODO: get highest infinity
            copy(uncountableCis(Value.infinity(false, 1), cTheta));
            return;
        }

        /* Real */
        if (isReal() && factor.isReal()) {
            a.multiply(factor.getA());
            refreshPolar();

        } else if (factor.isImaginary() && isImaginary()) {
            setA(-1 * b.getValue() * factor.getB().getValue());
            setB(0);
            refreshPolar();

        } else {
            // mismatch / one is complex
            double combinedModulus = modulus * factor.getModulus();

            copy(fromPolar(combinedModulus, cTheta));
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
        refreshPolar();
    }

    /**
     * Multiplies <b>n</b> complex numbers together.
     * <p>
     * The following are implemented:
     * <ul>
     * <li>Uncountable addition
     * <li>DNE
     * </ul>
     * 
     * @param factors
     * @return
     */
    public static final ComplexNum multiply(ComplexNum... factors) {
        if (factors == null || factors.length == 0) {
            return ComplexNum.zero();
        } else if (factors.length == 1) {
            return factors[0];
        } else if (NumberUtils.containsDNE(factors)) {
            return new ComplexNum();
        }

        ComplexNum output = factors[0].clone();
        for (int index = 1; index < factors.length; index++) {
            output.multiply(factors[index]);
        }

        return output;
    }

    // #endregion

    // #region Division

    /**
     * Divides <b>this</b> by a dividend.
     * <ul>
     * <li>if two numbers are real, it performs normal division.
     * <li>if two numbers are imaginary, it performs normal division ->
     * ai/bi
     * = a/b
     * <li>if there is a mismatch or one number is complex, then it
     * performs the
     * following equation:
     * {@code ((r_1 / r_2) * cos(theta_1 - theta_2)) + ((r_1 / r_2) * i * sin(theta_1 - theta_2))}
     * </ul>
     * 
     * 
     * @param factors a list of BNumbers.
     */
    private final void divide(ComplexNum dividend) {
        if (isZero()) {
            return;
        } else if (dividend.isZero()) {
            return;
        }

        /* Variables */
        double cTheta = getTheta() - dividend.getTheta(); // combinedTheta

        /* Uncountables */
        if (hasUncountable()) {
            // TODO: get highest infinity
            copy(uncountableCis(Value.infinity(false, 1), cTheta));
            return;
        } else if (dividend.hasUncountable()) {
            copy(new ComplexNum(0, 0));
            return;
        }

        /* Values */
        if (dividend.isReal() && isReal()) {
            a.divide(dividend.getA());
            refreshPolar();

        } else if (dividend.isImaginary() && isImaginary()) {
            setA(b.getValue() / dividend.getB().getValue());
            setB(0);
            refreshPolar();

        } else {
            // mismatch / one is complex
            double combinedModulus = modulus / dividend.getModulus();

            copy(fromPolar(combinedModulus, cTheta));
        }
    }

    /**
     * Divides the divisor by <b>n</b> dividends. A deep copy is not required to
     * call this method. The following are implemented:
     * 
     * <ul>
     * <li>Uncountable division
     * <li>DNE
     * </ul>
     * 
     * @param divisor
     * @param dividends
     * @return
     */
    public static final ComplexNum divide(ComplexNum divisor, ComplexNum... dividends) {
        if (divisor == null || dividends == null || dividends.length == 0) {
            return ComplexNum.zero();
        } else if (NumberUtils.containsDNE(dividends) || divisor.isDNE()) {
            return new ComplexNum();
        }

        /* Arithmetic */
        ComplexNum output = divisor.clone();
        for (ComplexNum i : dividends) {
            output.divide(i);
        }
        return output;
    }
    // #endregion

    // #region Powers
    /**
     * Provides a deep copy of the number as a reciprocal.
     * 
     * @return {@code 1 / this}
     */
    public final ComplexNum reciprocal() {
        ComplexNum output = clone();
        output = ComplexNum.divide(new ComplexNum(1, 0), output);
        return output;
    }

    /**
     * 
     * Applies the nth real power to a complex number.
     * 
     * 
     * @param power A real number. Cannot be Double.POSITIVE_INFINITY,
     *              Double.NEGATIVE_INFINITY, nor Double.NaN.
     * @return
     */
    private final void power(double power) {
        loggy.logHeader("complex ^ real: (" + this + ")^(" + power + ")");

        /* Reciprocate if needed */
        if (power < 0) {
            copy(reciprocal());
            power *= -1;
        }

        if (isReal()) {
            if (!a.isNegative()) {
                // positive a so no problems :D
                a.power(power);
                refreshPolar();
                return;
            }

            if (!NumberTheory.isRational(power)) {
                // if the power is irrational
                copy(fromPolar(Math.pow(-a.getValue(), power), (Math.PI * power)));
                return;
            }

            if (!NumberUtils.inOpenRange(power, 0, 1)) {
                // imaginary numbers only crop up if its in (0,1)
                a.power(power);
                refreshPolar();
                return;
            }

            int[] ratio = NumberTheory.findRatio(power);
            if (ratio[1] % 2 == 1) {
                // negative base doesn't work for some reason
                setA(-Math.pow(-a.getValue(), power));
                refreshPolar();
                return;

            } else if (ratio[1] == 2) {
                setB(Math.pow(-a.getValue(), power));
                setA(0);
                refreshPolar();
                return;
            }

            copy(fromPolar(Math.pow(-a.getValue(), power), Math.PI * power));

        } else if (isImaginary() && NumberTheory.isInteger(power)) {
            // multiplying by an imaginary number is rotating the number by PI / 2 rads on
            // the imaginary-real axis.

            if (power % 4 == 0) {
                // double check this
                setA(Math.pow(b.getValue(), power));
                setB(0);

            } else if (power % 2 == 0) {
                setA(-Math.pow(b.getValue(), power));
                setB(0);

            } else if (power % 4 == 3) {
                setA(0);
                setB(-Math.pow(b.getValue(), power));

            } else {
                b.power(power);
            }
            refreshModulus();
            refreshTheta();

        } else {
            // imaginary with non-int power / complex

            // DeMoivre's theorem -> rational or irrational, int power or non
            // int power
            copy(fromPolar(Math.pow(modulus, power), theta * power));
        }
    }

    // TODO: roots of a complex number

    /**
     * Gets the complex number of a real, imaginary, or complex base to a real
     * power. The following are implemented:
     * <ul>
     * <li>Uncountable power
     * <li>Irrational powers
     * <li>DNE
     * </ul>
     * 
     * @param base
     * @param power
     * @return
     */
    public static final ComplexNum pow(ComplexNum base, double power) {
        ComplexNum temp = base.clone();
        temp.power(power);
        return temp;
    }

    /**
     * Gets the complex number of a real base to a real, imaginary, or complex
     * power. The following are implemented:
     * <ul>
     * <li>Uncountable power
     * <li>Irrational powers
     * <li>DNE
     * </ul>
     * 
     * <p>
     * <em>Calling pow() does not affect the data of <b>power</b></em>.
     * 
     * @param base
     * @param power
     * @return
     */
    public static final ComplexNum pow(double base, ComplexNum power) {
        loggy.logHeader("real^complex: (" + base + ")^(" + power + ")");

        /* DNE */
        if (power.isDNE()) {
            return power;
        }

        /* Infinity Power */
        if (power.hasUncountable()) {
            return NumberUtils.powWithInf(base, power);
        }

        /* Complex Power */
        double aPow = power.getA().getValue(), bPow = power.getB().getValue();
        double aOut, bOut;
        if (power.isReal()) {
            ComplexNum baseNum = new ComplexNum(base, 0);
            baseNum.power(aPow);
            return baseNum;

        } else if (power.isImaginary()) {
            if (base < 0) {
                // negative base
                ComplexNum output = pow(-base, power);
                if (bPow % 2 == 0) {
                    // (-1)^2i = 1^i = 1
                    return output;
                } else if (bPow % 2 == 1) {
                    // (-1)^3i = (-1)^i = e^(-pi)
                    output.multiplyScalar(Constants.Number.NEG_ONE_TO_I);
                    return output;
                } else {
                    // (-1)^ni = (-1^n)^i
                    ComplexNum negOneToPow = fromPolar(1, Math.PI * bPow);
                    negOneToPow = pow(negOneToPow, new ComplexNum(0, 1));
                    return ComplexNum.multiply(output, negOneToPow);
                }

            } else {
                aOut = Math.cos(bPow * Math.log(base));
                bOut = Math.sin(bPow * Math.log(base));
                return new ComplexNum(aOut, bOut);
            }

        } else {
            // complex power
            if (base < 0) {
                // negative base
                ComplexNum output = pow(-base, power);
                ComplexNum negOneA = negOneToPower(aPow);
                ComplexNum negOneB = negOneToPower(bPow);
                negOneB = pow(negOneB, new ComplexNum(0, 1));

                return multiply(output, negOneA, negOneB);

            } else {
                double tTheta = bPow * Math.log(base); // temp theta
                double tModulus = Math.exp(aPow * Math.log(base));
                return fromPolar(tModulus, tTheta);
            }
        }
    }

    /**
     * Gets the complex number of a real, imaginary, or complex base to a real,
     * imaginary, or complex power. The following are implemented:
     * <ul>
     * <li>Uncountable base and/or uncountable power
     * <li>Irrational numbers
     * <li>DNE
     * </ul>
     * 
     * @param base
     * @param power
     * @return
     */
    public static final ComplexNum pow(ComplexNum base, ComplexNum power) {
        loggy.logHeader("complex ^ complex: (" + base + ")^(" + power + ")");

        /* DNE */
        if (base.isDNE() || power.isDNE()) {
            return new ComplexNum();
        }

        /* Uncountable */
        if (power.hasUncountable() && base.isReal()) {
            return NumberUtils.powWithInf(base.getA().getValue(), power);

        } else if (power.hasUncountable()) {
            return new ComplexNum();

        } else if (base.hasUncountable()) {
            if (power.hasUncountable()) {
                // cis(r2 * theta1 * cos(theta2)) = cis(theta1 * a2) = DNE
                // r1^(a+bi), b cannot be infinity
                return new ComplexNum();
            }

            /**
             * e^(-r2 * theta1 * sin(theta2)) = e^(-theta1 * b2)
             */
            double expMod = Math.exp(-power.getModulus() * base.getTheta() * Math.sin(power.getTheta()));

            /**
             * r2 * theta1 * cos(theta2) = theta1 * a2
             */
            double theta = power.getModulus() * base.getTheta() * Math.cos(power.getTheta());
            return uncountableCis(Value.infinity(expMod < 0, 1), theta);
        }

        /* Complex */
        if (base.isReal()) {
            return pow(base.getA().getValue(), power);

        } else if (power.isReal()) {
            ComplexNum baseCopy = base.clone();
            baseCopy.power(power.getA().getValue());
            return baseCopy;

        } else {
            // (complex or imaginary) ^ (complex or imaginary)
            ComplexNum modOneToPow = pow(base.getModulus(), power);
            /**
             * e^(-theta1 * r2 * sin(theta2))
             */
            double cisMod = Math.exp(-1 * power.getModulus() * Math.sin(power.getTheta()) * base.getTheta());

            /**
             * r2 * cos(theta2) * theta 1
             */
            double cisTheta = power.getModulus() * Math.cos(power.getTheta()) * base.getTheta();
            ComplexNum cisToPow = fromPolar(cisMod, cisTheta);
            return ComplexNum.multiply(modOneToPow, cisToPow);
        }
    }
    // #endregion

    // #region Modulus
    public final ComplexNum mod(double modulo) {
        if (isZero()) {
            return this;
        }

        if (isReal()) {
            return new ComplexNum(a.mod(modulo), 0);
        }
        throw new UnsupportedOperationException("Number " + this
                + " is not a real number, and I have yet to figure out mod with imaginary and complex numbers.");
    }

    public final ComplexNum mod(ComplexNum modulo) {
        if (isReal() && modulo.isReal()) {
            return new ComplexNum(a.mod(modulo.getA().getValue()), 0);
        }

        throw new UnsupportedOperationException();

    }
    // #endregion

    // #region Trigonometry
    /**
     * 
     */
    public final void sin() {

    }
    // #endregion

    // #region Comparison Bools
    /**
     * Checks if BNumber is equal to a real value.
     * 
     * <p>
     * Will return false if <b>this</b> is an imaginary number or a complex number.
     * 
     * @param value
     * @return
     */
    private boolean equals(double value) {
        if (isImaginary() || isComplex()) {
            return false;
        }

        return NumberUtils.precisionCheck(a.getValue(), value) && NumberUtils.precisionCheck(b.getValue(), 0);
    }

    /**
     * Check if <b>this</b> is equal to any object.
     * 
     */
    @Override
    public boolean equals(Object arg) {
        if (arg == null) {
            return false;
        } else if (arg instanceof Double) {
            // TODO: fix the "double does seem to relate to BNumber"
            return equals((double) arg);
        } else if (arg instanceof Integer) {
            // TODO: fix the "integer does seem to relate to BNumber"
            return equals((int) arg);
        } else if (arg instanceof ComplexNum) {
            /**
             * Checks if the real components are equal to each other and then the imaginary
             * components.
             */
            ComplexNum other = (ComplexNum) arg;
            return NumberUtils.precisionCheck(a.getValue(), other.getA().getValue())
                    && NumberUtils.precisionCheck(b.getValue(), other.getB().getValue());
        }
        return false;
    }

    /**
     * Compared with lexicographical ordering.
     * 
     * @param other
     * @return
     */
    public boolean lessThan(ComplexNum other) {
        if (other.isComplex() || isComplex()) {
            // TODO: learn lexicographical ordering
        }

        return a.compareTo(other.getA()) < 0;
    }

    public boolean lessThanEqual(ComplexNum other) {
        if (other.isComplex() || isComplex()) {
            // TODO: learn lexicographical ordering
        }

        return a.compareTo(other.getA()) <= 0;
    }

    public boolean greaterThan(ComplexNum other) {
        if (other.isComplex() || isComplex()) {
            // TODO: learn lexicographical ordering
        }

        return a.compareTo(other.getA()) > 0;
    }

    public boolean greaterThanEqual(ComplexNum other) {
        if (other.isComplex() || isComplex()) {
            // TODO: learn lexicographical ordering
        }

        return a.compareTo(other.getA()) >= 0;
    }
    // #endregion
}
