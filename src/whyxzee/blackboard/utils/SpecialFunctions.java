package whyxzee.blackboard.utils;

public class SpecialFunctions {
    public static final double gammaFunction(double input) {
        /* Input */
        boolean isInputNegative = input < 0;

        /* Integer */
        if (input == 0) {
            return Double.NaN;
        } else if (ArithmeticUtils.isInteger(input)) {
            // gamma(-n) = NaN : n-1 identity
            return isInputNegative ? Double.NaN : factorial((int) input - 1);
        }

        /* n +- 0.5 */
        if (ArithmeticUtils.isDivisibleBy(input, 0.5)) {
            /* Initializing variables */
            int numInput = (int) (input * 2); // numerator of the input
            double numOut = Math.pow(Math.PI, 0.5); // numerator of the output

            /* Function */
            if (isInputNegative) {
                /* Negative input */
                numInput *= -1;
                numOut *= Math.pow(2, numInput);
                numOut *= factorial((numInput - 1) / 2);
                if (!ArithmeticUtils.isEven((numInput + 1) / 2)) {
                    // odd power, so -1 is multiplied
                    numOut *= -1;
                }
                return numOut / factorial(numInput);
            } else {
                /* Positive input */
                numOut *= Math.pow(2, 1 - numInput);
                numOut *= factorial(numInput - 1);
                return numOut / factorial((numInput - 1) / 2);
            }
        }

        // no worky :(
        /* (0, 0.5) U (0.5, 1) */
        // if (ArithmeticUtils.inOpenRange(input, 0, 0.5)) {
        // /* (0, 0.5) */

        // } else if (ArithmeticUtils.inOpenRange(input, 0.5, 1)) {
        // /* (0.5, 1) */
        // double exponent = Math.log(gammaFunction(1.5)) + ((input - 0.5) *
        // (-Math.log(gammaFunction(1.5))));
        // return Math.exp(exponent);
        // // stops being accurate after 0.7
        // }

        /* n +- p/q */
        if (isInputNegative) {
            /* Initializing variables */
            int intInput = (int) -input + 1; // integer part of the input
            double difference = intInput - input;
            int denomInput = (int) Math.pow(10, ArithmeticUtils.numOfDigits(difference));
            int numInput = (int) (difference * denomInput);

            /* Function */
            double piNotation = 1;
            for (int i = 1; i < intInput + 1; i++) {
                piNotation *= (i * denomInput) - denomInput;
            }
            if (!ArithmeticUtils.isEven(intInput)) {
                // if intInput is odd
                piNotation *= -1;
            }
            return gammaFunction((double) numInput / denomInput) * Math.pow(denomInput, intInput) / piNotation;
        } else {
            /* Initializing variables */
            int intInput = (int) input; // integer part of the input
            input -= intInput;
            int denomInput = (int) Math.pow(10, ArithmeticUtils.numOfDigits(input));
            int numInput = (int) (input * denomInput);

            /* Function */
            double piNotation = 1;
            for (int i = 1; i < intInput + 1; i++) {
                piNotation *= numInput + (i * denomInput) - denomInput;
            }
            return gammaFunction((double) numInput / denomInput) * piNotation * Math.pow(denomInput, -intInput);
        }
    }

    public static final int factorial(int input) {
        if (input > 20) {
            throw new RuntimeException("Factorial size too large at value " + input);
        } else if (input == 1 || input == 0) {
            return 1;
        } else if (input < 0) {
            return 0;
        }

        return input * factorial(input - 1);
    }

    public static final int permutation(int n, int r) {
        if (n > 20) {
            throw new RuntimeException("Permutation size too large at value " + n);
        }

        if (r > n) {
            // because there is no way to sort r objects into n slots
            return 0;
        } else if (r == 1) {
            // there are only n ways to sort 1 object into n slots
            return n;
        } else if (n == r) {
            return factorial(n);
        }

        int output = 1;
        for (int i = 0; i < r; i++) {
            output *= n - i;
        }
        return output;
    }

    public static final int combination(int n, int r) {
        if (n > 20) {
            throw new RuntimeException("Permutation size too large at value " + n);
        }

        if (r > n) {
            return 0;
        } else if (n == r || r == 1) {
            return 1;
        }

        return permutation(n, r) / factorial(r);
    }

    public static final double log_b(double base, double value) {
        return Math.log(value) / Math.log(base);
    }
}
