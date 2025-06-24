package whyxzee.blackboard.math.pure.combinatorics;

import whyxzee.blackboard.math.pure.numbers.Complex;
import whyxzee.blackboard.math.pure.numbers.NumberTheory;
import whyxzee.blackboard.math.utils.pure.NumberUtils;

/**
 * A general-use class for combinatorics.
 * 
 * <p>
 * The functionality of this class has been checked on <b>6/16/2025</b> and
 * nothing has changed since.
 */
public class CombinatoricsUtils {
    // #region Factorial / Gamma
    public static final Complex gammaFunction(double value) {
        throw new UnsupportedOperationException("Gamma function is unimplemented");
    }

    public static final Complex gammaFunction(Complex value) {
        throw new UnsupportedOperationException("Gamma function is unimplemented");
    }

    public static final Complex factorial(double value) {
        if (NumberTheory.isInteger(value)) {
            return new Complex(recursiveFactorial((int) value), 0);
        }
        throw new UnsupportedOperationException();
    }

    private static final int recursiveFactorial(int number) {
        if (number <= 1) {
            return 1;
        }
        return number * recursiveFactorial(number - 1);
    }

    public static final Complex factorial(Complex value) {
        if (value.isReal()) {
            return factorial(value);
        }

        throw new UnsupportedOperationException("Factorial for complex/imaginary numbers is unimplemented.");
    }
    // #endregion

    // #region Permutations
    /**
     * A permutation is the number of ways <b>r</b> objects can be sorted into
     * <b>n</b> slots. The order does number, so 123 is not the same as 132.
     * 
     * @param n real and positive integer
     * @param r real and positive integer
     * @return a real value
     */
    public static final Complex permutation(int n, int r) {
        if (r > n) {
            throw new ArithmeticException("Value " + r + " is greater than value " + n + " for permutations.");
        } else if (r == n) {
            return factorial(r);
        }

        int output = 1;
        for (int i = n; i > (n - r); i--) {
            output *= i;
        }
        return new Complex(output, 0);
    }

    // #endregion

    // #region Combinations
    /**
     * A combination is the amount of ways <b>r</b> objects can be picked out of
     * <b>n</b> objects. The order does not matter, so 123 is the same as 132.
     * 
     * @param n real and positive integer
     * @param r real and positive integer
     * @return a real value.
     */
    public static final Complex combination(int n, int r) {
        if (r > n) {
            throw new ArithmeticException("Value " + r + " is greater than value " + n + " for combinations.");
        } else if (r == n || r == 0) {
            return new Complex(1, 0);
        }

        return NumberUtils.divide(permutation(n, r), factorial(r));
    }
    // #endregion

}
