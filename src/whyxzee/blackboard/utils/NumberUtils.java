package whyxzee.blackboard.utils;

public final class NumberUtils {
    // #region Conversion Methods
    /**
     * Turns a number primitive into a double value. The following are valid
     * primitive data types:
     * <ul>
     * <li>byte
     * <li>short
     * <li>int
     * <li>long
     * <li>float
     * <li>double
     * 
     * @param arg
     * @return
     */
    public static final double doubleFromObj(Object arg) {
        if (arg instanceof Double) {
            return (double) arg;
        } else if (arg instanceof Integer) {
            return (int) arg;
        } else if (arg instanceof Byte) {
            return (byte) arg;
        } else if (arg instanceof Short) {
            return (short) arg;
        } else if (arg instanceof Long) {
            return (long) arg;
        } else if (arg instanceof Float) {
            return (float) arg;
        }
        return 0;
    }

    /**
     * Checks if the <b>arg</b> is one of the following types:
     * <ul>
     * <li>byte
     * <li>short
     * <li>int
     * <li>long
     * <li>float
     * <li>double
     * 
     * @param arg
     * @return
     */
    public static final boolean isNumPrimitive(Object arg) {
        return arg instanceof Double || arg instanceof Integer
                || arg instanceof Byte || arg instanceof Short || arg instanceof Long || arg instanceof Float;
    }
    // #endregion
}
