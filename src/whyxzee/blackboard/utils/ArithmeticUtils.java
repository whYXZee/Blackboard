package whyxzee.blackboard.utils;

import java.util.HashMap;

import whyxzee.blackboard.terms.PolynomialTerm;
import whyxzee.blackboard.terms.Term;
import whyxzee.blackboard.terms.variables.Variable;

public class ArithmeticUtils {
    public static int numOfDigits(double value) {
        /* Initializing variables */
        char[] charArray = Double.toString(value).toCharArray();
        boolean currentlyTrailingZero = false;

        /* Algorithm */
        int digits = 0;
        int trailingZeros = 0;
        for (char i : charArray) {
            if (currentlyTrailingZero) {
                if (i != '0') {
                    currentlyTrailingZero = false;
                    digits += 1 + trailingZeros;
                    trailingZeros = 0;
                } else {
                    trailingZeros++;
                }
            } else {
                if (i == '0') {
                    currentlyTrailingZero = true;
                } else if (i != '.') {
                    digits++;
                }
            }
        }
        return digits;
    }

    public static int doubleToNumerator(double value) {
        int digits = numOfDigits(value);
        return (int) (value * Math.pow(10, digits));
    }

    //
    // Boolean methods
    //
    public static boolean isInteger(double value) {
        return value % 1 == 0;
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

    public static boolean inOpenRange(double value, double lowerBound, double upperBound) {
        return (lowerBound < value) && (value < upperBound);
    }

}
