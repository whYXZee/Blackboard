package whyxzee.blackboard.math.pure.numbers;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.pure.numbers.uncountables.GeneralInfinity;
import whyxzee.blackboard.utils.UnicodeUtils;

/**
 * <p>
 * The functionality of this class has been checked on <b>6/17/2025</b> and the
 * following has changed:
 * <ul>
 * <li>multiplication()
 * <li>division()
 * <li>power()
 */
public class BUncountable extends BNumber {
    /* Variables */
    /**
     * The size of the uncountable in relation to similar types.
     */
    private int realSize;
    private int imaginarySize;
    private UncountableType realType;
    private UncountableType imaginaryType;
    private String realChar;
    private String imaginaryChar;

    // #region Constructors
    public enum UncountableType {
        NONE(0),
        ALEPH(1),
        GEN_INFINITY(10);

        private final int order;

        private UncountableType(int order) {
            this.order = order;
        }

        public final int getOrder() {
            return order;
        }
    }

    /**
     * Constructor for a real uncountable number.
     * 
     * @param rVal  the value of the real uncountable.
     * @param rSize the size of the real uncountable.
     * @param rType the type of the real uncountable.
     * @param rChar the character that represents the uncountable.
     */
    public BUncountable(double rVal, int rSize, UncountableType rType, String rChar) {
        super(rVal, 0);
        this.realSize = rSize;
        this.realType = rType;
        this.realChar = rChar;
        this.imaginarySize = -1;
        this.imaginaryType = UncountableType.NONE;
        this.imaginaryChar = "";
    }

    /**
     * Constructor for a number which has a real and/or imaginary uncountable. It
     * can be mixed with numbers.
     * 
     * @param rVal  the value of the real uncountable.
     * @param rSize the size of the real uncountable.
     * @param rType the type of the real uncountable.
     * @param rChar tye character that represents the real uncountable.
     * @param iVal  the value of the imaginary uncountable.
     * @param iSize the size of the imaginary uncountable.
     * @param iType the type of the imaginary uncountable.
     * @param iChar the character that represents the imaginary uncountable.
     */
    private BUncountable(double rVal, int rSize, UncountableType rType, String rChar, double iVal, int iSize,
            UncountableType iType, String iChar) {
        super(rVal, iVal);
        this.realSize = rSize;
        this.realType = rType;
        this.realChar = rChar;
        this.imaginarySize = iSize;
        this.imaginaryType = iType;
        this.imaginaryChar = iChar;
    }

    public static final BNumber createCustomUncountable(BNumber real, BNumber imaginary) {
        if (!real.isUncountable() && !imaginary.isUncountable()) {
            return BNumber.add(real, imaginary);
        }

        if (real.isUncountable() && imaginary.isUncountable()) {
            BUncountable realUnc = (BUncountable) real;
            BUncountable imagUnc = (BUncountable) imaginary;
            return new BUncountable(realUnc.getA(), realUnc.getRealSize(), realUnc.getRealType(), realUnc.getRealChar(),
                    imagUnc.getA(), imagUnc.getRealSize(), imagUnc.getRealType(), imagUnc.getRealChar());
        }

        if (real.isUncountable()) {
            BUncountable realUnc = (BUncountable) real;
            return new BUncountable(realUnc.getA(), realUnc.getRealSize(), realUnc.getRealType(), realUnc.getRealChar(),
                    imaginary.getB(), -1, UncountableType.NONE, "");
        }
        BUncountable imagUnc = (BUncountable) imaginary;
        return new BUncountable(real.getA(), -1, UncountableType.NONE, "",
                imagUnc.getA(), imagUnc.getRealSize(), imagUnc.getRealType(), imagUnc.getRealChar());
    }

    /**
     * Creates a custom uncountable based on given polar form data.
     * 
     * @param mod   the modulus with the highest order/size of infinity
     * @param theta theta
     * @return
     */
    public static final BNumber uncountableCis(BUncountable mod, double theta) {
        /* Declaring variables */
        BNumber rVal, iVal;
        double cosVal = Math.cos(theta);
        double sinVal = Math.sin(theta);

        /* Real */
        if (!NumberUtils.precisionCheck(cosVal, 0)) {
            rVal = mod.clone();
            rVal.multiplyScalar(cosVal);
        } else {
            rVal = BNumber.zero();
        }

        /* Imaginary */
        if (!NumberUtils.precisionCheck(sinVal, 0)) {
            iVal = mod.clone();
            iVal.multiplyScalar(sinVal);
        } else {
            iVal = BNumber.zero();
        }

        return createCustomUncountable(rVal, iVal);
    }

    @Override
    public final String toString() {
        if (!isAZero() && isBZero()) {
            // "Real" infinity
            return (getA() < 0 ? "-" : "") + realChar + UnicodeUtils.intToSubscript(realSize);

        } else if (isAZero() && !isBZero()) {
            // "Imaginary" infinity
            return (getB() < 0 ? "-" : "") + imaginaryChar + UnicodeUtils.intToSubscript(imaginarySize)
                    + Constants.Unicode.IMAGINARY_NUMBER;
        }

        /* Complex Infinity */
        String output = "";
        if (isRealType(UncountableType.NONE)) {
            output += NumberUtils.valueToString(getA());
        } else {
            output += (getA() < 0 ? "-" : "") + realChar + UnicodeUtils.intToSubscript(realSize);
        }

        boolean isBNegative = getB() < 0;
        output += (isBNegative) ? " - " : " + ";
        if (isImaginaryType(UncountableType.NONE)) {
            output += NumberUtils.valueToString(isBNegative ? -getB() : getB());
        } else {
            output += imaginaryChar + UnicodeUtils.intToSubscript(imaginarySize);
        }
        output += Constants.Unicode.IMAGINARY_NUMBER;

        return output;
    }

    public final String polarString() {
        String output = "";
        if (!isAZero() && isBZero() && !isRealType(UncountableType.NONE)) {
            // real infinity
            output += (getA() < 0 ? "-" : "") + realChar + UnicodeUtils.intToSubscript(realSize);

        } else if (isAZero() && !isBZero() && !isImaginaryType(UncountableType.NONE)) {
            // imaginary infinity
            output += (getB() < 0 ? "-" : "") + imaginaryChar + UnicodeUtils.intToSubscript(imaginarySize);

        } else {
            // complex infinity
            if (isSmallerOrder(realType, imaginaryType)) {
                output += imaginaryChar + UnicodeUtils.intToSubscript(imaginarySize);
            }
            output += realChar;

            if (isEqualOrder(realType, imaginaryType)) {
                output += realChar;
                if (isSmallerSize(realSize, imaginarySize)) {
                    output += UnicodeUtils.intToSubscript(imaginarySize);
                } else {
                    output += UnicodeUtils.intToSubscript(realSize);
                }
            } else {
                output += UnicodeUtils.intToSubscript(realSize);
            }
        }

        /* CIS() part */
        output += "cis(" + NumberUtils.valueToString(getTheta()) + ")";
        return output;
    }

    @Override
    public final BUncountable clone() {
        return new BUncountable(getA(), realSize, realType, realChar, getB(), imaginarySize,
                imaginaryType, imaginaryChar);
    }

    ///
    /// Get & Set Methods
    ///
    // #region Polar / Exponential
    /**
     * Returns what the value of a complex uncountable number would be.
     * 
     * @param a
     * @param b
     * @return
     */
    public static final double thetaWithInfinity(double a, double b) {
        boolean realInf = Double.isInfinite(a), imInf = Double.isInfinite(b);
        double theta;

        if (realInf && imInf) {
            // Complex Infinity

            theta = Math.atan(Math.signum(b) / Math.signum(a));
            if (b < 0 && a < 0) {
                // Quadrant three
                theta += Math.PI;
            } else if (a < 0) {
                // Quadrant two
                theta -= Math.PI;
            }
            return theta;

        } else if (realInf) {
            // Only Real Infinity
            theta = 0;
            if (a < 0) {
                theta += Math.PI;
            }
            return theta;

        } else {
            // Only Imaginary Infinity
            return Math.signum(b) * Math.PI / 2;
        }

        // TODO: what if infinities are not inputted?
    }
    // #endregion

    // #region Uncountable Data
    public final int getRealSize() {
        return realSize;
    }

    public final UncountableType getRealType() {
        return realType;
    }

    public final String getRealChar() {
        return realChar;
    }

    public final int getImaginarySize() {
        return imaginarySize;
    }

    public final UncountableType getImaginaryType() {
        return imaginaryType;
    }

    public final String getImaginaryChar() {
        return imaginaryChar;
    }
    // #endregion

    public final BNumber getRealInf() {
        return new BUncountable(getA(), realSize, realType, realChar);
    }

    public final BNumber getImaginaryInf() {
        return new BUncountable(getB(), imaginarySize, imaginaryType, imaginaryChar, 0, -1,
                UncountableType.NONE, "");
    }

    ///
    /// Boolean Methods
    ///
    // #region Number Type
    @Override
    public final boolean isComplex() {
        return false;
    }

    @Override
    public final boolean isImaginary() {
        return false;
    }

    @Override
    public final boolean isReal() {
        return false;
    }

    @Override
    public final boolean isRational(int sigFigs) {
        return false;
    }

    @Override
    public final boolean isUncountable() {
        return true;
    }
    // #endregion

    public final boolean isRealType(UncountableType type) {
        return this.realType == type;
    }

    public final boolean isImaginaryType(UncountableType type) {
        return this.imaginaryType == type;
    }

    ///
    /// Operation Methods
    ///
    // #region Addition & Subtraction
    /**
     * Adds BUncountable to a BNumber or another BUncountable.
     * 
     * @param a
     * @param b
     * @return a custom BUncountable
     */
    public static final BNumber add(BUncountable a, BNumber b) {
        BNumber rVal, iVal;
        if (b.isUncountable()) {
            // both are uncountable
            BUncountable bInf = (BUncountable) b;

            /* Real */
            UncountableType aRType = a.getRealType(), bRType = bInf.getRealType();
            if (isSmallerOrder(aRType, bRType)) {
                rVal = bInf.getRealInf();
            } else if (isEqualOrder(aRType, bRType)) {
                // (a is smaller than b) ? b real infinity: a real infinity
                rVal = (isSmallerSize(a.getRealSize(), bInf.getRealSize())) ? bInf.getRealInf() : a.getRealInf();
            } else {
                rVal = a.getRealInf();
            }

            /* Imaginary */
            UncountableType aIType = a.getImaginaryType(), bIType = bInf.getImaginaryType();
            if (isSmallerOrder(aIType, bIType)) {
                iVal = bInf.getImaginaryInf();
            } else if (isEqualOrder(aRType, bRType)) {
                // (a is smaller than b) ? b imaginary infinity: a imaginary infinity
                iVal = (isSmallerSize(a.getImaginarySize(), bInf.getImaginarySize())) ? bInf.getImaginaryInf()
                        : a.getImaginaryInf();
            } else {
                iVal = a.getImaginaryInf();
            }

        } else {
            rVal = (a.isRealType(UncountableType.NONE)) ? new BNumber(b.getA(), 0) : a.getRealInf();
            iVal = (a.isImaginaryType(UncountableType.NONE)) ? new BNumber(0, b.getB()) : a.getImaginaryInf();

        }

        return createCustomUncountable(rVal, iVal);
    }
    // #endregion

    // #region Multiplication
    /**
     * Multiplies a BUncountable to a BNumber or another BUncountable.
     * 
     * @param infA
     * @param two
     * @return
     */
    public static final BNumber multiply(BUncountable infA, double theta) {
        /* Creating Variables */
        BUncountable combinedMod = new GeneralInfinity(false);

        /* Getting the combinedMod */
        // TODO: implement Comparable to find biggest infinity from given values

        return uncountableCis(combinedMod, theta);
    }
    // #endregion

    // #region Divide
    /**
     * Multiplies a BUncountable to a BNumber or another BUncountable.
     * 
     * @param infA
     * @param two
     * @return
     */
    public static final BNumber divide(BUncountable infA, double theta) {
        /* Creating Variables */
        BUncountable combinedMod = new GeneralInfinity(false);

        /* Getting the combinedMod */
        // TODO: implement Comparable to find biggest infinity from given values

        return uncountableCis(combinedMod, theta);
    }
    // #endregion

    // #region Powers

    /**
     * 
     * @param base
     * @param power
     * @return
     */
    public static final BNumber pow(double base, BUncountable power) {
        boolean posConverge = Math.abs(base) < 1; // converges as x -> pos infinity
        // boolean negConverge = Math.abs(base) > 1; // converges as x -> neg infinity

        if (!power.isAZero() && power.isBZero()) {
            if (base == 1) {
                return new BNumber(1, 0);
            } else if (base == -1) {
                return new DoesNotExist();
            }

            // TODO: what is 0^infty?

            if (power.getA() < 0) {
                // Power is Real Negative Infinity, so x -> neg infinity
                if (posConverge) {
                    if (base < 0) {
                        // negative base
                        return new DoesNotExist(); // alternates
                    }
                    return new GeneralInfinity(false, 2);
                }
                return new BNumber(0, 0);
            } else {
                // Power is Real Positive Infinity, so x -> pos infinity
                if (posConverge) {
                    return new BNumber(0, 0);
                }

                if (base < 0) {
                    // neg base
                    return new DoesNotExist(); // alternates
                }
                return new GeneralInfinity(false, 2);
            }

        } else if (power.isAZero() && !power.isBZero()) {
            // "imaginary" infinity
            return new DoesNotExist();

        } else {
            // "complex" infinity

            // negative base = DNE always, b = infinity then DNE
            if (base < 0 || Double.isInfinite(power.getB())) {
                return new DoesNotExist();
            }

            if (power.getA() < 0) {
                // negative power
                return BNumber.zero();
            }

            return uncountableCis(new GeneralInfinity(false), power.getB() * Math.log(base));
        }
    }

    /**
     * 
     * @param base
     * @param power a BNumber. The power cannot be a BUnncountable.
     * @return
     */
    public static final BNumber pow(BUncountable base, BNumber power) {
        if (power.isUncountable()) {
            return new DoesNotExist();
        }

        /**
         * e^(-r2 * theta1 * sin(theta2))
         */
        double expMod = Math.exp(-power.getModulus() * base.getTheta() * Math.sin(power.getTheta()));

        /**
         * r2 * theta1 * cos(theta2)
         */
        double theta = power.getModulus() * base.getTheta() * Math.cos(power.getTheta());
        return uncountableCis(new GeneralInfinity(expMod < 0), theta);
    }
    // #endregion

    ///
    /// Equality and Inequality
    ///
    // #region Order
    /**
     * Checks if <b>Uncountable One</b> has the same type of uncountable than
     * <b>Uncountable Two</b>.
     * 
     * @param one
     * @param two
     * @return {@code one == two}
     */
    public static final boolean isEqualOrder(UncountableType one, UncountableType two) {
        return one.getOrder() == two.getOrder();
    }

    /**
     * Checks if <b>Uncountable One</b> is a smaller type of uncountable than
     * <b>Uncountable Two</b>.
     * 
     * @param one
     * @param two
     * @return {@code one < two}
     */
    public static final boolean isSmallerOrder(UncountableType one, UncountableType two) {
        return one.getOrder() < two.getOrder();
    }

    /**
     * Checks if <b>Uncountable One</b> is a larger type of uncountable than
     * <b>Uncountable Two</b>.
     * 
     * @param one
     * @param two
     * @return {@code one > two}
     */
    public static final boolean isBigger(UncountableType one, UncountableType two) {
        return one.getOrder() > two.getOrder();
    }
    // #endregion

    // #region Size
    /**
     * Checks if <b>Uncountable One</b> is the same size of uncountable as
     * <b>Uncountable Two</b>.
     * 
     * @param one
     * @param two
     * @return {@code one == two}
     */
    public static final boolean isEqualSize(int one, int two) {
        return one == two;
    }

    /**
     * Checks if <b>Uncountable One</b> is a smaller size of uncountable as
     * <b>Uncountable Two</b>.
     * 
     * @param one
     * @param two
     * @return {@code one < two}
     */
    public static final boolean isSmallerSize(int one, int two) {
        return one < two;
    }

    /**
     * Checks if <b>Uncountable One</b> is a bigger size of uncountable as
     * <b>Uncountable Two</b>.
     * 
     * @param one
     * @param two
     * @return {@code one > two}
     */
    public static final boolean isBiggerSize(int one, int two) {
        return one > two;
    }
    // #endregion
}