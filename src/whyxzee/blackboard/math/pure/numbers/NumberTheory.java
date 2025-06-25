package whyxzee.blackboard.math.pure.numbers;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.utils.NumberUtils;

public class NumberTheory {

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
        private static ArrayList<Complex> numbers = new ArrayList<Complex>();

        /**
         * Gets all of the factors of the <b>number</b>.
         * 
         * @param number Must be an integer.
         * @return
         */
        public static final ArrayList<Complex> factorsOf(Complex number) {
            numbers = new ArrayList<Complex>();
            if (!number.isReal()) {
                throw new UnsupportedOperationException("A number can only have factors iff it is an integer");
            }

            double a = number.getA().abs();
            for (int i = 1; i <= a / 2; i++) {
                if ((a / i) % 1 == 0) {
                    add(new Complex(a / i, 0), new Complex(i, 0));
                }
            }

            return numbers;
        }

        ///
        /// Get & Set Methods
        ///
        private static final void add(Complex... factors) {
            for (Complex i : factors) {
                if (!numbers.contains(i)) {
                    numbers.add(i);
                }
            }
        }
    }
    // #endregion
}
