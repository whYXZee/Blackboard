package whyxzee.blackboard.math.pure.numbers;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.utils.UnicodeUtils;

public class Value implements Comparable<Value> {
    /* Variables */
    private double value;
    /**
     * How big a number is compared to others of similar type (aleph naught vs aleph
     * one)
     */
    private byte size;
    /**
     * How big a number is compared to others (ie aleph vs general infinity)
     */
    private byte order;
    private String string;

    // #region Constructors
    /**
     * A constructor for a DNE.
     */
    public Value() {
        value = Double.NaN;
        size = -1;
        order = Constants.Number.DNE_ORDER;
        string = "DNE";
    }

    /**
     * A constructor for a normal value.
     * 
     * @param value
     */
    public Value(double value) {
        this.value = value;
        size = 0;
        order = 1;
        string = "";
    }

    /**
     * A constructor for an uncountable.
     */
    public Value(double value, int size, int order, String string) {
        this.value = value;
        this.size = NumberUtils.clampByte(size);
        this.order = NumberUtils.clampByte(order);
        this.string = string;
    }
    // #endregion

    // #region Infinitesimal
    /**
     * A pre-made static constructor for an infinitesimal value.
     * 
     * @param string
     * @return
     */
    public static final Value infinitesimal(String string) {
        return new Value(Constants.Number.INFINITESIMAL_VAL, 0,
                Constants.Number.INFINITESIMAL_ORDER, string);
    }

    public final boolean isInfinitesimal() {
        return order == Constants.Number.INFINITESIMAL_ORDER;
    }
    // #endregion

    // #region Aleph
    /**
     * A pre-made static constructor for an Aleph.
     * 
     * @param isNegative
     * @param size
     * @return
     */
    public static final Value aleph(boolean isNegative, int size) {
        return new Value(isNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY, NumberUtils.clampByte(size),
                Constants.Number.ALEPH_ORDER, Constants.Unicode.ALEPH);
    }
    // #endregion

    // #region Blanket Infinity
    /**
     * A pre-made static constructor for a blanket Infinity.
     * 
     * @param isNegative
     * @param size
     * @return
     */
    public static final Value infinity(boolean isNegative, int size) {
        return new Value(isNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY, NumberUtils.clampByte(size),
                Constants.Number.GENERAL_INFINITY_ORDER, Constants.Unicode.INFINITY);
    }
    // #endregion

    // #region String/Display
    @Override
    public final String toString() {
        if (isInfinity()) {
            return ((isNegative()) ? "-" : "") + string + UnicodeUtils.intToSubscript(size);
        } else if (isInfinitesimal() || isDNE()) {
            return string;
        }
        return NumberUtils.valueToString(value);
    }
    // #endregion

    // #region Copying/Cloning
    public final void copy(Value other) {
        this.value = other.getValue();
        this.size = other.getSize();
        this.order = other.getOrder();
        this.string = other.getString();
    }

    @Override
    public final Value clone() {
        return new Value(value, (int) size, (int) order, string); // TODO: clone string

    }
    // #endregion

    // #region Get/Set
    public final double getValue() {
        return value;
    }

    public final byte getSize() {
        return size;
    }

    public final byte getOrder() {
        return order;
    }

    public final String getString() {
        return string;
    }
    // #endregion

    // #region Arithmetic
    public final void add(Value addend) {
        if (hasValue() && addend.hasValue()) {
            value += addend.getValue();
            return;
        }

        /* Uncountables */
        if (compareTo(addend) < 0) {
            copy(addend);
        }
        // by now, this > addend or this == addend so no need to change
    }

    public final void multiply(Value factor) {
        if (hasValue() && factor.hasValue()) {
            value *= factor.getValue();
            return;
        }

        /* Uncountables */
    }

    public final void multiply(double scalar) {
        value *= scalar;
    }

    public final void divide(Value dividend) {
        if (hasValue() && dividend.hasValue()) {
            value /= dividend.getValue();
            return;
        }

        if (compareTo(dividend) < 0) {
            // denominator reaches infinity faster
            copy(new Value(0));
        } else if (compareTo(dividend) == 0) {
            // same infinity, so L'Hospital's rule
            copy(new Value(1));
            multiply(signum() * dividend.signum());
        }
        // numerator reaches infinity faster, nothing needs to be done
    }

    public final void power(double power) {
        value = Math.pow(value, power);
    }
    // #endregion

    // #region Operations
    /**
     * Performs a deep copy while also multiplying the value by -1.
     * 
     * @return
     */
    public final Value negate() {
        if (isInfinitesimal()) {
            return infinitesimal(string); // TODO: help
        } else if (isInfinity()) {
            return new Value(-value, size, order, string);
        }
        return new Value(-value);
    }

    /**
     * 
     * @return the absolute value of <b>this</b>
     */
    public final double abs() {
        return Math.abs(value);
    }

    public final double mod(double modulo) {
        return value % modulo;
    }

    public final double signum() {
        return Math.signum(value);
    }
    // #endregion

    // #region Number Type Bools
    /**
     * 
     * @return
     */
    public final boolean isZero() {
        return NumberUtils.precisionCheck(value, 0);
    }

    public final boolean isInteger() {
        return NumberTheory.isInteger(value);
    }

    public final boolean isRational(int sigFigs) {
        return NumberTheory.isRational(sigFigs);
    }

    public final boolean isRational() {
        return NumberTheory.isRational(Constants.Number.SIG_FIGS);
    }

    public final boolean hasValue() {
        return !isDNE() && !isInfinitesimal() && !isInfinity();
    }

    /**
     * 
     * @return <em>true</em> if the order is greater than
     *         {@link whyxzee.blackboard.Constants.Number#VALUE_ORDER} and it is not
     *         DNE.
     *         <li><em>false</em> if otherwise
     */
    public final boolean isInfinity() {
        // not a value && not DNE
        return order > Constants.Number.VALUE_ORDER && !Double.isNaN(value);
    }

    public final boolean isDNE() {
        return Double.isNaN(value);
    }

    public final boolean isNegative() {
        return value < 0;
    }
    // #endregion

    // #region Comparison Bools
    @Override
    public final boolean equals(Object arg) {
        if (arg == null) {
            return false;
        }

        if (arg instanceof Value) {
            Value other = (Value) arg;

            // ensure that they are the same type
            if (size != other.getSize() || order != other.getOrder()) {
                return false;
            } else if (isInfinitesimal() && other.isInfinitesimal()) {
                return string.equals(other.toString());
            }
            return NumberUtils.precisionCheck(value, other.getValue());

        } else if (arg instanceof Byte) { // doubt that this is needed
            return NumberUtils.precisionCheck(value, (byte) arg);

        } else if (arg instanceof Short) { // doubt that this is needed
            return NumberUtils.precisionCheck(value, (short) arg);

        } else if (arg instanceof Integer) {
            return NumberUtils.precisionCheck(value, (int) arg);

        } else if (arg instanceof Long) { // doubt that this is needed
            return NumberUtils.precisionCheck(value, (long) arg);

        } else if (arg instanceof Float) { // doubt that this is needed
            return NumberUtils.precisionCheck(value, (float) arg);

        } else if (arg instanceof Double) {
            return NumberUtils.precisionCheck(value, (double) arg);
        }
        return false;
    }

    /**
     * 
     * @param arg0
     * @return
     *         <b>Negative value</b> if this is less than arg0
     *         <li><b>Zero</b> if this equals arg0
     *         <li><b>Positive value</b> if this is greater than arg0
     */
    @Override
    public int compareTo(Value arg0) {
        /* Check order */
        if (order < arg0.getOrder()) {
            return -1;
        } else if (order > arg0.getOrder()) {
            return 1;
        }

        /* Check size */
        if (size < arg0.getSize()) {
            return -1;
        } else if (order > arg0.getOrder()) {
            return 1;
        }

        /* Check values */
        if (value < arg0.getValue()) {
            return -1;
        } else if (value > arg0.getValue()) {
            return 1;
        }
        return 0;
    }
    // #endregion
}
