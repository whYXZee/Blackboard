package whyxzee.blackboard.utils;

import whyxzee.blackboard.Constants;

public class ArithmeticUtils {
    /**
     * Counts the number of significant figures in <b>value</b>.
     * 
     * @param value
     * @return the number of significant figures in <b>value</b>
     */
    public static int numOfDigits(double value) {
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
    public static String valueToString(double value, int sigFigs) {
        if (withinEpsilon(value, 1, Math.pow(10, -2 * sigFigs))) {
            return "";
        } else if (withinEpsilon(value, -1, Math.pow(10, -2 * sigFigs))) {
            return "-";
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
    public static String valueToString(double value) {
        return valueToString(value, Constants.NumberConstants.SIG_FIGS);
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
        for (int i = 2; i < Constants.NumberConstants.MAX_PRIME_NUMBER + 1; i++) {
            for (int j = 1; j < i; j++) {
                if (withinEpsilon((double) j / i, valToFind, Math.pow(10, -2 * sigFigs))) {
                    return integer + UnicodeUtils.intToSuperscript(j) + Constants.Unicode.FRACTION_SLASH
                            + UnicodeUtils.intToSubscript(i);
                }
            }
        }

        /* Turning into fraction was unsuccessful */
        return truncate(value, sigFigs);
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

    //
    // Boolean methods
    //
    public static boolean isInteger(double value) {
        return value % 1 == 0;
    }

    public static final boolean isDivisibleBy(int value, int num) {
        return value % num == 0;
    }

    public static final boolean isDivisibleBy(double value, int num) {
        return value % num == 0;
    }

    public static final boolean isDivisibleBy(double value, double num) {
        return value % num == 0;
    }

    public static boolean isEven(double value) {
        return isDivisibleBy(value, 2);
    }

    public static final boolean withinEpsilon(double value, double desired, double epsilon) {
        return (desired + epsilon > value) && (desired - epsilon < value);
    }

    public static final boolean isRational(double value, int sigFigs) {
        double valToFind = Math.abs(value - (int) value);
        for (int i = 2; i < Constants.NumberConstants.MAX_PRIME_NUMBER + 1; i++) {
            for (int j = 1; j < i; j++) {
                if (withinEpsilon((double) j / i, valToFind, Math.pow(10, -2 * sigFigs))) {
                    return true;
                }
            }
        }

        return false;
    }

    public static final boolean isRational(double value) {
        return isRational(value, Constants.NumberConstants.SIG_FIGS);
    }
}
