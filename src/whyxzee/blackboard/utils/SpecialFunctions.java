package whyxzee.blackboard.utils;

public class SpecialFunctions {
    public static double gammaFunction(double input) {
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

        int denomInput = (int) Math.pow(10, ArithmeticUtils.numOfDigits(input));
        int numInput = (int) (input * denomInput);
        /* +- p/q */
        if (Math.abs(input) < 1) {
            /* Initializing variables */
        }

        /* n +- p/q */
        if (isInputNegative) {
            /* Initializing variables */
            int intInput = (int) -input + 1; // integer part of the input
            double difference = intInput - input;
            denomInput = (int) Math.pow(10, ArithmeticUtils.numOfDigits(difference));
            numInput = (int) (difference * denomInput);

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
            denomInput = (int) Math.pow(10, ArithmeticUtils.numOfDigits(input));
            numInput = (int) (input * denomInput);

            /* Function */
            double piNotation = 1;
            for (int i = 1; i < intInput + 1; i++) {
                piNotation *= numInput + (i * denomInput) - denomInput;
            }
            return gammaFunction((double) numInput / denomInput) * piNotation * Math.pow(denomInput, -intInput);
        }
    }

    public static int factorial(int input) {
        if (input > 20) {
            throw new RuntimeException("Factorial size too large at value " + input);
        }

        int output = 1;
        for (int i = input; i > 1; i--) {
            output *= i;
        }

        return output;
    }

    public static double log_b(double base, double value) {
        return Math.log(value) / Math.log(base);
    }

    //
    // Boolean Methods
    //
    public static final boolean isApproaching(double value, double approaching) {
        return value < 0.05;
    }

}
