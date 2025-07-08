package whyxzee.blackboard.math.pure.numbers;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.utils.pure.TrigUtils;
import whyxzee.blackboard.utils.NumberUtils;
import whyxzee.blackboard.utils.UnicodeUtils;

public class BNum implements Comparable<BNum> {
    // #region Constants
    public static final double INFINITESIMAL_VAL = 0.001;
    public static final short DNE_ORDER = -1;
    public static final short INFINITESIMAL_ORDER = 0;
    public static final short VALUE_ORDER = 1;
    public static final short ALEPH_ORDER = 2;
    public static final short GENERAL_INFINITY_ORDER = 3;
    // #endregion

    /* Variables */
    private double value;
    /**
     * How big a number is compared to others of similar type (aleph naught vs aleph
     * one). The smallest size is 0.
     */
    private byte size;
    /**
     * How big a number is compared to others (ie aleph vs general infinity)
     */
    private byte order;
    private String string;

    // #region Constructors
    public static final BNum DNE = new BNum(Double.NaN, -1, BNum.DNE_ORDER, "DNE");

    /**
     * A constructor for a normal value.
     * 
     * @param value
     */
    public BNum(double value) {
        if (Double.isNaN(value)) {
            copyDNE();
        } else {
            this.value = value;
            size = 0;
            order = 1;
            string = "";
        }
    }

    /**
     * A constructor for an uncountable.
     */
    public BNum(double value, int size, int order, String string) {
        this.value = value;
        this.size = NumberUtils.clampByte(size);
        this.order = NumberUtils.clampByte(order);
        this.string = string;
    }
    // #endregion

    // #region Value Constants
    public static final BNum goldenRatio(boolean isNegative) {
        return new BNum(isNegative ? -(1.0 + Math.sqrt(5)) / 2.0 : (1.0 + Math.sqrt(5)) / 2.0, 0, VALUE_ORDER,
                UnicodeUtils.LOWER_PHI);
    }
    // #endregion

    // #region Infinitesimal
    /**
     * A pre-made static constructor for an infinitesimal value.
     * 
     * @param string
     * @return
     */
    public static final BNum infinitesimal(String string) {
        return new BNum(BNum.INFINITESIMAL_VAL, 0,
                BNum.INFINITESIMAL_ORDER, string);
    }

    public final boolean isInfinitesimal() {
        return order == BNum.INFINITESIMAL_ORDER;
    }
    // #endregion

    // #region Infinities
    /**
     * A pre-made static constructor for an Aleph.
     * 
     * @param isNegative
     * @param size
     * @return
     */
    public static final BNum aleph(boolean isNegative, int size) {
        return new BNum(isNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY, NumberUtils.clampByte(size),
                BNum.ALEPH_ORDER, Constants.Unicode.ALEPH);
    }

    /**
     * A pre-made static constructor for a blanket Infinity.
     * 
     * @param isNegative
     * @param size
     * @return
     */
    public static final BNum infinity(boolean isNegative, int size) {
        return new BNum(isNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY, NumberUtils.clampByte(size),
                BNum.GENERAL_INFINITY_ORDER, Constants.Unicode.INFINITY);
    }
    // #endregion

    // #region String/Display
    public final String display() {
        if (isInfinity()) {
            return ((isNegative()) ? "-" : "") + string + UnicodeUtils.intToSubscript(size);
        } else if (isInfinitesimal() || isDNE()) {
            return string;
        }
        return NumberUtils.valueToString(value);
    }

    @Override
    public final String toString() {
        if (isInfinity()) {
            return ((isNegative()) ? "-" : "") + string + UnicodeUtils.intToSubscript(size);
        } else if (isInfinitesimal() || isDNE() || !string.equals("")) {
            return string;
        }
        return NumberUtils.valueToString(value);
        // return new BigDecimal(value).toPlainString();
    }
    // #endregion

    // #region Conversions
    public static final BNum fromObj(Object arg) {
        if (arg instanceof BNum) {
            return (BNum) arg;
        } else if (NumberUtils.isNumPrimitive(arg)) {
            return new BNum(NumberUtils.doubleFromObj(arg));
        }
        return BNum.DNE;
    }

    public static final BNum[] fromObjArr(Object[] args) {
        BNum[] output = new BNum[args.length];
        for (int i = 0; i < args.length; i++) {
            output[i] = BNum.fromObj(args[i]);
        }
        return output;
    }
    // #endregion

    // #region Copying/Cloning
    public final void copyDNE() {
        this.value = Double.NaN;
        this.size = -1;
        this.order = BNum.DNE_ORDER;
        this.string = "DNE";
    }

    public final void copy(BNum other) {
        this.value = other.getValue();
        this.size = other.getSize();
        this.order = other.getOrder();
        this.string = other.getString();
    }

    @Override
    public final BNum clone() {
        return new BNum(value, (int) size, (int) order, string); // TODO: clone string

    }
    // #endregion

    // #region Get/Set
    public final double getValue() {
        return value;
    }

    public final void setValue(double value) {
        this.value = value;
    }

    public final byte getSize() {
        return size;
    }

    public final void setSize(byte size) {
        this.size = size;
    }

    public final byte getOrder() {
        return order;
    }

    public final void setOrder(byte order) {
        this.order = order;
    }

    public final String getString() {
        return string;
    }

    public final void setString(String string) {
        this.string = string;
    }
    // #endregion

    // #region Add
    /**
     * Adds n addends onto <b>this</b>. This method also doubles for subtraction.
     * The following are implemented:
     * <ul>
     * <li>DNE
     * <li>Uncountable addition: the highest order -> size uncountable is kept.
     * </ul>
     * 
     * <em>Calling add() will change the data of <b>this</b>, but not the data of
     * <b>other</b></em>.
     * 
     * @param args
     * @return A Value of all of the added terms.
     */
    public final BNum add(Object... args) {
        BNum[] addends = BNum.fromObjArr(args);

        for (BNum i : addends) {
            /* DNE */
            if (this.isDNE() || i.isDNE()) {
                this.copyDNE();
                return this;
            }

            /* Value */
            if (hasValue() && i.hasValue()) {
                value += i.getValue();
                continue;
            }

            /* Infinity / Infinitesimal */
            if (compareTo(i) < 0) {
                // prioritize the greatest value
                copy(i);
            }
            // by now, this > addend or this == addend so no need to change
        }

        // make a "constant" like the golden ratio stop being represented by phi
        if (this.hasValue() && !string.equals("")) {
            string = "";
        }
        return this;
    }

    /**
     * Performs
     * {@link whyxzee.blackboard.math.pure.numbers.BNum#add(Object...)} without
     * the use of an object.
     * 
     * <p>
     * <em>Calling statAdd() does not change the data of any of the
     * factors</em>.
     * 
     * @param addendA
     * @param addends
     * @return
     */
    public static final BNum statAdd(BNum addendA, Object... addends) {
        return addendA.clone().add(addends);
    }
    // #endregion

    // #region Multiply
    /**
     * Multiplies <b>this</b> by n factors. The following are implemented:
     * <ul>
     * <li>DNE
     * <li>TODO: infinity / infinitesimal
     * </ul>
     * 
     * <em>Calling multiply() will change the data of <b>this</b>, but not the data
     * of <b>factor</b></em>.
     * 
     * @param factors Values or number primitives
     * @return
     */
    public final BNum multiply(Object... args) {
        BNum[] factors = BNum.fromObjArr(args);

        for (BNum i : factors) {
            if (this.isDNE() || i.isDNE()) {
                this.copyDNE();
                return this;
            }

            if (hasValue() && i.hasValue()) {
                value *= i.getValue();
                continue;
            }

            /* Infinitesimal */
            if (this.isInfinitesimal() || i.isInfinitesimal()) {
                // TODO: later wahh
            }

            /* Infinity */
            if (compareTo(i) < 0) {
                // prioritize the greatest value
                this.copy(i);
            }

        }

        // make a "constant" like the golden ratio stop being represented by phi
        if (this.hasValue() && !string.equals("")) {
            string = "";
        }
        return this;
    }

    /**
     * Performs
     * {@link whyxzee.blackboard.math.pure.numbers.BNum#multiply(BNum...)} without
     * the use of an object.
     * 
     * <p>
     * <em>Calling statMultiply() does not change the data of any of the
     * factors</em>.
     * 
     * @param factorA
     * @param factors
     * @return
     */
    public static final BNum statMultiply(Object factorA, Object... factors) {
        return BNum.fromObj(factorA).clone().multiply(factors);
    }

    /**
     * Multiplies <b>this</b> by a scalar number.
     * 
     * <p>
     * <em>Calling multiply() will change the data of <b>this</b></em>.
     * 
     * @param scalar
     */
    public final void multiply(double scalar) {
        value *= scalar;
    }
    // #endregion

    // #region Divide
    /**
     * Divides <b>this</b> by n divisors. The following are implemented:
     * <ul>
     * <li>DNE
     * <li>Infinite division: treated as limits
     * <li>Division by zero/Infinitesimal: treated as limits
     * </ul>
     * 
     * <em>Calling divide() will change the data of <b>this</b>, but not the data of
     * <b>dividend</b></em>.
     * 
     * @param args
     * @return
     */
    public final BNum divide(Object... args) {
        BNum[] divisors = BNum.fromObjArr(args);

        for (BNum i : divisors) {
            /* DNE */
            if (this.isDNE() || i.isDNE()) {
                this.copyDNE();
                return this;
            }

            /* Values & Infinitesimal */
            if (this.hasValue() && i.hasValue()) {
                if (i.isZero()) {
                    this.copy(BNum.infinity(isNegative(), 1));
                } else {
                    value /= i.getValue();
                }
                continue;
            } else if (this.hasValue() && i.isInfinitesimal()) {
                this.copy(BNum.infinity(isNegative(), 1));
                continue;
            } else if (this.isInfinitesimal() && i.hasValue()) {
                this.copy(new BNum(0));
                continue;
            }

            /* Infinity */
            if (this.compareTo(i) < 0) {
                // denominator reaches infinity faster
                copy(new BNum(0));
            } else if (this.compareTo(i) == 0) {
                // same infinity, so L'Hospital's rule
                copy(new BNum(1));
                multiply(this.signum() * i.signum());
            }
            // numerator reaches infinity faster, nothing needs to be done
        }

        // make a "constant" like the golden ratio stop being represented by phi
        if (this.hasValue() && !string.equals("")) {
            string = "";
        }
        return this;
    }

    /**
     * Performs
     * {@link whyxzee.blackboard.math.pure.numbers.BNum#divide(BNum...)} without
     * the use of an object.
     * 
     * <p>
     * <em>Calling statDivide() does not change the data of any of the
     * factors</em>.
     * 
     * @param dividend
     * @param divisors
     * @return
     */
    public static final BNum statDivide(Object dividend, Object... divisors) {
        return BNum.fromObj(dividend).clone().divide(divisors);
    }
    // #endregion

    // #region Powers
    /**
     * <em>Calling exp() will not change the data of <b>power</b></em>.
     * 
     * @param power
     * @return {@code e^power}
     */
    public static final BNum exp(BNum power) {
        /* DNE */
        if (power.isDNE()) {
            return BNum.DNE;
        }

        /* Real Values */
        if (power.hasValue()) {
            return new BNum(Math.exp(power.getValue()));
        }

        /* Infinity */
        if (power.isInfinity() && power.isNegative()) {
            return new BNum(0);
        } else if (power.isInfinity()) {
            // if positive as well
            return BNum.infinity(false, 2);
        }
        return BNum.DNE;
    }

    /**
     * Applies a real, uncountable, or DNE power to to <b>this</b>. The following
     * are implemented:
     * <ul>
     * <li>DNE
     * <li>Infinite powers & bases (treated as limits)
     * <li>Infinitesimal powers (treated as limits)
     * </ul>
     * 
     * <em>Calling power() will change the value of <b>this</b>, but not the data of
     * <b>power</b></em>.
     * 
     * @param power a Value or a number primitive
     */
    public final BNum power(Object arg) {
        BNum power = BNum.fromObj(arg);
        System.out.println("this: " + this.isInfinitesimal());

        /* DNE */
        if (power.isDNE() || this.isDNE()) {
            this.copyDNE();
            return this;
        }

        /* Real Values */
        if (this.hasValue() && power.hasValue()) {
            value = Math.pow(value, power.getValue());
            return this;
        }

        /* Infinitesimal Power */
        if (power.isInfinitesimal() && this.hasValue()) {
            value = 1;
            return this;
        } else if (power.isInfinitesimal() && this.isInfinity()) {
            // treated as limit as power approaches zero and base approahces infinity
            this.copyDNE();
            return this;
        }

        /* Infinite Base */
        if (this.isInfinity() && power.isNegative()) {
            this.copy(new BNum(0));
            return this;
        } else if (this.isInfinity() && !power.isNegative()) {
            return this;
        }

        /* Infinite Power & Real Base */
        if (value == 1) {
            // 1^n = 1
            return this;
        } else if (value == -1) {
            this.copyDNE(); // alternates
            return this;
        }

        if (isNegative()) {
            if (Math.abs(value) > 1) {
                this.copy(new BNum(0));
                return this;
            }

            if (value < 0) {
                this.copyDNE();
            } else {
                this.copy(BNum.infinity(false, 2));
            }
            return this;
        } else {
            if (Math.abs(value) < 1) {
                this.copy(new BNum(0));
            } else if (value < 0) {
                this.copyDNE();
            } else {
                this.copy(BNum.infinity(false, 2));
            }
            return this;
        }
    }

    /**
     * Performs
     * {@link whyxzee.blackboard.math.pure.numbers.BNum#power(BNum)} without
     * the use of an object.
     * 
     * <p>
     * <em>Calling statPower() does not change the data of any of the
     * factors</em>.
     * 
     * @param factorA
     * @param factors
     * @return
     */
    public static final BNum statPower(BNum base, BNum power) {
        return base.clone().power(power);
    }
    // #endregion

    // #region Logs
    /**
     * Returns a new BNum that is the log (natural log) of <b>this</b>. The
     * following are implemented:
     * <ul>
     * <li>DNE
     * <li>Infinity (treated as a lim x-> +- infinity)
     * <li>Infinitesimal (treated as lim x-> 0)
     * </ul>
     * 
     * <em>Calling log() does not change the data of <b>this</b></em>.
     * 
     * @return
     */
    public final BNum log() {
        if (hasValue() && !isNegative() && !isZero()) {
            return new BNum(Math.log(value));
        } else if (isInfinity() && !isNegative()) {
            return BNum.infinity(false, 0);
        }
        // DNE, infinitesimal, or negative infinity
        return BNum.DNE;
    }
    // #endregion

    // #region Operations
    /**
     * Performs a deep copy while also multiplying the value by -1.
     * 
     * @return
     */
    public final BNum negate() {
        if (isInfinitesimal()) {
            return infinitesimal(string); // TODO: help
        } else if (isInfinity()) {
            return new BNum(-value, size, order, string);
        } else if (isDNE()) {
            return BNum.DNE;
        }
        return new BNum(-value);
    }

    /**
     * <p>
     * <em>Calling abs() does not change the data of <b>this</b></em>.
     * 
     * @return the absolute value of <b>this</b>
     */
    public final double abs() {
        return Math.abs(value);
    }

    /**
     * <p>
     * <em>Calling mod() does not change the data of <b>this</b></em>.
     * 
     * @param modulo
     * @return
     */
    public final double mod(double modulo) {
        return value % modulo;
    }

    /**
     * <p>
     * <em>Calling signum() does not change the data of <b>this</b></em>.
     * 
     * @return
     */
    public final double signum() {
        return Math.signum(value);
    }
    // #endregion

    // #region Trig Ops
    /**
     * Returns a new BNum that is the sine of <b>this</b> as radians. The following
     * are implemented:
     * <ul>
     * <li>DNE
     * <li>Infinity (treated as a lim x-> +- infinity)
     * <li>Infinitesimal (treated as lim x-> 0)
     * </ul>
     * 
     * <em>Calling sin() does not change the data of <b>this</b>.</em>
     * 
     * @return {@code Math.sin(value)}
     */
    public final BNum sin() {
        if (hasValue()) {
            return new BNum(Math.sin(value));
        } else if (isInfinitesimal()) {
            return new BNum(0);
        }
        // infinity or DNE
        // sin(x) -> infinity, it diverges
        return BNum.DNE;
    }

    /**
     * Returns a new BNum that is the cosine of <b>this</b> as radians. The
     * following are implemented:
     * <ul>
     * <li>DNE
     * <li>Infinity (treated as a lim x-> +- infinity)
     * <li>Infinitesimal (treated as lim x-> 0)
     * </ul>
     * <p>
     * <em>Calling cos() does not change the data of <b>this</b>.</em>
     * 
     * @return {@code Math.cos(value)}
     */
    public final BNum cos() {
        if (hasValue()) {
            return new BNum(Math.cos(value));
        } else if (isInfinitesimal()) {
            return new BNum(1);
        }
        // cos(x) -> infinity, it diverges
        // infinity or DNE
        return BNum.DNE;
    }

    /**
     * Returns a new BNum that is the tangent of <b>this</b> as radians. The
     * following are implemented:
     * <ul>
     * <li>DNE
     * <li>Infinity (treated as a lim x-> +- infinity)
     * <li>Infinitesimal (treated as lim x-> 0)
     * <li>Asymptotes
     * </ul>
     * <p>
     * <em>Calling tan() does not change the data of <b>this</b>.</em>
     * 
     * @return {@code Math.tan(value)}
     */
    public final BNum tan() {
        if (hasValue() && !TrigUtils.tanDiscont(value)) {
            return new BNum(Math.tan(value));
        } else if (isInfinitesimal()) {
            return new BNum(0);
        }
        // tan(x) -> infinity, it diverges
        // DNE or infinity
        return BNum.DNE;
    }

    /**
     * Returns a new BNum that is the cosecant of <b>this</b> as radians. The
     * following are implemented:
     * <ul>
     * <li>DNE
     * <li>Infinity (treated as a lim x-> +- infinity)
     * <li>Infinitesimal (treated as lim x-> 0)
     * <li>Asymptotes
     * </ul>
     * <p>
     * <em>Calling csc() does not change the data of <b>this</b>.</em>
     * 
     * @return {@code 1.0 / Math.sin(value)}
     */
    public final BNum csc() {
        if (hasValue()) {
            double sinVal = Math.sin(value);
            if (!NumberUtils.precisionCheck(sinVal, 0)) {
                return new BNum(1 / sinVal);
            }
        }
        // if infinity, infinitesimal, DNE, or sin is close to 0
        return BNum.DNE;
    }

    /**
     * Returns a new BNum that is the secant of <b>this</b> as radians. The
     * following are implemented:
     * <ul>
     * <li>DNE
     * <li>Infinity (treated as a lim x-> +- infinity)
     * <li>Infinitesimal (treated as lim x-> 0)
     * <li>Asymptotes
     * </ul>
     * <p>
     * <em>Calling sec() does not change the data of <b>this</b>.</em>
     * 
     * @return {@code 1.0 / Math.cos(value)}
     */
    public final BNum sec() {
        if (hasValue()) {
            double cosVal = Math.cos(value);
            if (!NumberUtils.precisionCheck(cosVal, 0)) {
                return new BNum(1 / cosVal);
            }
        } else if (isInfinitesimal()) {
            return new BNum(1);
        }
        // if infinity, DNE, or cos is close to 0
        return BNum.DNE;
    }

    /**
     * Returns a new BNum that is the cotangent of <b>this</b> as radians. The
     * following are implemented:
     * <ul>
     * <li>DNE
     * <li>Infinity (treated as a lim x-> +- infinity)
     * <li>Infinitesimal (treated as lim x-> 0)
     * <li>Asymptotes
     * </ul>
     * <p>
     * <em>Calling cot() does not change the data of <b>this</b>.</em>
     * 
     * @return {@code Math.tan(value)}
     */
    public final BNum cot() {
        if (hasValue()) {
            BNum tanVal = tan();
            if (!tanVal.isZero() && tanVal.isDNE()) {
                return new BNum(1).divide(tanVal);
            }
        }
        // infinity, infinitesimal, or DNE
        return BNum.DNE;
    }
    // #endregion

    // #region Inv Trig Ops
    /**
     * Returns a new BNum that is the arc-sine of <b>this</b> in radians. The
     * following are implemented:
     * <ul>
     * <li>DNE
     * <li>Infinity (treated as a lim x-> +- infinity)
     * <li>Infinitesimal (treated as lim x-> 0)
     * <li>Domain of [-1, 1]
     * </ul>
     * <p>
     * <em>Calling asin() does not change the data of <b>this</b>.</em>
     * 
     * @return {@code Math.asin(value)}
     */
    public final BNum asin() {
        if (hasValue() && Math.abs(value) <= 1) {
            return new BNum(Math.asin(value));
        } else if (isInfinitesimal()) {
            return new BNum(0);
        }
        // DNE
        return BNum.DNE;
    }

    /**
     * Returns a new BNum that is the arc-cosine of <b>this</b> in radians. The
     * following are implemented:
     * <ul>
     * <li>DNE
     * <li>Infinity (treated as a lim x-> +- infinity)
     * <li>Infinitesimal (treated as lim x-> 0)
     * <li>Domain of [-1, 1]
     * </ul>
     * <p>
     * <em>Calling acos() does not change the data of <b>this</b>.</em>
     * 
     * @return {@code Math.acos(value)}
     */
    public final BNum acos() {
        if (hasValue() && Math.abs(value) <= 1) {
            return new BNum(Math.acos(value));
        } else if (isInfinitesimal()) {
            return new BNum(Math.PI / 2);
        }
        // DNE
        return BNum.DNE;
    }

    /**
     * Returns a new BNum that is the arc-tangent of <b>this</b> in radians. The
     * following are implemented:
     * <ul>
     * <li>DNE
     * <li>Infinity (treated as a lim x-> +- infinity)
     * <li>Infinitesimal (treated as lim x-> 0)
     * </ul>
     * <p>
     * <em>Calling atan() does not change the data of <b>this</b>.</em>
     * 
     * @return {@code Math.atan(value)}
     */
    public final BNum atan() {
        if (hasValue()) {
            return new BNum(Math.atan(value));
        } else if (isInfinity()) {
            return new BNum(signum() * Math.PI / 2);
        } else if (isInfinitesimal()) {
            return new BNum(0);
        }
        // DNE
        return BNum.DNE;
    }

    /**
     * Returns a new BNum that is the arc-cosecant of <b>this</b> in radians. The
     * following are implmented:
     * <ul>
     * <li>DNE
     * <li>Infinity (treated as a lim x-> +- infinity)
     * <li>Infinitesimal (treated as lim x-> 0)
     * <li>Discontinuity in domain ([-infty, -1] U [1, infty])
     * </ul>
     * <p>
     * <em>Calling acsc() does not change the data of <b>this</b>.</em>
     * 
     * @return {@code Math.asin(1.0 / value)}
     */
    public final BNum acsc() {
        if (hasValue() && Math.abs(value) >= 1) {
            return new BNum(Math.asin(1 / value));
        } else if (isInfinity()) {
            return new BNum(0);
        }
        // DNE or infinitesimal
        return BNum.DNE;
    }

    /**
     * Returns a new BNum that is the arc-secant of <b>this</b> as radians. The
     * following are implemented:
     * <ul>
     * <li>DNE
     * <li>Infinity (treated as a lim x-> +- infinity)
     * <li>Infinitesimal (treated as lim x-> 0)
     * <li>Discontinuity in domain ([-infty, -1] U [1, infty])
     * </ul>
     * <p>
     * <em>Calling asec() does not change the data of <b>this</b>.</em>
     * 
     * @return {@code Math.tan(value)}
     */
    public final BNum asec() {
        if (hasValue() && Math.abs(value) >= 1) {
            return new BNum(Math.acos(1 / value));
        } else if (isInfinity()) {
            return new BNum(Math.PI / 2);
        }
        // DNE or infinitesimal
        return BNum.DNE;
    }

    /**
     * Returns a new BNum that is the arc-cotangent of <b>this</b> as radians. The
     * following are implemented:
     * <ul>
     * <li>DNE
     * <li>Infinity (treated as a lim x-> +- infinity)
     * <li>Infinitesimal (treated as lim x-> 0)
     * <li>Jump discontinuity
     * </ul>
     * <p>
     * <em>Calling acot() does not change the data of <b>this</b>.</em>
     * 
     * @return {@code Math.atan(1.0 / value)}
     */
    public final BNum acot() {
        if (hasValue() && value != 0) {
            return new BNum(Math.atan(1 / value));
        } else if (isInfinity()) {
            return new BNum(0);
        }
        // DNE or infinitesimal
        return BNum.DNE;
    }
    // #endregion

    // #region Hyperbolic Trig
    /**
     * Returns a new BNum that is the hyperbolic sine of <b>this</b> as radians. The
     * following are implemented:
     * <ul>
     * <li>DNE
     * <li>Infinity (treated as a lim x-> +- infinity)
     * <li>Infinitesimal (treated as lim x-> 0)
     * </ul>
     * <p>
     * <em>Calling sinh() does not change the data of <b>this</b>.</em>
     * 
     * @return {@code Math.sinh(value)}
     */
    public final BNum sinh() {
        if (hasValue()) {
            return new BNum(Math.sinh(value));
        } else if (isInfinity()) {
            // exponential def
            return BNum.infinity(isNegative(), 2);
        } else if (isInfinitesimal()) {
            return new BNum(0);
        }
        // DNE
        return BNum.DNE;
    }

    /**
     * Returns a new BNum that is the hyperbolic cosine of <b>this</b> as radians.
     * The following are implemented:
     * <ul>
     * <li>DNE
     * <li>Infinity (treated as a lim x-> +- infinity)
     * <li>Infinitesimal (treated as lim x-> 0)
     * </ul>
     * <p>
     * <em>Calling cosh() does not change the data of <b>this</b>.</em>
     * 
     * @return {@code Math.cosh(value)}
     */
    public final BNum cosh() {
        if (hasValue()) {
            return new BNum(Math.cosh(value));
        } else if (isInfinity()) {
            return BNum.infinity(false, 0);
        } else if (isInfinitesimal()) {
            return new BNum(1);
        }
        // DNE
        return BNum.DNE;
    }

    /**
     * Returns a new BNum that is the hyperbolic tangent of <b>this</b> as radians.
     * The following are implemented:
     * <ul>
     * <li>DNE
     * <li>Infinity (treated as a lim x-> +- infinity)
     * <li>Infinitesimal (treated as lim x-> 0)
     * </ul>
     * <p>
     * <em>Calling tanh() does not change the data of <b>this</b>.</em>
     * 
     * @return {@code Math.tanh(value)}
     */
    public final BNum tanh() {
        if (hasValue()) {
            return new BNum(Math.tanh(value));
        } else if (isInfinity()) {
            return new BNum(isNegative() ? -1 : 1);
        } else if (isInfinitesimal()) {
            return new BNum(0);
        }
        // DNE
        return BNum.DNE;
    }

    /**
     * Returns a new BNum that is the hyperbolic cosecant of <b>this</b> as radians.
     * The following are implemented:
     * <ul>
     * <li>DNE
     * <li>Infinity (treated as a lim x-> +- infinity)
     * <li>Infinitesimal (treated as lim x-> 0)
     * <li>Asymptote when x = 0.
     * </ul>
     * <p>
     * <em>Calling csch() does not change the data of <b>this</b>.</em>
     * 
     * @return {@code 1.0 / Math.sinh(value)}
     */
    public final BNum csch() {
        if (hasValue() && !isZero()) {
            return new BNum(1 / Math.sinh(value));
        } else if (isInfinity()) {
            return new BNum(0);
        }
        // DNE, infinitesimal, or value == 0
        return BNum.DNE;
    }

    /**
     * Returns a new BNum that is the hyperbolic secant of <b>this</b> as radians.
     * The following are implemented:
     * <ul>
     * <li>DNE
     * <li>Infinity (treated as a lim x-> +- infinity)
     * <li>Infinitesimal (treated as lim x-> 0)
     * </ul>
     * <p>
     * <em>Calling sech() does not change the data of <b>this</b>.</em>
     * 
     * @return {@code 1.0 / Math.cosh(value)}
     */
    public final BNum sech() {
        if (hasValue()) {
            return new BNum(1 / Math.cosh(value));
        } else if (isInfinity()) {
            return new BNum(0);
        } else if (isInfinitesimal()) {
            return new BNum(1);
        }
        // DNE
        return BNum.DNE;
    }

    /**
     * Returns a new BNum that is the hyperbolic cotangent of <b>this</b> as
     * radians. The following are implemented:
     * <ul>
     * <li>DNE
     * <li>Infinity (treated as a lim x-> +- infinity)
     * <li>Infinitesimal (treated as lim x-> 0)
     * <li>Asymptote when x = 0
     * </ul>
     * <p>
     * <em>Calling coth() does not change the data of <b>this</b>.</em>
     * 
     * @return {@code 1.0 / Math.tanh(value)}
     */
    public final BNum coth() {
        if (hasValue() && value != 0) {
            return new BNum(1 / Math.tanh(value));
        } else if (isInfinity()) {
            return new BNum(0);
        }
        // DNE, infinitesimal, or value == 0
        return BNum.DNE;
    }
    // #endregion

    // #region Hyperbolix Inv Trig
    /**
     * Returns a new BNum that is the hyperbolic arc-sine of <b>this</b> as radians.
     * The following are implemented:
     * <ul>
     * <li>DNE
     * <li>Infinity (treated as a lim x-> +- infinity)
     * <li>Infinitesimal (treated as lim x-> 0)
     * </ul>
     * <p>
     * <em>Calling asinh() does not change the data of <b>this</b>.</em>
     * 
     * @return the logarithmic definition:
     *         {@code Math.log(value + sqrt(value^2 + 1))}
     */
    public final BNum asinh() {
        if (hasValue()) {
            double innerVal = value + Math.sqrt(Math.pow(value, 2) + 1);
            return new BNum(Math.log(innerVal));
        } else if (isInfinity()) {
            return BNum.infinity(isNegative(), 0);
        } else if (isInfinitesimal()) {
            return new BNum(0);
        }
        // DNE
        return BNum.DNE;
    }

    /**
     * Returns a new BNum that is the hyperbolic arc-cosine of <b>this</b> as
     * radians. The following are implemented:
     * <ul>
     * <li>DNE
     * <li>Infinity (treated as a lim x-> +- infinity)
     * <li>Infinitesimal (treated as lim x-> 0)
     * <li>Domain of [1, infty)
     * </ul>
     * <p>
     * <em>Calling acosh() does not change the data of <b>this</b>.</em>
     * 
     * @return the logarithmic definition:
     *         {@code Math.log(value + sqrt(value^2 + 1))}
     */
    public final BNum acosh() {
        if (hasValue() && value >= 1) {
            double innerVal = value + Math.sqrt(Math.pow(value, 2) - 1);
            return new BNum(Math.log(innerVal));
        } else if (isInfinity() && !isNegative()) {
            return BNum.infinity(false, 0);
        }
        // DNE, infinitesimal, or outside the domain
        return BNum.DNE;
    }

    /**
     * Returns a new BNum that is the hyperbolic arc-tangent of <b>this</b> as
     * radians. The following are implemented:
     * <ul>
     * <li>DNE
     * <li>Infinity (treated as a lim x-> +- infinity)
     * <li>Infinitesimal (treated as lim x-> 0)
     * <li>Domain of (-1, 1)
     * </ul>
     * <p>
     * <em>Calling atanh() does not change the data of <b>this</b>.</em>
     * 
     * @return the logarithmic definition:
     *         {@code 0.5 * Math.log((1 + value) / (1 - value))}
     */
    public final BNum atanh() {
        if (hasValue() && Math.abs(value) < 1) {
            double innerVal = (1 + value) / (1 - value);
            return new BNum(0.5 * Math.log(innerVal));
        } else if (isInfinitesimal()) {
            return new BNum(0);
        }
        // DNE, infinity, or outside of the domain
        return BNum.DNE;
    }

    /**
     * Returns a new BNum that is the hyperbolic arc-cosecant of <b>this</b> as
     * radians. The following are implemented:
     * <ul>
     * <li>DNE
     * <li>Infinity (treated as a lim x-> +- infinity)
     * <li>Infinitesimal (treated as lim x-> 0)
     * <li>Asymptote when x = 0.
     * </ul>
     * <p>
     * <em>Calling acsch() does not change the data of <b>this</b>.</em>
     * 
     * @return the logarithmic definition:
     *         {@code Math.log(x^-1 + sqrt(x^-2 + 1) )}
     */
    public final BNum acsch() {
        if (hasValue() && !isZero()) {
            double innerVal = (1 / value) + Math.sqrt((1 / Math.pow(value, 2)) + 1);
            return new BNum(Math.log(innerVal));
        } else if (isInfinity()) {
            return BNum.infinity(isNegative(), 0);
        } else if (isInfinitesimal()) {
            return new BNum(0);
        }
        // DNE or x = 0
        return BNum.DNE;
    }

    /**
     * Returns a new BNum that is the hyperbolic arc-cosecant of <b>this</b> as
     * radians. The following are implemented:
     * <ul>
     * <li>DNE
     * <li>Infinity (treated as a lim x-> +- infinity)
     * <li>Infinitesimal (treated as lim x-> 0)
     * <li>Domain of (0, 1]
     * </ul>
     * <p>
     * <em>Calling acsch() does not change the data of <b>this</b>.</em>
     * 
     * @return the logarithmic definition:
     *         {@code Math.log(x^-1 + sqrt(x^-2 - 1) )}
     */
    public final BNum asech() {
        if (hasValue() && value > 0 && value <= 1) {
            double innerVal = (1 / value) + Math.sqrt((1 / Math.pow(value, 2)) - 1);
            return new BNum(Math.log(innerVal));
        }
        return BNum.DNE;
    }
    // #endregion

    // #region Combinatorics
    /**
     * Returns a new BNum that is the factorial of <b>this</b>.
     * 
     * <p>
     * <em>Calling factorial() does not change the data of <b>this</b></em>.
     * 
     * @return
     */
    public final BNum factorial() {
        if (this.isInfinity()) {
            return BNum.infinity(false, 3);
        }

        if (this.isInteger()) {
            int intVal = (int) value;
            int outVal = 1;
            for (int i = intVal; i > 0; i--) {
                outVal *= i;
            }
            return new BNum(outVal);
        }

        /* Gamma Function territory */
        return BNum.DNE;
    }

    /**
     * A permutation is the number of ways <b>r</b> objects can be sorted into
     * <b>n</b> slots. The order does number, so 123 is not the same as 132.
     * 
     * <p>
     * <em>Calling permutation() does not change the data of either of the
     * args</em>.
     * 
     * @param argN real and positive integer: should be a Value or a number
     *             primitive
     * @param argR real and positive integer: should be a Value or a number
     *             primitive
     * @return a real value
     */
    public static final BNum permutation(Object argN, Object argR) {
        BNum n = BNum.fromObj(argN);
        BNum r = BNum.fromObj(argR);

        if (!n.isInteger() || !r.isInteger()) {
            throw new ArithmeticException("Either value " + n + " and/or value " + r + " are not integers.");
        } else if (r.compareTo(n) > 0) {
            throw new ArithmeticException("Value " + r + " is greater than the value " + n + ".");
        } else if (r.compareTo(n) == 0) {
            return r.factorial();
        }

        BNum output = new BNum(1);
        int nVal = (int) n.getValue();
        int rVal = (int) r.getValue();
        for (int i = nVal; i > (nVal - rVal); i--) {
            output.multiply(i);
        }
        return output;
    }

    /**
     * A combination is the amount of ways <b>r</b> objects can be picked out of
     * <b>n</b> objects. The order does not matter, so 123 is the same as 132.
     * 
     * <p>
     * <em>Calling combination() does not change the data of either of the
     * args</em>.
     * 
     * @param argN real and positive integer: should be a Value or a number
     *             primitive
     * @param argR real and positive integer: should be a Value or a number
     *             primitive
     * @return a real value
     */
    public static final BNum combination(Object argN, Object argR) {
        BNum n = BNum.fromObj(argN);
        BNum r = BNum.fromObj(argR);

        if (!n.isInteger() || !r.isInteger()) {
            throw new ArithmeticException("Either value " + n + " and/or value " + r + " are not integers.");
        } else if (r.compareTo(n) > 0) {
            throw new ArithmeticException("Value " + r + " is greater than the value " + n + ".");
        } else if (r.compareTo(n) == 0) {
            return new BNum(1);
        }

        return permutation(argN, argR).divide(r.factorial());
    }
    // #endregion

    // #region Number Theory
    /**
     * Finds which two numbers makes the ratio.
     * 
     * <p>
     * <em>Calling findRatio() does not change the data of <b>this</b></em>.
     * 
     * @param sigFigs the multiplier of precision, where the fraction has a
     *                10^(-2 * <b>sigFigs</b>) tolerance due to how double can
     *                sometimes be imprecise.
     * @return
     */
    public final int[] findRatio(int sigFigs) {
        int integer = 0;
        if (value < 0) {
            integer = -1;
        }
        if (Math.abs(value) > 1) {
            integer += (int) Math.abs(value);
        }

        double valToFind = Math.abs(value - (int) value);
        double epsilon = Math.pow(10, -2 * sigFigs);
        for (int i = 2; i < Constants.Number.MAX_PRIME_NUMBER + 1; i++) {
            for (int j = 1; j < i; j++) {
                if (NumberUtils.withinEpsilon((double) j / i, valToFind, epsilon)) {
                    int[] output = new int[2];
                    output[0] = (integer * i) + j;
                    output[1] = i;
                    return output;
                }
            }
        }

        /* Turning into fraction was unsuccessful */
        int[] out = { 1, 1 };
        return out;
    }

    public final int[] findRatio() {
        return this.findRatio(Constants.Number.SIG_FIGS);
    }
    // #endregion

    /**
     * Returns a new BNum that is the hyperbolic arc-cotangent of <b>this</b> as
     * radians. The following are implemented:
     * <ul>
     * <li>DNE
     * <li>Infinity (treated as a lim x-> +- infinity)
     * <li>Infinitesimal (treated as lim x-> 0)
     * <li>Domain of (-infty, -1) U (1, infty)
     * </ul>
     * <p>
     * <em>Calling acoth() does not change the data of <b>this</b>.</em>
     * 
     * @return the logarithmic definition:
     *         {@code 0.5 * Math.log((value + 1) / (value - 1))}
     */
    public final BNum acoth() {
        if (hasValue() && Math.abs(value) > 1) {
            double innerVal = (value + 1) / (value - 1);
            return new BNum(0.5 * Math.log(innerVal));
        } else if (isInfinity()) {
            return BNum.infinity(isNegative(), 0);
        }
        // DNE or infinitesimal
        return BNum.DNE;
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
        return value % 1 == 0;
    }

    public final boolean isRational(int sigFigs) {
        /* Variables */
        double valToFind = Math.abs(value - (int) value);
        double epsilon = Math.pow(10, -2 * sigFigs);

        for (int i = 2; i < Constants.Number.MAX_PRIME_NUMBER + 1; i++) {
            for (int j = 1; j < i; j++) {
                if (NumberUtils.withinEpsilon((double) j / i, valToFind, epsilon)) {
                    return true;
                }
            }
        }

        return false;
    }

    public final boolean isRational() {
        return this.isRational(Constants.Number.SIG_FIGS);
    }

    /**
     * 
     * @return {@code true} if <b>this</b> is not an infinitesimal, not infinity,
     *         nor DNE
     */
    public final boolean hasValue() {
        return !isDNE() && !isInfinitesimal() && !isInfinity();
    }

    /**
     * 
     * @return {@code true} if the order is greater than
     *         {@link whyxzee.blackboard.Constants.Number#VALUE_ORDER} and it is not
     *         DNE.
     *         <li>{@code false} if otherwise
     */
    public final boolean isInfinity() {
        // not a value && not DNE
        return order > BNum.VALUE_ORDER && !isDNE();
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
        if (arg instanceof BNum) {
            BNum other = (BNum) arg;

            // ensure that they are the same type
            if (size != other.getSize() || order != other.getOrder()) {
                return false;
            } else if (isInfinitesimal() && other.isInfinitesimal()) {
                return string.equals(other.toString());
            }
            return NumberUtils.precisionCheck(value, other.getValue());

        } else if (NumberUtils.isNumPrimitive(arg)) { // doubt that this is needed
            return NumberUtils.precisionCheck(value, NumberUtils.doubleFromObj(arg));
        }
        return false;
    }

    /**
     * 
     * @param arg0
     * @return <b>Negative value</b> if this is less than arg0
     *         <li><b>Zero</b> if this equals arg0
     *         <li><b>Positive value</b> if this is greater than arg0
     */
    @Override
    public int compareTo(BNum arg0) {
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

    public static final boolean inClosedRange(Object argLBound, Object argMiddle, Object argUBound) {
        BNum lBound = BNum.fromObj(argLBound);
        BNum middle = BNum.fromObj(argMiddle);
        BNum uBound = BNum.fromObj(argUBound);

        return (lBound.compareTo(middle) <= 0) && (middle.compareTo(uBound) <= 0);
    }
    // #endregion
}
