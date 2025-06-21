package whyxzee.blackboard.math.pure.numbers;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.utils.UnicodeUtils;

public class NumberUtils {
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

    // #region DNEs
    public static final boolean containsDNE(ComplexNum... numbers) {
        if (numbers == null) {
            return false;
        }

        for (ComplexNum i : numbers) {
            if (i.isDNE()) {
                return true;
            }
        }
        return false;
    }

    public static final boolean containsDNE(ArrayList<ComplexNum> numbers) {
        if (numbers == null) {
            return false;
        }

        for (ComplexNum i : numbers) {
            if (i.isDNE()) {
                return true;
            }
        }
        return false;
    }
    // #endregion

    // #region Power
    public static final ComplexNum powWithInf(double base, ComplexNum power) {
        boolean posConverge = Math.abs(base) < 1; // converges as x -> pos infinity
        // boolean negConverge = Math.abs(base) > 1; // converges as x -> neg infinity

        if (!power.isAZero() && power.isBZero()) {
            if (base == 1) {
                return new ComplexNum(1, 0);
            } else if (base == -1) {
                return new ComplexNum();
            }

            // TODO: what is 0^infty?

            if (power.getA().isNegative()) {
                // Power is Real Negative Infinity, so x -> neg infinity
                if (posConverge) {
                    if (base < 0) {
                        // negative base
                        return new ComplexNum(); // alternates
                    }
                    Value real = Value.infinity(false, 2);
                    return new ComplexNum(real, 0);
                }
                return new ComplexNum(0, 0);
            } else {
                // Power is Real Positive Infinity, so x -> pos infinity
                if (posConverge) {
                    return new ComplexNum(0, 0);
                }

                if (base < 0) {
                    // neg base
                    return new ComplexNum(); // alternates
                }
                Value real = Value.infinity(false, 2);
                return new ComplexNum(real, 0);
            }

        } else if (power.isAZero() && !power.isBZero()) {
            // "imaginary" infinity
            return new ComplexNum();

        } else {
            // "complex" infinity

            // negative base = DNE always, b = infinity then DNE
            if (base < 0 || power.getB().isInfinity()) {
                return new ComplexNum();
            }

            if (power.getA().isNegative()) {
                // negative power
                return ComplexNum.zero();
            }

            double cTheta = power.getB().getValue() * Math.log(base);
            double cosVal = Math.cos(cTheta);
            Value a, b;
            if (!NumberUtils.precisionCheck(cosVal, 0)) {
                a = Value.infinity(cosVal < 0, 1);
            } else {
                a = new Value(0);
            }

            double sinVal = Math.sin(cTheta);
            if (!NumberUtils.precisionCheck(sinVal, 0)) {
                b = Value.infinity(sinVal < 0, 1);
            } else {
                b = new Value(0);
            }
            return new ComplexNum(a, b);
        }
    }
    // #endregion
}
