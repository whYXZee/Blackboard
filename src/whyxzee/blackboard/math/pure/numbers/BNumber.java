package whyxzee.blackboard.math.pure.numbers;

import java.util.Arrays;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.pure.numbers.BUncountable.UncountableType;
import whyxzee.blackboard.math.pure.terms.PowerTerm;

/**
 * A generic package made for real, imaginary, and complex numbers.
 * The package is built for the polar definition of complex numbers.
 * 
 * <p>
 * The functionality of this class has been checked on <b>6/74/2025</b> and
 * the following has changed:
 * <ul>
 * <li>power()
 */
public class BNumber {
    private static final boolean TELEMETRY_ON = Constants.TelemetryConstants.BNUMBER_TELEMETRY;

    /* Polar Definition of Complex Numbers */
    private double modulus;
    private double theta;

    /* Rectangular Definition */
    private double a; // real part of a number
    private double b; // imaginary part

    /**
     * Constructor for a complex number, formatted in rectangular form
     * ({@code a+bi}).
     * 
     * @param a the "real part" of a number
     * @param b the "imaginary part" of a number
     */
    public BNumber(double a, double b) {
        /* Rectangular Definition */
        this.a = a;
        this.b = b;

        /* Polar Definition */
        refreshModulus();
        refreshTheta();
    }

    /**
     * Static constructor for a complex number, formatted in the polar form
     * ({@code r*cis(theta)}) or the exponential form ({@code r*e^(theta*i)}).
     * 
     * @param modulus
     * @param theta
     * @return
     */
    public static final BNumber fromPolar(double modulus, double theta) {
        return new BNumber(modulus * Math.cos(theta), modulus * Math.sin(theta));
    }

    /**
     * Static constructor for a complex number that has a value of zero.
     * 
     * @return
     */
    public static final BNumber zero() {
        return new BNumber(0, 0);
    }

    @Override
    public String toString() {
        if (isZero()) {
            return "0";
        } else if (isReal()) {
            return NumberUtils.valueToString(a);
        } else if (isImaginary()) {
            return NumberUtils.valueToString(b) + Constants.Unicode.IMAGINARY_NUMBER;
        }

        /* Complex */
        String output = "";
        output += NumberUtils.valueToString(a);
        output += (b < 0) ? " - " : " + ";
        output += NumberUtils.valueToString((b < 0) ? -b : b);
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

    @Override
    /**
     * Provides a deep copy of the BNumber.
     */
    public BNumber clone() {
        return new BNumber(a, b);
    }

    public final PowerTerm toTerm() {
        return new PowerTerm(this);
    }

    //
    // Get & Set Methods
    //
    // #region Rectangular
    public final double getA() {
        return a;
    }

    public final void setA(double a) {
        this.a = a;
    }

    public final double getB() {
        return b;
    }

    public final void setB(double b) {
        this.b = b;
    }
    // #endregion

    // #region Polar / Exponential
    public final double getModulus() {
        return modulus;
    }

    public final void setModulus(double modulus) {
        this.modulus = modulus;
    }

    public final void refreshModulus() {
        if (Double.isInfinite(a) || Double.isInfinite(b)) {
            modulus = Double.POSITIVE_INFINITY;
            return;
        }

        if (a == 0) {
            modulus = Math.abs(b);
        } else if (b == 0) {
            modulus = Math.abs(a);
        } else {
            modulus = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
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
        if (Double.isInfinite(a) || Double.isInfinite(b)) {
            theta = BUncountable.thetaWithInfinity(a, b);
            return;
        }

        if (!NumberUtils.precisionCheck(getA(), 0.0)) {
            theta = Math.atan(b / a);
            if (b < 0 && a < 0) {
                // Quadrant three
                theta += Math.PI;
            } else if (a < 0) {
                // Quadrant two
                theta -= Math.PI;
            }
        } else {
            theta = Math.signum(b) * Math.PI / 2;
        }
    }
    // #endregion

    ///
    /// Boolean Methods
    ///
    // #region Number Type
    /**
     * A number is complex if both <b>a</b> and <b>b</b> are non-zero values.
     * 
     * @return
     */
    public boolean isComplex() {
        return !isAZero() && !isBZero();
    }

    /**
     * A number is imaginary if the <b>a</b> value is zero, while the <b>b</b> value
     * is a non-zero value.
     * 
     * @return {@code a == 0 && b != 0}
     */
    public boolean isImaginary() {
        return isAZero() && !isBZero();
    }

    /**
     * A number is real if the <b>a</b> value is a non-zero value while the <b>b</b>
     * value is a zero.
     * 
     * @return {@code a != 0 && b == 0};
     */
    public boolean isReal() {
        return !isAZero() && isBZero();
    }

    /**
     * 
     * @param sigFigs the accuracy that should be checked, used for
     *                {@link whyxzee.blackboard.math.pure.numbers.NumberUtils#withinEpsilon(double, double, double)}.
     * @return {@code false} if the number is imaginary, if the number is complex,
     *         or if it is not rational. {@code true} if otherwise.
     */
    public boolean isRational(int sigFigs) {
        if (isImaginary() || isComplex()) {
            return false;
        }
        return NumberUtils.isRational(a, sigFigs);
    }

    /**
     * Checks if the number is a rational number using the
     * {@link whyxzee.blackboard.Constants.NumberConstants#SIG_FIGS} number of sig
     * figs.
     * 
     * @return {@code false} if the number is imaginary, if the number is complex,
     *         or if it is not rational. {@code true} if otherwise.
     */
    public boolean isRational() {
        return isRational(Constants.NumberConstants.SIG_FIGS);
    }

    /**
     * 
     * @return
     */
    public boolean isUncountable() {
        return false;
    }

    public boolean isDNE() {
        return false;
    }
    // #endregion

    // #region Zeros
    /**
     * Checks if both a and b are zero.
     * 
     * @return
     */
    public boolean isZero() {
        return isAZero() && isBZero();
    }

    public final boolean isAZero() {
        return NumberUtils.precisionCheck(a, 0);
    }

    public final boolean isBZero() {
        return NumberUtils.precisionCheck(b, 0);
    }
    // #endregion

    public final boolean isNegative() {
        return lessThan(new BNumber(0, 0));
    }

    ///
    /// Operations
    ///
    /**
     * Performs a deep copy of the complex number, and then negates the copy.
     * 
     * @return
     */
    public final BNumber negate() {
        return new BNumber(-a, -b);
    }

    // #region Addition & Subtraction
    /**
     * Adds one complex numbers to <b>this</b>. Adds all the real parts
     * together, then all of the imaginary parts together. The following
     * is implemented into the method:
     * 
     * @param addend a BNumber.
     */
    private final void add(BNumber addend) {
        a += addend.getA();
        b += addend.getB();
    }

    /**
     * Adds <b>n</b> complex numbers. Adds all of the real parts together, and then
     * all of the imaginary parts together. The following is implemented into the
     * method:
     * <ul>
     * <li>Uncountable addition
     * <li>DNE
     * </ul>
     * 
     * @param addends
     * @return
     */
    public static final BNumber add(BNumber... addends) {
        if (addends == null || addends.length == 0) {
            return new BNumber(0, 0);
        } else if (addends.length == 1) {
            return addends[0];
        } else if (NumberUtils.containsDNE(addends)) {
            return new DoesNotExist();
        }

        BNumber output = new BNumber(0, 0);
        for (BNumber i : addends) {
            /* Uncountable Addition */
            if (output.isUncountable()) {
                output = BUncountable.add((BUncountable) output, i);
                continue;
            }

            /* Normal Addition */
            if (i.isUncountable()) {
                output = BUncountable.add((BUncountable) i, output);
            } else {
                output.add(i);
            }
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
    private final void multiply(BNumber factor) {
        if (factor.isReal() && isReal()) {
            setA(a * factor.getA());
            refreshModulus();

        } else if (factor.isImaginary() && isImaginary()) {
            setA(-1 * b * factor.getB());
            setB(0);
            refreshModulus();
            setTheta(0);

        } else {
            // mismatch / one is complex
            double combinedModulus = modulus * factor.getModulus();
            double combinedTheta = theta + factor.getTheta();

            a = combinedModulus * Math.cos(combinedTheta);
            b = combinedModulus * Math.sin(combinedTheta);
            refreshModulus();
            refreshTheta();
        }
    }

    /**
     * Multiplies the real and imaginary parts of the complex number by a real
     * value.
     * 
     * @param value
     */
    public final void multiplyScalar(double value) {
        setA(a * value);
        setB(b * value);
        refreshModulus();
        refreshTheta(); // might not be needed, cuz it should be the same ratio
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
    public static final BNumber multiply(BNumber... factors) {

        if (factors == null || factors.length == 0) {
            return BNumber.zero();
        } else if (factors.length == 1) {
            return factors[0];
        } else if (NumberUtils.containsDNE(factors)) {
            return new DoesNotExist();
        }

        /* Telemetry */
        if (TELEMETRY_ON)
            System.out.println("----Multiply with " + Arrays.toString(factors) + "----");

        BNumber output = factors[0].clone();
        for (int index = 1; index < factors.length; index++) {
            BNumber i = factors[index];

            /* Telemetry */
            if (TELEMETRY_ON)
                System.out.println("output: " + output + " index: " + i);

            /* Uncountable Multiplication */
            if (output.isUncountable()) {
                /* Telemetry */
                if (TELEMETRY_ON)
                    System.out.println("- Output is uncountable.");

                /* Arithmetic */
                output = BUncountable.multiply((BUncountable) output, i);
                continue;
            }

            /* Complex Multiplication */
            if (i.isUncountable()) {
                /* Telemetry */
                if (TELEMETRY_ON)
                    System.out.println("- Index is uncountable.");

                /* Arithmetic */
                output = BUncountable.multiply((BUncountable) i, output);

            } else { // checked
                /* Telemetry */
                if (TELEMETRY_ON)
                    System.out.println("- Both are countable.");

                /* Arithmetic */
                output.multiply(i);
            }
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
    private final void divide(BNumber dividend) {
        if (isZero()) {
            return;
        }

        if (dividend.isZero()) {
            return;
        }

        if (dividend.isReal() && isReal()) {
            a = a / dividend.getA();
            refreshModulus();

        } else if (dividend.isImaginary() && isImaginary()) {
            a = b / dividend.getB();
            b = 0;
            refreshModulus();
            theta = 0;

        } else {
            // mismatch / one is complex
            double combinedModulus = modulus / dividend.getModulus();
            double combinedTheta = theta - dividend.getTheta();

            a = combinedModulus * Math.cos(combinedTheta);
            b = combinedModulus * Math.sin(combinedTheta);
            refreshModulus();
            refreshTheta();
        }
    }

    /**
     * Divides the divisor by <b>n</b> dividends. The following are implemented:
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
    public static final BNumber divide(BNumber divisor, BNumber... dividends) {
        if (divisor == null || dividends == null || dividends.length == 0) {
            return BNumber.zero();
        } else if (NumberUtils.containsDNE(dividends) || divisor.isDNE()) {
            return new DoesNotExist();
        }

        /* Telemetry */
        if (TELEMETRY_ON)
            System.out.println("----Divide " + divisor + " by " + Arrays.toString(dividends) + "----");

        /* Arithmetic */
        BNumber output = divisor.clone();
        for (BNumber i : dividends) {
            /* Uncountable Division */
            if (output.isUncountable()) {
                /* Telemetry */
                if (TELEMETRY_ON)
                    System.out.println(" - output is uncountable, index uncountable: " + i.isUncountable());

                /* Arithmetic */
                if (i.isUncountable()) {
                    // infinity / infinity
                    /* Limit + L'Hospital's */
                    double multiplier = Math.signum(output.getModulus()) * Math.signum(i.getModulus());

                    /* Solely the cis(theta1 - theta2) */
                    output = fromPolar(1, output.getTheta());
                    output.divide(fromPolar(1, i.getTheta()));
                    output.multiplyScalar(multiplier);

                } else {
                    // infinity / number
                    output = BUncountable.divide((BUncountable) output, output.getTheta() - i.getTheta());
                }
                continue;
            }

            /* Complex Division */
            if (i.isUncountable()) {
                // number / infinity

                /* Telemetry */
                if (TELEMETRY_ON)
                    System.out.println(" - output is not uncountable, index is uncountable.");

                /* Arithmetic */
                return BNumber.zero();
            } else {
                /* Telemetry */
                if (TELEMETRY_ON)
                    System.out.println(" - both are complex numbers.");

                output.divide(i);
            }

        }
        return output;
    }
    // #endregion

    // #region Powers
    /**
     * 
     * Applies the nth power to a number.
     * 
     * 
     * @param power
     * @return
     */
    private final void power(double power) {
        // the power cannot be an uncountable lol, it accepts a primative and not a
        // non-primative

        if (isReal()) {
            if (a < 0) {
                // negative base
                setB(Math.pow(-a, power));
            } else {
                setA(Math.pow(a, power));
            }
            refreshModulus();
            refreshTheta();

        } else if (isImaginary() && NumberUtils.isInteger(power)) {
            // multiplying by an imaginary number is rotating the number by PI / 2 rads on
            // the imaginary-real axis.

            if (power % 4 == 0) {
                // double check this
                setA(Math.pow(b, power));
                setB(0);

            } else if (power % 2 == 0) {
                setA(-Math.pow(b, power));
                setB(0);

            } else if (power % 4 == 3) {
                setA(0);
                setB(-Math.pow(b, power));

            } else {
                setB(Math.pow(b, power));
            }
            refreshModulus();
            refreshTheta();

        } else {
            // imaginary with non-int power / complex

            if (NumberUtils.isInteger(power) && power > 0) {
                // DeMoivre's Theorem
                setModulus(Math.pow(modulus, power));
                setTheta(theta * power);
                setA(modulus * Math.cos(theta));
                setB(modulus * Math.sin(theta));

            } else {
                // non-int power of a complex/imaginary number, as well as negative powers
                BNumber temp = pow(this, new BNumber(power, 0));
                setModulus(temp.getModulus());
                setTheta(temp.getTheta());
                setA(temp.getA());
                setB(temp.getB());
            }
        }
    }

    // TODO: roots of a complex number

    /**
     * Gets the complex number of a real base to a real, imaginary, or complex
     * power. The following are implemented:
     * <ul>
     * <li>Uncountable power
     * <li>DNE
     * </ul>
     * 
     * @param base
     * @param power
     * @return
     */
    public static final BNumber pow(double base, BNumber power) {
        /* DNE */
        if (power.isDNE()) {
            return power;
        }

        /* Infinity Power */
        if (power.isUncountable()) {
            return BUncountable.pow(base, (BUncountable) power);
        }

        /* Complex Power */
        double aPow = power.getA(), bPow = power.getB();
        double aOut, bOut;
        if (power.isReal()) {
            if (!(0 < Math.abs(aPow)) || !(Math.abs(aPow) < 1)) {
                // !(0 < |pow| < 1)
                return new BNumber(Math.pow(base, aPow), 0);
            }

            /* 0 < |pow| < 1 */
            int[] ratio = NumberUtils.findRatio(aPow);
            if (ratio[1] % 2 == 0 && base < 0) {
                // imaginary values are needed
                return new BNumber(0, Math.pow(-base, aPow));
            }
            return new BNumber(Math.pow(base, aPow), 0);

        } else if (power.isImaginary()) {
            if (base < 0) {
                // negative base
                BNumber output = pow(-base, power);
                if (Math.pow(-1, bPow) < 0) {
                    output.multiplyScalar(Constants.NumberConstants.NEG_ONE_TO_I);
                }
                return output;

            } else {
                aOut = Math.cos(bPow * Math.log(base));
                bOut = Math.sin(bPow * Math.log(base));
                return new BNumber(aOut, bOut);
            }

        } else {
            // complex power
            if (base < 0) {
                // negative base
                BNumber output = pow(-base, power);
                aOut = Math.pow(-1, aPow);
                bOut = Math.pow(-1, bPow);
                if (bOut < 0) {
                    bOut = Constants.NumberConstants.NEG_ONE_TO_I;
                }
                output.multiplyScalar(aOut * bOut);
                return output;
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
     * <li>DNE
     * </ul>
     * 
     * @param base
     * @param power
     * @return
     */
    public static final BNumber pow(BNumber base, BNumber power) {
        BNumber baseCopy = base.clone();
        BNumber powerCopy = power.clone();

        /* DNE */
        if (base.isDNE() || power.isDNE()) {
            return new DoesNotExist();
        }

        /* Uncountable */
        if (power.isUncountable() && base.isReal()) {
            return BUncountable.pow(base.getA(), (BUncountable) power);

        } else if (power.isUncountable()) {
            return new DoesNotExist();

        } else if (base.isUncountable()) {
            return BUncountable.pow((BUncountable) base, power);

        }

        /* Complex */
        if (base.isReal()) {
            return pow(base.getA(), power);

        } else if (power.isReal()) {
            baseCopy.power(power.getA());
            return baseCopy;

        } else {
            // (complex or imaginary) ^ (complex or imaginary)
            BNumber modOneToPow = pow(base.getModulus(), power);
            /**
             * e^(-theta1 * r2 * sin(theta2))
             */
            double cisMod = Math.exp(-1 * power.getModulus() * Math.sin(power.getTheta()) * base.getTheta());

            /**
             * r2 * cos(theta2) * theta 1
             */
            double cisTheta = power.getModulus() * Math.cos(power.getTheta()) * base.getTheta();
            BNumber cisToPow = fromPolar(cisMod, cisTheta);
            return BNumber.multiply(modOneToPow, cisToPow);
        }
    }
    // #endregion

    // #region Modulus
    public final BNumber mod(double modulo) {
        if (isZero()) {
            return this;
        }

        if (isReal()) {
            return new BNumber(a % modulo, 0);
        }
        throw new UnsupportedOperationException("Number " + this
                + " is not a real number, and I have yet to figure out mod with imaginary and complex numbers.");
    }

    public final BNumber mod(BNumber modulo) {
        if (isReal() && modulo.isReal()) {
            return new BNumber(a % modulo.getA(), 0);
        }

        throw new UnsupportedOperationException();

    }
    // #endregion

    ///
    /// Equality & Inequality
    ///
    // #region
    public boolean equals(double value) {
        if (isImaginary() || isComplex()) {
            return false;
        }

        return NumberUtils.precisionCheck(a, value) && NumberUtils.precisionCheck(b, 0);
    }

    public boolean equals(BNumber other) {
        return NumberUtils.precisionCheck(a, other.getA()) && NumberUtils.precisionCheck(b, other.getB());
    }

    /**
     * Compared with lexicographical ordering.
     * 
     * @param other
     * @return
     */
    public boolean lessThan(BNumber other) {
        if (other.isComplex() || isComplex()) {
            // TODO: learn lexicographical ordering
        }

        return a < other.getA();
    }

    public boolean lessThanEqual(BNumber other) {
        if (other.isComplex() || isComplex()) {
            // TODO: learn lexicographical ordering
        }

        return a <= other.getA();
    }

    public boolean greaterThan(BNumber other) {
        if (other.isComplex() || isComplex()) {
            // TODO: learn lexicographical ordering
        }

        return a > other.getA();
    }

    public boolean greaterThanEqual(BNumber other) {
        if (other.isComplex() || isComplex()) {
            // TODO: learn lexicographical ordering
        }

        return a >= other.getA();
    }
    // #endregion
}
