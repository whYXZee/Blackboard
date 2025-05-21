package whyxzee.blackboard.utils;

public class ArithmeticUtils {

    //
    // Boolean methods
    //
    public static boolean isInteger(double value) {
        return value % 1 == 0;
    }

    public static boolean isDivisibleBy(double value, int num) {
        return value % num == 0;
    }

    public static boolean isEven(double value) {
        return isDivisibleBy(value, 2);
    }

}
