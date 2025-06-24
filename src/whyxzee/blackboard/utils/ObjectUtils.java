package whyxzee.blackboard.utils;

import whyxzee.blackboard.math.pure.numbers.Complex;
import whyxzee.blackboard.math.pure.numbers.BNum;
import whyxzee.blackboard.math.utils.pure.NumberUtils;

/**
 * A utility class for transforming an arbitrary object into acceptable
 * Blackboard data types.
 */
public final class ObjectUtils {
    // #region Number Primitives
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

    // #region ComplexNum
    /**
     * Creates a ComplexNum from any given Object. For a non-DNE ComplexNum, input
     * one of the following:
     * <ul>
     * <li>Value
     * <li>ComplexNum
     * <li>Number Primitive
     * 
     * @param arg
     * @return
     */
    public static final Complex objToComplex(Object arg) {
        if (arg instanceof Complex) {
            return (Complex) arg;
        } else if (isNumPrimitive(arg)) {
            return new Complex(doubleFromObj(arg), new BNum(0));
        } else if (arg instanceof BNum) {
            return new Complex((BNum) arg, new BNum(0));
        }

        /* DNE */
        return NumberUtils.DNE();
    }
    // #endregion
}
