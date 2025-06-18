package whyxzee.blackboard.math.pure.numbers;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.utils.UnicodeUtils;

public class NumberUtils {
    //
    // Number Theory
    //
    /**
     * A triangle number (denoted by T_n) is the sum of all numbers from 1 to
     * <b>n</b>
     * 
     * @param n the nth triangular number (the final number added). <b>n</b> must be
     *          a positive integer.
     * @return
     */
    public static final int triangleNumber(int n) {
        if (n <= 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }
        return (n * (n + 1)) / 2;
    }

    /**
     * A tetrahedral number (denoted by Te_n) is the sum of all triangular numbers
     * from 1 to <b>n</b>.
     * 
     * @param n
     * @return
     */
    public static final int tetrahedralNumber(int n) {
        if (n <= 0) {
            return 0;
        }

        int output = 0;
        for (int i = 1; i < n + 1; i++) {
            output += triangleNumber(i);
        }
        return output;
    }

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

    /**
     * Finds which two numbers makes the ratio.
     * 
     * @param ratio
     * @param sigFigs the multiplier of precision, where the fraction has a
     *                10^(-2 * <b>sigFigs</b>) tolerance due to how double can
     *                sometimes be imprecise.
     * @return
     */
    public static final int[] findRatio(double ratio, int sigFigs) {
        int integer = 0;
        if (ratio < 0) {
            integer = -1;
        }
        if (Math.abs(ratio) > 1) {
            integer += (int) Math.abs(ratio);
        }

        double valToFind = Math.abs(ratio - (int) ratio);
        double epsilon = Math.pow(10, -2 * sigFigs);
        for (int i = 2; i < Constants.NumberConstants.MAX_PRIME_NUMBER + 1; i++) {
            for (int j = 1; j < i; j++) {
                if (withinEpsilon((double) j / i, valToFind, epsilon)) {
                    int[] output = new int[2];
                    output[0] = (integer * i) + j;
                    output[1] = i;
                    return output;
                }
            }
        }

        /* Turning into fraction was unsuccessful */
        return new int[2];
    }

    /**
     * 
     * Finds which two numbers makes the ratio, using .
     * 
     * @param ratio
     * @return a ratio based on
     *         {@link whyxzee.blackboard.Constants.NumberConstants#SIG_FIGS}.
     */
    public static final int[] findRatio(double ratio) {
        return findRatio(ratio, Constants.NumberConstants.SIG_FIGS);
    }

    /**
     * <p>
     * The functionality of this class has been checked on <b>6/15/2025</b> and
     * nothing has changed since.
     */
    public static class Factors {
        /* Variables */
        private static ArrayList<BNumber> numbers = new ArrayList<BNumber>();

        /**
         * Gets all of the factors of the <b>number</b>.
         * 
         * @param number Must be an integer.
         * @return
         */
        public static final ArrayList<BNumber> factorsOf(BNumber number) {
            numbers = new ArrayList<BNumber>();
            if (!number.isReal()) {
                throw new UnsupportedOperationException();
            }

            double a = Math.abs(number.getA());
            for (int i = 1; i <= a / 2; i++) {
                if ((a / i) % 1 == 0) {
                    add(new BNumber(a / i, 0), new BNumber(i, 0));
                }
            }

            return numbers;
        }

        ///
        /// Get & Set Methods
        ///
        private static final void add(BNumber... factors) {
            for (BNumber i : factors) {
                if (!contains(i)) {
                    numbers.add(i);
                }
            }
        }

        ///
        /// Boolean Methods
        ///
        private static final boolean contains(BNumber number) {
            for (BNumber i : numbers) {
                if (i.equals(number)) {
                    return true;
                }
            }
            return false;
        }
    }

    ///
    /// String Optimization
    ///
    // #region
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

        if (!isInteger(value)) {
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
     *         {@link whyxzee.blackboard.Constants.NumberConstants#SIG_FIGS}.
     */
    public static final String valueToString(double value) {
        return valueToString(value, Constants.NumberConstants.SIG_FIGS);
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
        for (int i = 2; i < Constants.NumberConstants.MAX_PRIME_NUMBER + 1; i++) {
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

        for (int i = 0; i < sigFigs; i++) {
            output += charArray[i];
            if (i == 0 && sigFigs != 1) {
                output += ".";
            }
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

    ///
    /// Boolean Methods
    ///
    // #region Number Type
    public static final boolean isInteger(double value) {
        return value % 1 == 0;
    }

    public static final boolean isInteger(BNumber value) {
        return value.mod(1).equals(0);
    }

    /**
     * 
     * @param value
     * @param sigFigs
     * @return
     */
    public static final boolean isRational(double value, int sigFigs) {
        /* Variables */
        double valToFind = Math.abs(value - (int) value);
        double epsilon = Math.pow(10, -2 * sigFigs);

        for (int i = 2; i < Constants.NumberConstants.MAX_PRIME_NUMBER + 1; i++) {
            for (int j = 1; j < i; j++) {
                if (withinEpsilon((double) j / i, valToFind, epsilon)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 
     * @param value
     * @return
     */
    public static final boolean isRational(double value) {
        return isRational(value, Constants.NumberConstants.SIG_FIGS);
    }

    public static final boolean containsDNE(BNumber... numbers) {
        for (BNumber i : numbers) {
            if (i.isDNE()) {
                return true;
            }
        }
        return false;
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
     * {@link whyxzee.blackboard.math.pure.numbers.NumberUtils#withinEpsilon(double, double, double)}
     * with an epsilon of {@code Math.pow(10, -2 * SIG_FIGS)}
     * 
     * @param value
     * @param desired
     * @return
     */
    public static final boolean precisionCheck(double value, double desired) {
        return withinEpsilon(value, desired, Math.pow(10, -2 * Constants.NumberConstants.SIG_FIGS));
    }

    /**
     * Quick method for checking how close a number is to another. Uses the
     * {@link whyxzee.blackboard.math.pure.numbers.NumberUtils#withinEpsilon(double, double, double)}
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
    // #endregion
}
