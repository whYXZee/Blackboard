package whyxzee.blackboard.math.pure.numbers;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.pure.terms.PowerTerm;

/**
 * A generic package made for real, imaginary, and complex numbers.
 * The package is built for the polar definition of complex numbers.
 * 
 * <p>
 * The functionality of this class has been checked on <b>6/14/2025</b> and
 * nothing has changed since.
 */
public class BNumber {
    /* Polar Definition of Complex Numbers */
    private double modulus;
    private double theta;

    /* Variables */
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

    @Override
    public String toString() {
        if (isZero()) {
            return "0";
        }
        if (isReal()) {
            return NumberUtils.valueToString(a);
        }
        if (isImaginary()) {
            return NumberUtils.valueToString(b) + Constants.Unicode.IMAGINARY_NUMBER;
        }

        /* Complex */
        String output = "";
        if (!NumberUtils.withinEpsilon(a, 0, Math.pow(10, -2 * Constants.NumberConstants.SIG_FIGS))) {
            output += NumberUtils.valueToString(a);
            output += (b < 0) ? " - " : " + ";
        }
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

        return NumberUtils.valueToString(modulus) + "cis(" + NumberUtils.valueToString(theta) + "/2)";
    }

    @Override
    public BNumber clone() {
        return new BNumber(a, b);
    }

    public final PowerTerm toTerm() {
        return new PowerTerm(this);
    }

    //
    // Get & Set Methods
    //
    // #region
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

    public final double getModulus() {
        return modulus;
    }

    public final void setModulus(double modulus) {
        this.modulus = modulus;
    }

    public final void refreshModulus() {
        if (Double.isInfinite(a) || Double.isInfinite(b)) {
            modulus = Double.POSITIVE_INFINITY;
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
        if (!NumberUtils.precisionCheck(getA(), 0.0)) {
            theta = Math.atan(b / a);
            if (b < 0 && a < 0) {
                // Quadrant three
                theta += Math.PI;
            } else if (a < 0) {
                theta -= Math.PI;
                // Quadrant two
            }
        } else {
            theta = Math.signum(b) * Math.PI / 2;
        }
    }
    // #endregion

    ///
    /// Boolean Methods
    ///
    // #region
    public boolean isComplex() {
        return !isAZero() && !isBZero();
    }

    public boolean isImaginary() {
        return isAZero() && !isBZero();
    }

    /**
     * 
     * @return {@code a == 0 && b != 0};
     */
    public boolean isReal() {
        return !isAZero() && isBZero();
    }

    public boolean isZero() {
        return isAZero() && isBZero();
    }

    public final boolean isAZero() {
        return NumberUtils.precisionCheck(a, 0.0);
    }

    public final boolean isBZero() {
        return NumberUtils.precisionCheck(getB(), 0.0);
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

    public boolean isUncountable() {
        return false;
    }

    public final boolean isNegative() {
        return lessThan(new BNumber(0, 0));
    }
    // #endregion

    ///
    /// Operations
    ///
    // #region
    /**
     * Performs a deep copy of the complex number, and then negates the copy.
     * 
     * @return
     */
    public final BNumber negate() {
        return new BNumber(-a, -b);
    }

    /**
     * Adds <b>n</b> number of numbers together. Adds all the real parts together,
     * then all of the imaginary parts together.
     * 
     * @param addends a list of BNumbers.
     */
    public final void add(BNumber... addends) {
        if (addends == null) {
            return;
        }

        for (BNumber i : addends) {
            a += i.getA();
            b += i.getB();
        }
    }

    /**
     * Multiplies <b>n</b> number of numbers together.
     * <ul>
     * <li>if two numbers are real, it performs normal multiplication.
     * <li>if two numbers are imaginary, it performs normal multiplication -> ai*bi
     * = -ab
     * <li>if there is a mismatch or one number is complex, then it performs the
     * following equation:
     * {@code (r_1 * r_2 * cos(theta_1 + theta_2)) + (r_1 * r_2 * i * sin(theta_1 + theta_2))}
     * </ul>
     * 
     * @param factors a list of BNumbers.
     */
    public final void multiply(BNumber... factors) {
        if (factors == null) {
            return;
        }

        for (BNumber i : factors) {
            if (i.isReal() && isReal()) {
                setA(a * i.getA());
                refreshModulus();

            } else if (i.isImaginary() && isImaginary()) {
                setA(-1 * b * i.getB());
                setB(0);
                refreshModulus();
                setTheta(0);

            } else {
                // mismatch / one is complex
                double combinedModulus = modulus * i.getModulus();
                double combinedTheta = theta + i.getTheta();

                setA(combinedModulus * Math.cos(combinedTheta));
                setB(combinedModulus * Math.sin(combinedTheta));
                refreshModulus();
                refreshTheta();
            }
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
     * A static division class for complex numbers. The method does a deep copy of
     * <b>divisor</b> and then uses the
     * {@link whyxzee.blackboard.math.pure.numbers.BNumber#divide(BNumber...)}
     * method.
     * 
     * @param divisor
     * @param dividend
     * @return
     */
    public static final BNumber divide(BNumber divisor, BNumber dividend) {
        BNumber output = divisor.clone();
        output.divide(dividend);
        return output;
    }

    /**
     * Divides <b>n</b> number of complex numbers together.
     * <ul>
     * <li>if two numbers are real, it performs normal division.
     * <li>if two numbers are imaginary, it performs normal division -> ai/bi
     * = a/b
     * <li>if there is a mismatch or one number is complex, then it performs the
     * following equation:
     * {@code ((r_1 / r_2) * cos(theta_1 - theta_2)) + ((r_1 / r_2) * i * sin(theta_1 - theta_2))}
     * </ul>
     * 
     * @param factors a list of BNumbers.
     */
    public final void divide(BNumber... dividends) {
        if (dividends == null) {
            return;
        }

        if (isZero()) {
            return;
        }

        for (BNumber i : dividends) {
            if (i.isZero()) {
                break;
            }

            if (i.isReal() && isReal()) {
                setA(a / i.getA());
                refreshModulus();

            } else if (i.isImaginary() && isImaginary()) {
                setA(b / i.getB());
                setB(0);
                refreshModulus();
                setTheta(0);

            } else {
                // mismatch / one is complex
                double combinedModulus = modulus / i.getModulus();
                double combinedTheta = theta - i.getTheta();

                setA(combinedModulus * Math.cos(combinedTheta));
                setB(combinedModulus * Math.sin(combinedTheta));
                refreshModulus();
                refreshTheta();
                System.out.println("modulus: " + combinedModulus + " theta: " + combinedTheta);
            }
        }
    }

    /**
     * Applies the pth power to a number.
     */
    public final void power(double power) {
        // TODO: what happens if it is an uncountable to a power?

        if (isReal()) {
            if (a < 0) {
                // negative base
                setB(Math.pow(-a, power));
            } else {
                setA(Math.pow(a, power));
            }
            refreshModulus();
            refreshTheta();

        } else if (isImaginary()) {
            if (power % 2 == 0) {
                setA(-Math.pow(b, power));
                setB(0);
            } else {
                setB(Math.pow(b, power));
            }
            refreshModulus();
            refreshTheta();

        } else {
            // is complex :gulp:

            if (NumberUtils.isInteger(power) && power > 0) {
                // DeMoivre's Theorem
                setModulus(Math.pow(modulus, power));
                setTheta(theta * power);
                setA(modulus * Math.cos(theta));
                setB(modulus * Math.sin(theta));

            } else {
                // TODO: non-int power of a complex number, as well as negative powers

            }
        }
    }

    // TODO: roots of a complex number

    /**
     * Gets the complex number of a real base to a real, imaginary, or complex
     * power.
     * 
     * @param base
     * @param power
     * @return
     */
    public static final BNumber pow(double base, BNumber power) {
        double aPow = power.getA(), bPow = power.getB();
        double aOut, bOut;
        if (power.isReal()) {
            if (!(0 < Math.abs(aPow)) || !(Math.abs(aPow) < 1)) {
                return new BNumber(Math.pow(base, aPow), 0);
            }

            int[] ratio = NumberUtils.findRatio(aPow);
            if (ratio[1] % 2 == 0 && base < 0) {
                return new BNumber(0, Math.pow(-base, aPow));
            }
            return new BNumber(Math.pow(base, aPow), 0);
        }

        if (power.isImaginary()) {
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
                BNumber temp = pow(-base, power);
                aOut = Math.pow(-1, aPow);
                bOut = Math.pow(-1, bPow);
                if (bOut < 0) {
                    bOut = Constants.NumberConstants.NEG_ONE_TO_I;
                }
                temp.multiplyScalar(aOut * bOut);
                return temp;
            } else {
                double tTheta = bPow * Math.log(base); // temp theta
                double tModulus = Math.exp(aPow * Math.log(base));
                return fromPolar(tModulus, tTheta);
            }
        }
    }

    /**
     * Gets the complex number of a real, imaginary, or complex base to a real,
     * imaginary, or complex power.
     * 
     * @param base
     * @param power
     * @return
     */
    public static final BNumber pow(BNumber base, BNumber power) {
        BNumber baseCopy = base.clone();
        BNumber powerCopy = power.clone();

        if (base.isReal()) {
            return pow(base.getA(), power);

        } else if (power.isReal()) {
            baseCopy.power(power.getA());
            return baseCopy;

        } else {
            // complex ^ complex
            BNumber modOneToPow = pow(base.getModulus(), power);
            double cisTheta = power.getModulus() * Math.cos(power.getTheta()) * base.getTheta();
            double cisMod = Math.exp(-1 * power.getModulus() * Math.sin(power.getTheta()) * base.getTheta());
            BNumber cisToPow = fromPolar(cisMod, cisTheta);
            modOneToPow.multiply(cisToPow);
            return modOneToPow;
        }
    }

    public final BNumber mod(double modulo) {
        if (isReal()) {
            return new BNumber(getA() % modulo, 0);
        }
        throw new UnsupportedOperationException();
    }

    public final BNumber mod(BNumber modulo) {
        if (isReal() && modulo.isReal()) {
            return new BNumber(getA() % modulo.getA(), 0);
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

        return NumberUtils.precisionCheck(a, value);
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
