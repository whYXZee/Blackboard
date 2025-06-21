package whyxzee.blackboard.math.pure.numbers;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;

public class NumberTheory {
    // #region Elementary Num Theory
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
    // #endregion

    // #region Ratio of Numbers
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

    /**
     * 
     * Finds which two numbers makes the ratio, using .
     * 
     * @param ratio
     * @return a ratio based on
     *         {@link whyxzee.blackboard.Constants.Number#SIG_FIGS}.
     */
    public static final int[] findRatio(double ratio) {
        return findRatio(ratio, Constants.Number.SIG_FIGS);
    }
    // #endregion

    // #region Factors
    /**
     * TODO: maybe the class can be a method?
     * 
     * <p>
     * The functionality of this class has been checked on <b>6/15/2025</b> and
     * nothing has changed since.
     */
    public static class Factors {
        /* Variables */
        private static ArrayList<ComplexNum> numbers = new ArrayList<ComplexNum>();

        /**
         * Gets all of the factors of the <b>number</b>.
         * 
         * @param number Must be an integer.
         * @return
         */
        public static final ArrayList<ComplexNum> factorsOf(ComplexNum number) {
            numbers = new ArrayList<ComplexNum>();
            if (!number.isReal()) {
                throw new UnsupportedOperationException("A number can only have factors iff it is an integer");
            }

            double a = number.getA().abs();
            for (int i = 1; i <= a / 2; i++) {
                if ((a / i) % 1 == 0) {
                    add(new ComplexNum(a / i, 0), new ComplexNum(i, 0));
                }
            }

            return numbers;
        }

        ///
        /// Get & Set Methods
        ///
        private static final void add(ComplexNum... factors) {
            for (ComplexNum i : factors) {
                if (!numbers.contains(i)) {
                    numbers.add(i);
                }
            }
        }
    }
    // #endregion

    // #region Number Type Bools
    public static final boolean isInteger(double value) {
        return value % 1 == 0;
    }

    public static final boolean isInteger(ComplexNum value) {
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

        for (int i = 2; i < Constants.Number.MAX_PRIME_NUMBER + 1; i++) {
            for (int j = 1; j < i; j++) {
                if (NumberUtils.withinEpsilon((double) j / i, valToFind, epsilon)) {
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
        return isRational(value, Constants.Number.SIG_FIGS);
    }
    // #endregion
}
