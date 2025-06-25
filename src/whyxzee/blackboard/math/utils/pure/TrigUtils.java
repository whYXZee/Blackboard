package whyxzee.blackboard.math.utils.pure;

import java.util.function.Function;

import whyxzee.blackboard.math.pure.numbers.Complex;

/**
 * <p>
 * All operations are in radians.
 */
public final class TrigUtils {
    // #region TrigType
    // links each TrigType to a function so the function is contained here.
    public static enum TrigType {
        /* Circular Trig */
        SIN("sin", Complex::sin),
        COS("cos", Complex::cos),
        TAN("tan", Complex::tan),
        CSC("csc", Complex::csc),
        SEC("sec", Complex::sec),
        COT("cot", Complex::cot),

        /* Circular Inverse Trig */
        ASIN("arcsin", Complex::asin),
        ACOS("arccos", Complex::acos),
        ATAN("arctan", Complex::atan),
        ACSC("arccsc", Complex::acsc),
        ASEC("arcsec", Complex::asec),
        ACOT("arccot", Complex::acot),

        // /* Hyperbolic Trig */
        // SINH("sinh", TrigUtils::sinh),
        // COSH("cosh", TrigUtils::cosh),
        // TANH("tanh", TrigUtils::tanh),
        // CSCH("csch", TrigUtils::csch),
        // SECH("sech", TrigUtils::sech),
        // COTH("coth", TrigUtils::coth),

        // /* Hyperbolic Inverse Trig */
        // ASINH("arsinh", TrigUtils::asinh),
        // ACOSH("arcosh", TrigUtils::acosh),
        // ATANH("artanh", TrigUtils::atanh),
        // ACSCH("arcsch", TrigUtils::acsch),
        // ASECH("arsech", TrigUtils::asech),
        // ACOTH("arcoth", TrigUtils::acoth)
        ;

        private final String string;
        private final Function<Complex, Complex> function;

        private TrigType(String string, Function<Complex, Complex> function) {
            this.string = string;
            this.function = function;
        }

        /**
         * Performs a Trig operation that is linked to the TrigType.
         * 
         * @param value
         * @return
         */
        public final Complex performOp(Complex value) {
            return function.apply(value);
        }

        ///
        /// String/Display
        ///
        public final String getString() {
            return string;
        }
    }

    public static final TrigType inverseOf(TrigType type) {
        switch (type) {
            case SIN:
                return TrigType.ASIN;
            case COS:
                return TrigType.ACOS;
            case TAN:
                return TrigType.ATAN;
            case CSC:
                return TrigType.ACSC;
            case SEC:
                return TrigType.ASEC;
            case COT:
                return TrigType.ACOT;

            case ASIN:
                return TrigType.SIN;
            case ACOS:
                return TrigType.COS;
            case ATAN:
                return TrigType.TAN;
            case ACSC:
                return TrigType.CSC;
            case ASEC:
                return TrigType.SEC;
            case ACOT:
                return TrigType.COT;
            default:
                return TrigType.SIN;
        }
    }
    // #endregion

    // #region Doubles
    /**
     * Checks if a given value is an asymptote of the graph {@code y=tan(x)}.
     * 
     * @param value
     * @return {@code true} if the value is a multiple of PI / 2, but not a multiply
     *         of PI.
     *         <li>{@code false} if otherwise
     */
    public static final boolean tanDiscont(double value) {
        if (value == 0) {
            return false;
        }

        // is not multiple of PI / 2 or is multiple of PI
        // DeMorgan's -> is multiply of PI / 2 and is not multiply of PI
        if (value % (Math.PI / 2) != 0 || value % Math.PI == 0) {
            return false;
        }

        return true;
    }

    /**
     * Checks if a given value is an asymptote of the graph {@code y=cot(x)}.
     * 
     * @param value
     * @return {@code true} if the value is a multiple of PI.
     *         <li>{@code false} if otherwise
     */
    public static final boolean cotDiscont(double value) {
        // if value == 0, then the modulo is zero too
        return value % Math.PI == 0;
    }
    // #endregion

}
