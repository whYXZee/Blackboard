package whyxzee.blackboard.utils;

public class SpecialFunctions {
    public static double gammaFunction(double input) {
        if (ArithmeticUtils.isInteger(input)) {
            // if an integer, do the n-1 identity
            return factorial((int) input - 1);
        }
        return 0;
    }

    public static int factorial(int input) {
        if (input > 20) {
            throw new RuntimeException("Factorial size too large at value " + input);
        }

        int output = 0;
        for (int i = input; i > 1; i--) {
            output *= i;
        }

        return output;
    }

    public static double log_b(double base, double value) {
        return Math.log(value) / Math.log(base);
    }

}
