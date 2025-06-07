package whyxzee.blackboard.utils;

public class BooleanUtils {
    public static final boolean xor(boolean a, boolean b) {
        return a != b;
    }

    public static final boolean imply(boolean a, boolean b) {
        return !a || b;
    }

    public static final boolean inverseImply(boolean a, boolean b) {
        return a || !b;
    }

    public static final boolean biImply(boolean a, boolean b) {
        return a == b;
    }
}
