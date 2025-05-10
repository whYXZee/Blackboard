package whyxzee.blackboard.utils;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.terms.values.Value;

public class ArithmeticUtils {
    public static Value simplifyFraction(int numerator, int denominator) {
        int tempNumerator = numerator;
        int tempDenominator = denominator;

        for (int prime : Constants.NumberConstants.PRIME_NUMBERS) {
            while (isDivisibleBy(tempNumerator, prime) && isDivisibleBy(tempDenominator, prime)) {
                // if both the numerator and denominator are divisible by a prime, continuously
                // divide by the prime until they aren't
                tempNumerator /= prime;
                tempDenominator /= prime;
            }
        }

        return new Value(0, tempNumerator, tempDenominator);
    }

    //
    // Boolean methods
    //
    public static boolean isInteger(double value) {
        return value % 1 == 0;
    }

    public static boolean isDivisibleBy(double value, int num) {
        return value % num == 0;
    }

}
