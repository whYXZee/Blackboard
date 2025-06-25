package whyxzee.blackboard.utils;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.pure.numbers.Complex;
import whyxzee.blackboard.math.pure.numbers.NumberTheory;
import whyxzee.blackboard.math.pure.numbers.Complex.ComplexType;
import whyxzee.blackboard.math.pure.numbers.BNum;

public final class NumberUtils {
    // #region Conversion Methods
    /**
     * Turns a number primitive into a double value. The following are valid
     * primitive data types:
     * <ul>
     * <li>byte
     * <li>short
     * <li>int
     * <li>long
     * <li>float
     * <li>double
     * 
     * @param arg
     * @return
     */
    public static final double doubleFromObj(Object arg) {
        if (arg instanceof Double) {
            return (double) arg;
        } else if (arg instanceof Integer) {
            return (int) arg;
        } else if (arg instanceof Byte) {
            return (byte) arg;
        } else if (arg instanceof Short) {
            return (short) arg;
        } else if (arg instanceof Long) {
            return (long) arg;
        } else if (arg instanceof Float) {
            return (float) arg;
        }
        return 0;
    }

    /**
     * Checks if the <b>arg</b> is one of the following types:
     * <ul>
     * <li>byte
     * <li>short
     * <li>int
     * <li>long
     * <li>float
     * <li>double
     * 
     * @param arg
     * @return
     */
    public static final boolean isNumPrimitive(Object arg) {
        return arg instanceof Double || arg instanceof Integer
                || arg instanceof Byte || arg instanceof Short || arg instanceof Long || arg instanceof Float;
    }
    // #endregion

    /**
     * Counts the number of significant figures in <b>value</b>.
     * 
     * @param value
     * @return the number of significant figures in <b>value</b>
     */
    public static final int numOfDigits(double value) {
        /* Initializing variables */
        char[] charArray = Double.toString(value).toCharArray();
        boolean currentlyTrailingZero = false;

        /* Algorithm */
        int digits = 0;
        int trailingZeros = 0;
        for (char i : charArray) {
            if (currentlyTrailingZero) {
                if (i == '.') {
                    currentlyTrailingZero = false;
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

    // #region String Optimization
    /**
     * "Optimizes" a value for user readability through the following criteria:
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
     * @param value
     * @param sigFigs the amount of significant figures in the output.
     * @return an optimized String based on <b>sigFigs</b>.
     */
    public static final String valueToString(double value, int sigFigs) {
        // if (withinEpsilon(value, 1, Math.pow(10, -2 * sigFigs))) {
        // return "";
        // } else if (withinEpsilon(value, -1, Math.pow(10, -2 * sigFigs))) {
        // return "-";
        // }

        // round thing if its smth like .000001 or .99999
        if (withinEpsilon(value, (int) value, Math.pow(10, -2 * sigFigs))) {
            return Integer.toString((int) value);
        } else if (withinEpsilon(value, (int) value + 1, Math.pow(10, -2 * sigFigs))) {
            return Integer.toString((int) value + 1);
        } else if (withinEpsilon(value, (int) value - 1, Math.pow(10, -2 * sigFigs))) {
            return Integer.toString((int) value - 1);
        }

        if (!NumberTheory.isInteger(value)) {
            // can be represented by a fraction
            return toFraction(value, sigFigs);

        } else if (numOfDigits(value) > sigFigs) {
            return toScientific(value, sigFigs);

        } else {
            return Integer.toString((int) value);
        }
    }

    /**
     * "Optimizes" a value for user readability through the following criteria:
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
     * @param value
     * @return an optimized String based on
     *         {@link whyxzee.blackboard.Constants.Number#SIG_FIGS}.
     */
    public static final String valueToString(double value) {
        return valueToString(value, Constants.Number.SIG_FIGS);
    }

    /**
     * 
     * @param value
     * @param sigFigs the multiplier of precision, where the fraction has a
     *                10^(-2 * <b>sigFigs</b>) tolerance due to how double can
     *                sometimes be imprecise.
     * @return
     */
    public static final String toFraction(double value, int sigFigs) {
        String integer = "";
        if (value < 0) {
            integer += "-";
        }
        if (Math.abs(value) > 1) {
            integer += (int) Math.abs(value);
        }

        double valToFind = Math.abs(value - (int) value);
        double epsilon = Math.pow(10, -2 * sigFigs);
        for (int i = 2; i < Constants.Number.MAX_PRIME_NUMBER + 1; i++) {
            for (int j = 1; j < i; j++) {
                if (withinEpsilon((double) j / i, valToFind, epsilon)) {
                    return integer + UnicodeUtils.intToSuperscript(j) + Constants.Unicode.FRACTION_SLASH
                            + UnicodeUtils.intToSubscript(i);
                }
            }
        }

        /* Turning into fraction was unsuccessful */
        return truncate(value, sigFigs);
    }

    public static String toScientific(double value, int sigFigs) {
        String output = "";
        if (value < 0) {
            // negative value
            output += "-";
            value *= -1; // logs cannot input a negative val
        }
        char[] charArray = Double.toString(value).toCharArray();

        for (int i = 0; i < sigFigs + 2; i++) {
            output += charArray[i];
        }

        output += "E" + (int) Math.floor(Math.log10(value));
        return output;
    }

    /**
     * Returns a truncated value after <b>sigFigs</b> number of decimal points.
     * 
     * @param value
     * @param sigFigs
     * @return
     */
    public static final String truncate(double value, int sigFigs) {
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

    // #region Range

    /**
     * Checks if a number is within a range.
     * 
     * @param value   The actual value
     * @param desired What value it should be
     * @param epsilon Half of the range. Added and subtracted from the desired.
     * @return {@code true} if within epsilon, false if otherwise.
     */
    public static final boolean withinEpsilon(double value, double desired, double epsilon) {
        return (desired + epsilon > value) && (desired - epsilon < value);
    }

    /**
     * Quick method for checking how close a number is to another. Uses the
     * {@link whyxzee.blackboard.math.pure.numbers.NumberTheory#withinEpsilon(double, double, double)}
     * with an epsilon of {@code Math.pow(10, -2 * SIG_FIGS)}
     * 
     * @param value
     * @param desired
     * @return
     */
    public static final boolean precisionCheck(double value, double desired) {
        return withinEpsilon(value, desired, Math.pow(10, -2 * Constants.Number.SIG_FIGS));
    }

    /**
     * Quick method for checking how close a number is to another. Uses the
     * {@link whyxzee.blackboard.math.pure.numbers.NumberTheory#withinEpsilon(double, double, double)}
     * with an epsilon of {@code Math.pow(10, -2 * sigFigs)}
     * 
     * @param value
     * @param desired
     * @param sigFigs
     * @return
     */
    public static final boolean precisionCheck(double value, double desired, int sigFigs) {
        return withinEpsilon(value, desired, Math.pow(10, -2 * sigFigs));
    }

    public static final boolean inOpenRange(double value, double lower, double upper) {
        return lower < value && value < upper;
    }

    public static final double clamp(double min, double max, double value) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        }
        return value;
    }

    public static final byte clampByte(double value) {
        return (byte) clamp(Byte.MIN_VALUE, Byte.MAX_VALUE, value);
    }
    // #endregion

    public static final Complex powWithInf(double base, Complex power) {
        if (!power.isAZero() && !power.isBZero()) {
            // "complex" infinity
            if (base < 0) {
                // negative base -> converges
                return Complex.DNE();
            } else if (power.getA().isNegative()) {
                // negative power, positive base
                return Complex.cmplx(0, 0);
            }

            double cTheta = power.getB().getValue() * Math.log(base);
            return Complex.fromPolar(ComplexType.COMPLEX, BNum.infinity(false, 1), cTheta);
        }

        return Complex.cmplx(new BNum(base).power(power.getA()), 0);
    }

}
