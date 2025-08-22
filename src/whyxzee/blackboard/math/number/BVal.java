package whyxzee.blackboard.math.number;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.utils.NumberUtils;
import whyxzee.blackboard.utils.UnicodeUtils;

/**
 * A package which denotes a single value.
 * 
 * <p>
 * The functionality of this class has not been checked.
 */
public class BVal implements Comparable<BVal> {
    // #region Constants
    private static final byte DEFAULT_SIG_FIGS = 4;

    private static final byte DNE_ORDER = -1;
    private static final byte DNE_SIZE = -1;

    private static final byte VAL_ORDER = 1;
    private static final byte VAL_SIZE = 0;

    private static final byte GEN_INF_ORDER = 2;
    private static final byte LOG_INF_SIZE = 0;
    private static final byte POLY_INF_SIZE = 1;
    private static final byte EXP_INF_SIZE = 3;
    private static final byte FACT_INF_SIZE = 4;
    // #endregion

    // #region Unicode
    public static final char FRACTION_SLASH = '\u2044';

    public static final char LOWER_PI = '\u03C0';
    public static final char LOWER_PHI = '\u03C6';
    // #endregion

    // #region Variables
    private double value;
    private Character constChar; // what character should represent this value?
    private byte sigFigs;
    private byte size; // how fast an infinity → ∞ compared to similar ones
    private byte order; // how fast an infinity → ∞ compared to other infinities
    // #endregion

    // #region Constructors
    /**
     * The constructor for a BVal from a double value (and thus any number
     * primitive). The value of sigfigs defaults to
     * {@value whyxzee.blackboard.math.number.BVal#DEFAULT_SIG_FIGS}.
     * 
     * @param value
     */
    public BVal(double value) {
        this.sigFigs = DEFAULT_SIG_FIGS;
        this.value = value;
        this.constChar = null;
        if (Double.isNaN(value)) {
            this.order = DNE_ORDER;
            this.size = DNE_SIZE;
        } else {
            this.order = VAL_ORDER;
            this.size = VAL_SIZE;
        }
    }

    public BVal(double value, Character constantChar) {
        this.sigFigs = DEFAULT_SIG_FIGS;
        this.value = value;
        this.constChar = constantChar;
    }

    /**
     * The constructor for a BVal from any object. The value of sigfigs defaults to
     * {@value whyxzee.blackboard.math.number.BVal#DEFAULT_SIG_FIGS}
     * 
     * @param arg If an invalid class is inputted, a DNE will be created. The
     *            following are acceptable classes:
     *            <ul>
     *            <li>Number primitives and wrappers
     *            </ul>
     */
    public BVal(Object arg) {
        this.sigFigs = DEFAULT_SIG_FIGS;
        this.constChar = null;
        if (NumberUtils.isNumPrimitive(arg)) {
            this.value = NumberUtils.doubleFromObj(arg);
            this.order = VAL_ORDER;
            this.size = VAL_SIZE;
        } else {
            this.value = Double.NaN;
            this.order = DNE_ORDER;
            this.size = DNE_SIZE;
        }
    }
    // #endregion

    // #region Pi
    public static final BVal PI() {
        return new BVal(Math.PI, LOWER_PI);
    }
    // #endregion

    // #region Euler's Number
    public static final BVal E() {
        return new BVal(Math.E, 'e');
    }

    public static final BVal exp(BVal power) {
        return E().pow(power);
    }
    // #endregion

    // #region Copying/Cloning
    /**
     * Copies the data of <b>o</b> onto <b>this</b>.
     * 
     * <p>
     * <em>Calling copy() will change the data of <b>this</b>, but will not change
     * the data of <b>o</b>.</em>
     * 
     * @param o the BVal to copy the data from
     */
    public void copy(BVal o) {
        this.value = o.getValue();
    }

    /**
     * @return a deep copy of <b>this</b>.
     */
    @Override
    public BVal clone() {
        return new BVal(value);
    }
    // #endregion

    // #region Display
    /**
     * "Optimizes" the string value for user readability through the following
     * criteria:
     * <ul>
     * <li>If a value's digits exceeds the number of significant figures, then the
     * value is outputted in scientific notation using that number of significant
     * figures.
     * <li>If a value can be represented as a fraction, then it will be outputted as
     * a fraction.
     * <li>If a value does not meet the above criteria, then it is printed as
     * normal.
     * </ul>
     * 
     * @return
     */
    @Override
    public String toString() {
        if (Double.isNaN(value)) {
            return "DNE";
        } else if (constChar != null) {
            return Character.toString(constChar);
        }

        // round thing if its smth like .000001 or .99999
        if (precisionCheck(new BVal((int) value))) {
            return Integer.toString((int) value);
        } else if (precisionCheck(new BVal((int) value + 1))) {
            return Integer.toString((int) value + 1);
        } else if (precisionCheck(new BVal((int) value - 1))) {
            return Integer.toString((int) value - 1);
        }

        if (isInteger()) {
            return Integer.toString((int) value);
        } else if (!isInteger()) {
            // could be represented as a fraction
            int[] mixedFrac = asFraction();
            if (mixedFrac[1] != 0) {
                // successfully represented as fraction
                return Integer.toString(mixedFrac[0]) + UnicodeUtils.intToSuperscript(mixedFrac[1])
                        + FRACTION_SLASH + UnicodeUtils.intToSubscript(mixedFrac[2]);
            }
        }
        if (numOfWholeNums() + numOfDecimals() > 5) {
            return asScientific();
        }

        return truncate();
    }

    /**
     * Returns the value as if it was a coefficient for a term, variable, etc.
     * 
     * @return
     */
    public String asCoef() {
        if (precisionCheck(new BVal(1))) {
            return "";
        } else if (precisionCheck(new BVal(-1))) {
            return "-";
        }
        return toString();
    }

    public String toConsole() {
        if (Double.isNaN(value)) {
            return "DNE";
        } else if (constChar != null) {
            return Character.toString(constChar);
        }

        // round thing if its smth like .000001 or .99999
        if (precisionCheck(new BVal((int) value))) {
            return Integer.toString((int) value);
        } else if (precisionCheck(new BVal((int) value + 1))) {
            return Integer.toString((int) value + 1);
        } else if (precisionCheck(new BVal((int) value - 1))) {
            return Integer.toString((int) value - 1);
        }

        if (isInteger()) {
            return Integer.toString((int) value);
        } else if (!isInteger()) {
            // could be represented as a fraction
            int[] mixedFrac = asFraction();
            if (mixedFrac[1] != 0) {
                // successfully represented as fraction
                return Integer.toString(mixedFrac[0]) + " " + Integer.toString(mixedFrac[1])
                        + "/" + Integer.toString(mixedFrac[2]);
            }
        }

        return Double.toString(value);
        // if (numOfWholeNums() + numOfDecimals() > 5) {
        // return asScientific();
        // }

        // return truncate();
    }

    /**
     * Truncates the value so it can be easily represented in a display or a
     * console.
     * 
     * <p>
     * <em>Calling truncate() does not change the value of <b>this</b>.</em>
     * 
     * @return a truncated String after <b>sigFigs</b> number of decimal points.
     */
    public String truncate() {
        /* Variables */
        char[] charArray = Double.toString(value).toCharArray();
        String output = "";
        int decimalPoints = 0;
        boolean decimals = false;
        int index = 0;

        /* Loop */
        while (true) {
            char addend = charArray[index];
            output += addend;

            if (decimals) {
                decimalPoints++;
            }
            if (addend == '.') {
                decimals = true;
            }

            index++;
            if (decimalPoints == sigFigs) {
                break;
            } else if (index >= charArray.length) {
                break;
            }
        }

        return output;
    }
    // #endregion

    // #region Value
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public byte getOrder() {
        return order;
    }

    public byte getSize() {
        return size;
    }

    public boolean isValue() {
        return order == VAL_ORDER;
    }

    public boolean isInfinity() {
        return order > VAL_ORDER;
    }

    public boolean isDNE() {
        return order == DNE_ORDER || value == Double.NaN;
    }

    /**
     * Checks if <b>this</b> is an integer.
     * 
     * @return {@code true} if the value is an integer
     *         <li>{@code false} if BVal is an infinity, infiinitesimal, DNE, or
     *         rational, or irrational
     */
    public boolean isInteger() {
        return value % 1 == 0;
    }

    /**
     * Checks if <b>this</b> is a rational number, meaning that the value can be
     * represented by a fraction.
     * 
     * @return {@code true} if
     *         {@link whyxzee.blackboard.math.number.BVal#asFraction()} is not an
     *         empty array
     *         <li>{@code false} if otherwise
     */
    public boolean isRational() {
        int[] frac = asFraction();
        for (int i : frac) {
            if (i != 0) {
                return true;
            }
        }
        // all are zeros
        return false;
    }

    /**
     * Checks if <b>this</b> is zero.
     * 
     * @return {@code true} if the value is zero
     *         <li>{@code false} if BVal is an infinity, infinitesimal, or DNE
     */
    public boolean isZero() {
        return withinEpsilon(new BVal(0), new BVal(Math.pow(10, -2 * sigFigs)));
    }

    /**
     * Checks if <b>this</b> is negative.
     * 
     * @return {@code value < 0}
     */
    public boolean isNegative() {
        return value < 0;
    }

    /**
     * Creates a deep copy of <b>this</b>, while multiplying <b>this</b> by -1.
     * 
     * <p>
     * <em>Calling negate() does not change the data of <b>this</b>.</em>
     * 
     * @return
     */
    public BVal negate() {
        return new BVal(-value);
    }

    /**
     * Tries best to represent the value as a fraction.
     * 
     * @return an int[] with a length of 3. If the value cannot be represented as a
     *         fraction, then it will return an array of {0,0,0}.
     *         <ul>
     *         <li>{@code index 0} is the whole number
     *         <li>{@code index 1} is the numerator
     *         <li>{@code index 2} is the denominator
     */
    public int[] asFraction() {
        int integer = (int) Math.abs(value);
        integer *= Math.signum(value);

        BVal des = new BVal(Math.abs(value - (int) value));
        for (int denom = 2; denom < Constants.Number.MAX_PRIME_NUMBER + 1; denom++) {
            for (int num = 1; num < denom; num++) {
                BVal val = new BVal((double) num / denom);
                if (val.precisionCheck(des)) {
                    return new int[] { integer, num, denom };
                }
            }
        }
        return new int[] { 0, 0, 0 };
    }

    /**
     * Represents <b>this</b> with scientific notation.
     * 
     * @return the value of <b>this</b> as scientific notation with <b>sigFigs</b>
     *         number of decimal points.
     */
    public String asScientific() {
        String output = "";
        if (value < 0) {
            // negative value
            output += "-";
            value *= -1; // logs cannot input a negative val
        }
        char[] charArray = Double.toString(value).toCharArray();

        output += charArray[0] + ".";
        for (int i = 1; i <= sigFigs; i++) {
            char iChar = charArray[i];
            if (iChar != '.') {
                output += iChar;
            }
        }

        output += "E" + (int) Math.floor(Math.log10(value));
        return output;
    }

    /**
     * @return the number of significant whole numbers in <b>value</b>
     */
    public int numOfWholeNums() {
        /* Initializing variables */
        char[] charArray = Double.toString(value).toCharArray();
        boolean currentlyTrailingZero = false;

        /* Algorithm */
        int digits = 0;
        int trailingZeros = 0;
        for (char i : charArray) {
            if (currentlyTrailingZero) {
                if (i == '.') {
                    digits += trailingZeros;
                    break;
                } else if (i != '0') {
                    currentlyTrailingZero = false;
                    digits += 1 + trailingZeros;
                    trailingZeros = 0;
                } else {
                    trailingZeros++;
                }
            } else {
                if (i == '0') {
                    currentlyTrailingZero = true;
                    trailingZeros++;
                } else if (i != '.') {
                    digits++;
                } else if (i == '.') {
                    break;
                }
            }
        }
        return digits;
    }

    /**
     * @return the number of significant decimal points in <b>value</b>
     */
    public int numOfDecimals() {
        /* Initializing variables */
        char[] charArray = Double.toString(value).toCharArray();
        boolean currentlyTrailingZero = false;
        boolean decimal = false;

        /* Algorithm */
        int digits = 0;
        int trailingZeros = 0;
        for (char i : charArray) {
            if (!decimal) {
                if (i == '.') {
                    decimal = true;
                }
                continue;
            }

            if (currentlyTrailingZero) {
                if (i == '.') {
                    currentlyTrailingZero = false;
                    decimal = true;
                    digits += trailingZeros;
                } else if (i != '0') {
                    currentlyTrailingZero = false;
                    digits += 1 + trailingZeros;
                    trailingZeros = 0;
                } else {
                    trailingZeros++;
                }
            } else {
                if (i == '0') {
                    currentlyTrailingZero = true;
                    trailingZeros++;
                } else if (i != '.') {
                    digits++;
                }
            }
        }
        return digits;
    }
    // #endregion

    // #region Expression
    public final Character getConstChar() {
        return constChar;
    }

    public final void setConstChar(Character constChar) {
        this.constChar = constChar;
    }
    // #endregion

    // #region Addition
    /**
     * Adds <b>n</b> number of addends to <b>this</b>.
     * 
     * <p>
     * <em>Calling add() does not change the data of <b>this</b> nor the values of
     * <b>addends</b>.</em>
     * 
     * @param addends
     * @return
     */
    public BVal add(BVal... addends) {
        BVal out = this.clone();

        for (BVal i : addends) {
            out.addToVal(i.getValue());
        }

        return out;
    }

    /**
     * Adds <b>addend</b> to the value of <b>this</b>
     * 
     * <p>
     * <em>Calling addToVal() changes the data of <b>this</b>.</em>
     * 
     * @param addend
     */
    private void addToVal(double addend) {
        value += addend;
    }
    // #endregion

    // #region Multiplication
    /**
     * Multiplies <b>this</b> by <b>n</b> number of factors.
     * 
     * <p>
     * <em>Calling multiply() does not change the data of <b>this</b> nor the values
     * of <b>factors</b>.</em>
     * 
     * @param factors
     * @return
     */
    public BVal multiply(BVal... factors) {
        BVal output = this.clone();

        for (BVal i : factors) {
            output.multiplyVal(i.getValue());
        }

        return output;
    }

    /**
     * Multiplies the value of <b>this</b> by the <b>factor</b>.
     * 
     * <p>
     * <em>Calling multiplyVal() changes the data of <b>this</b>.</em>
     * 
     * @param factor
     */
    private void multiplyVal(double factor) {
        value *= factor;
    }
    // #endregion

    // #region Division
    /**
     * Divides <b>this</b> by <b>n</b> number of dividends.
     * 
     * <p>
     * <em>Calling divide() does not change the data of <b>this</b> nor the values
     * of <b>dividends</b></em>
     * 
     * @param dividends
     * @return
     */
    public BVal divide(BVal... dividends) {
        BVal out = this.clone();

        for (BVal i : dividends) {
            out.multiplyVal(1 / i.getValue());
        }
        return out;
    }
    // #endregion

    // #region Power
    /**
     * Puts <b>this</b> to an <b>exponent</b>.
     * 
     * <p>
     * <em>Calling pow() does not change the data of <b>this</b> nor the data of
     * <b>exponent</b></em>
     * 
     * @param exponent
     * @return the power of {@code this^exponent}
     */
    public BVal pow(BVal exponent) {
        return new BVal(Math.pow(value, exponent.getValue()));
    }

    /**
     * Puts <b>this</b> to an <b>exponent</b>.
     * 
     * <p>
     * <em>Calling pow() does not change the data of <b>this</b> nor the data of
     * <b>exponent</b></em>
     * 
     * @param exponent is a double value, which is then turned into a BVal and
     *                 inputted
     *                 into {@link whyxzee.blackboard.math.number.BVal#pow(BVal)}
     * @return the power of {@code this^exponent}
     */
    public BVal pow(double exponent) {
        return pow(new BVal(exponent));
    }
    // #endregion

    // #region Logarithm
    /**
     * <p>
     * <em>Calling log() does not change the value of <b>this</b> nor the value of
     * <b>base</b>.</em>
     * 
     * @param base the base of the log.
     * @return the log of <b>this</b> with base <b>base</b>
     */
    public BVal log(BVal base) {
        return new BVal(Math.log(value) / Math.log(base.getValue()));
    }

    /**
     * <p>
     * <em>Calling ln() does not change the data of <b>this</b>.</em>
     * 
     * @return the natural log of <b>this</b>
     */
    public BVal ln() {
        return new BVal(Math.log(value));
    }
    // #endregion

    // #region Circular Trig
    /**
     * Returns the sine value of <b>this</b>.
     * 
     * <p>
     * <em>Calling sin() does not change the value of <b>this</b>.</em>
     * 
     * @return {@code sin(value)}
     */
    public BVal sin() {
        return new BVal(Math.sin(value));
    }

    /**
     * Returns the cosine value of <b>this</b>.
     * 
     * <p>
     * <em>Calling cos() does not change the value of <b>this</b>.</em>
     * 
     * @return {@code cos(value)}
     */
    public BVal cos() {
        return new BVal(Math.cos(value));
    }

    /**
     * Returns the arc-tangent value of <b>this</b>.
     * 
     * <p>
     * <em>Calling atan() does not change the value of <b>this</b>.</em>
     * 
     * @return
     */
    public BVal atan() {
        return new BVal(Math.atan(value));
    }

    // #endregion

    // #region Range
    /**
     * Checks if <b>this</b> BVal is within ±ε (plus or minus epsilon) from the
     * <b>desired</b> BVal.
     * 
     * @param desired is the center of the range.
     * @param epsilon is half of the range. It is added and subtracted from the
     *                <b>desired</b>.
     * @return {@code true} if <b>this</b> BVal is within the epsilon
     *         <li>{@code false} if otherwise.
     */
    public boolean withinEpsilon(BVal desired, BVal epsilon) {
        // uncountable crap

        double des = desired.getValue(), eps = epsilon.getValue();
        return (des + eps > value) && (des - eps < value);
    }

    /**
     * A preset method for checking the precision of <b>this</b> to the desired
     * value.
     * 
     * @param desired is the value that is compared to <b>this</b>.
     * @return {@code true} if <b>this</b> is within 10^(-2*<b>sigFigs</b>) of the
     *         <b>desired</b> value.
     */
    public boolean precisionCheck(BVal desired) {
        return withinEpsilon(desired, new BVal(Math.pow(10, -2 * sigFigs)));
    }

    /**
     * Clamps <b>this</b> so <b>this</b> is in between <b>lowerBound</b> and
     * <b>upperBound</b>.
     * 
     * <p>
     * In the event that <b>lowerBound</b> is greater than
     * <b>upperBound</b>, they will be flipped around in the method.
     * 
     * <p>
     * <em>Calling clamp() changes the data of <b>this</b>.</em>
     * 
     * @param lowerBound is the lowest value that <b>this</b> can be
     * @param upperBound is the highest value that <b>this</b> can be
     */
    public void clamp(BVal lowerBound, BVal upperBound) {
        if (compareTo(lowerBound) == -1) {
            copy(lowerBound);
        } else if (compareTo(upperBound) == 1) {
            copy(upperBound);
        }

        /* Incase the two are swapped */
        if (lowerBound.compareTo(upperBound) != 1) {
            return;
        }

        // lowerBound > upperBound
        if (compareTo(lowerBound) == 1) {
            copy(lowerBound);
        } else if (compareTo(upperBound) == -1) {
            copy(upperBound);
        }
    }
    // #endregion

    // #region Comparison
    /**
     * Checks if <b>this</b> is less than, equal to, or greater than <b>o</b>.
     * 
     * @return {@code Negative value} if <b>this</b> is less than <b>o</b>
     *         <li>{@code Zero} if <b>this</b> equals <b>o</b>
     *         <li>{@code Positive value} if <b>this</b> is greater than <b>o</b
     */
    @Override
    public int compareTo(BVal o) {
        if (isValue() && o.isValue()) {
            /* Numbers */
            if (value < o.getValue()) {
                return -1;
            } else if (precisionCheck(o)) {
                return 0;
            } else {
                return 1;
            }
        }

        /* Order */
        if (order < o.getOrder()) {
            return -1;
        } else if (order > o.getOrder()) {
            return 1;
        }

        /* Size */
        if (size < o.getSize()) {
            return -1;
        } else if (size > o.getSize()) {
            return 1;
        }
        return 0;

    }

    /**
     * Checks if <b>this</b> has an equal value to <b>arg0</b>.
     * 
     * @param arg0 is an object with any type. If an object of any class other than
     *             a BVal is inputted into the function, then equals() is called
     *             with the argument of a BVal using the object constructor
     *             ({@link whyxzee.blackboard.math.number.BNum#BNum(Object)})
     */
    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof BVal) {
            BVal o = (BVal) arg0;
            if (order != o.getOrder()) {
                return false;
            }
            if (size != o.getSize()) {
                return false;
            }

            return isValue() ? precisionCheck(o) : true;
        }
        return equals(new BVal(arg0));
    }
    // #endregion
}
