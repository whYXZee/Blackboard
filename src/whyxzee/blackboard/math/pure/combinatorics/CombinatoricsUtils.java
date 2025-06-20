package whyxzee.blackboard.math.pure.combinatorics;

import whyxzee.blackboard.math.pure.numbers.BNumber;
import whyxzee.blackboard.math.pure.numbers.NumberTheory;

/**
 * A general-use class for combinatorics.
 * 
 * <p>
 * The functionality of this class has been checked on <b>6/16/2025</b> and
 * nothing has changed since.
 */
public class CombinatoricsUtils {
    // #region Factorial / Gamma
    public static final BNumber gammaFunction(double value) {
        throw new UnsupportedOperationException("Gamma function is unimplemented");
    }

    public static final BNumber gammaFunction(BNumber value) {
        throw new UnsupportedOperationException("Gamma function is unimplemented");
    }

    public static final BNumber factorial(double value) {
        if (NumberTheory.isInteger(value)) {
            return new BNumber(recursiveFactorial((int) value), 0);
        }
        throw new UnsupportedOperationException();
    }

    private static final int recursiveFactorial(int number) {
        if (number <= 1) {
            return 1;
        }
        return number * recursiveFactorial(number - 1);
    }

    public static final BNumber factorial(BNumber value) {
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
    public static final BNumber permutation(int n, int r) {
        if (r > n) {
            throw new ArithmeticException("Value " + r + " is greater than value " + n + " for permutations.");
        } else if (r == n) {
            return factorial(r);
        }

        int output = 1;
        for (int i = n; i > (n - r); i--) {
            output *= i;
        }
        return new BNumber(output, 0);
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
    public static final BNumber combination(int n, int r) {
        if (r > n) {
            throw new ArithmeticException("Value " + r + " is greater than value " + n + " for combinations.");
        } else if (r == n || r == 0) {
            return new BNumber(1, 0);
        }

        return BNumber.divide(permutation(n, r), factorial(r));
    }
    // #endregion

}
