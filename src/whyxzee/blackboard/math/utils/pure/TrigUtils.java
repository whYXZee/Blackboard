package whyxzee.blackboard.math.utils.pure;

import java.util.function.Function;

import whyxzee.blackboard.math.pure.numbers.Complex;
import whyxzee.blackboard.math.pure.numbers.BNum;

/**
 * <p>
 * All operations are in radians.
 */
public final class TrigUtils {
    // #region TrigType
    // links each TrigType to a function so the function is contained here.
    public static enum TrigType {
        /* Circular Trig */
        SIN("sin", TrigUtils::sin),
        COS("cos", TrigUtils::cos),
        TAN("tan", TrigUtils::tan),
        CSC("csc", TrigUtils::csc),
        SEC("sec", TrigUtils::sec),
        COT("cot", TrigUtils::cot),

        /* Circular Inverse Trig */
        ASIN("arcsin", TrigUtils::asin),
        ACOS("arccos", TrigUtils::acos),
        ATAN("arctan", TrigUtils::atan),
        ACSC("arccsc", TrigUtils::acsc),
        ASEC("arcsec", TrigUtils::asec),
        ACOT("arccot", TrigUtils::acot),

        /* Hyperbolic Trig */
        SINH("sinh", TrigUtils::sinh),
        COSH("cosh", TrigUtils::cosh),
        TANH("tanh", TrigUtils::tanh),
        CSCH("csch", TrigUtils::csch),
        SECH("sech", TrigUtils::sech),
        COTH("coth", TrigUtils::coth),

        /* Hyperbolic Inverse Trig */
        ASINH("arsinh", TrigUtils::asinh),
        ACOSH("arcosh", TrigUtils::acosh),
        ATANH("artanh", TrigUtils::atanh),
        ACSCH("arcsch", TrigUtils::acsch),
        ASECH("arsech", TrigUtils::asech),
        ACOTH("arcoth", TrigUtils::acoth);

        private final String trigString;
        private final Function<Complex, Complex> function;

        private TrigType(String trigString, Function<Complex, Complex> function) {
            this.trigString = trigString;
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
        public final String getTrigString() {
            return trigString;
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

    // #region Circle Ops-All
    public static final Complex sin(Complex value) {
        /* DNE */
        if (value.isDNE()) {
            return NumberUtils.DNE();
        }

        return TrigUtils.Complex.sin(value.getA(), value.getB());
    }

    public static final Complex cos(Complex value) {
        /* DNE */
        if (value.isDNE()) {
            return NumberUtils.DNE();
        }

        return TrigUtils.Complex.cos(value.getA(), value.getB());
    }

    public static final Complex tan(Complex value) {
        /* DNE */
        if (value.isDNE()) {
            return NumberUtils.DNE();
        }

        return TrigUtils.Complex.tan(value.getA(), value.getB());
    }

    public static final Complex csc(Complex value) {
        /* DNE */
        if (value.isDNE()) {
            return NumberUtils.DNE();
        }

        return TrigUtils.Complex.csc(value.getA(), value.getB());
    }

    public static final Complex sec(Complex value) {
        /* DNE */
        if (value.isDNE()) {
            return NumberUtils.DNE();
        }

        return TrigUtils.Complex.sec(value.getA(), value.getB());
    }

    public static final Complex cot(Complex value) {
        /* DNE */
        if (value.isDNE()) {
            return NumberUtils.DNE();
        }

        return TrigUtils.Complex.cot(value.getA(), value.getB());
    }
    // #endregion

    // #region Circle Inv Ops-All
    public static final Complex asin(Complex value) {
        /* DNE */
        if (value.isDNE()) {
            return NumberUtils.DNE();
        }

        return TrigUtils.Complex.asin(value.getA(), value.getB());
    }

    public static final Complex acos(Complex value) {
        /* DNE */
        if (value.isDNE()) {
            return NumberUtils.DNE();
        }

        return TrigUtils.Complex.acos(value.getA(), value.getB());
    }

    public static final Complex atan(Complex value) {
        /* DNE */
        if (value.isDNE()) {
            return NumberUtils.DNE();
        }

        return TrigUtils.Complex.atan(value.getA(), value.getB());
    }

    public static final Complex acsc(Complex value) {
        /* DNE */
        if (value.isDNE()) {
            return NumberUtils.DNE();
        }

        return TrigUtils.Complex.acsc(value.getA(), value.getB());
    }

    public static final Complex asec(Complex value) {
        /* DNE */
        if (value.isDNE()) {
            return NumberUtils.DNE();
        }

        return TrigUtils.Complex.asec(value.getA(), value.getB());
    }

    public static final Complex acot(Complex value) {
        /* DNE */
        if (value.isDNE()) {
            return NumberUtils.DNE();
        }

        return TrigUtils.Complex.acot(value.getA(), value.getB());
    }
    // #endregion

    // #region Hyper Ops-All
    public static final Complex sinh(Complex value) {
        /* DNE */
        if (value.isDNE()) {
            return NumberUtils.DNE();
        }

        return TrigUtils.Complex.sinh(value.getA(), value.getB());
    }

    public static final Complex cosh(Complex value) {
        /* DNE */
        if (value.isDNE()) {
            return NumberUtils.DNE();
        }

        return TrigUtils.Complex.cosh(value.getA(), value.getB());
    }

    public static final Complex tanh(Complex value) {
        /* DNE */
        if (value.isDNE()) {
            return NumberUtils.DNE();
        }

        return TrigUtils.Complex.tanh(value.getA(), value.getB());
    }

    public static final Complex csch(Complex value) {
        /* DNE */
        if (value.isDNE()) {
            return NumberUtils.DNE();
        }

        return TrigUtils.Complex.csch(value.getA(), value.getB());
    }

    public static final Complex sech(Complex value) {
        /* DNE */
        if (value.isDNE()) {
            return NumberUtils.DNE();
        }

        return TrigUtils.Complex.sech(value.getA(), value.getB());
    }

    public static final Complex coth(Complex value) {
        /* DNE */
        if (value.isDNE()) {
            return NumberUtils.DNE();
        }

        return TrigUtils.Complex.coth(value.getA(), value.getB());
    }
    // #endregion

    // #region Hyper Inv Ops-All
    public static final Complex asinh(Complex value) {
        /* DNE */
        if (value.isDNE()) {
            return NumberUtils.DNE();
        }

        return TrigUtils.Complex.asinh(value.getA(), value.getB());
    }

    public static final Complex acosh(Complex value) {
        /* DNE */
        if (value.isDNE()) {
            return NumberUtils.DNE();
        }

        return TrigUtils.Complex.acosh(value.getA(), value.getB());
    }

    public static final Complex atanh(Complex value) {
        /* DNE */
        if (value.isDNE()) {
            return NumberUtils.DNE();
        }

        return TrigUtils.Complex.atanh(value.getA(), value.getB());
    }

    public static final Complex acsch(Complex value) {
        /* DNE */
        if (value.isDNE()) {
            return NumberUtils.DNE();
        }

        return TrigUtils.Complex.acsch(value.getA(), value.getB());
    }

    public static final Complex asech(Complex value) {
        /* DNE */
        if (value.isDNE()) {
            return NumberUtils.DNE();
        }

        return TrigUtils.Complex.asech(value.getA(), value.getB());
    }

    public static final Complex acoth(Complex value) {
        /* DNE */
        if (value.isDNE()) {
            return NumberUtils.DNE();
        }

        return TrigUtils.Complex.acoth(value.getA(), value.getB());
    }
    // #endregion

    /**
     * 
     */
    private static final class Complex {
        // #region Circle-Cmplx
        /**
         * 
         * @param value
         * @return
         */
        public static final Complex sin(BNum a, BNum b) {
            if (!a.isZero() && b.isZero()) {
                // real arguement
                return new Complex(a.sin(), 0);
            } else if (a.isZero() && !b.isZero()) {
                // imaginary arguement
                return new Complex(0, b.sinh());
            } else {
                // complex arguement
                if (a.isNegative()) {
                    return TrigUtils.Complex.sin(a.negate(), b.negate()).negate();
                }

                BNum real, imaginary;
                real = a.clone().sin().multiply(b.cosh());
                if (b.isNegative()) {
                    // angle difference
                    imaginary = ValueUtils.multiply(a.cos(), b.sinh()).negate();
                } else {
                    // angle sum
                    imaginary = ValueUtils.multiply(a.cos(), b.sinh());
                }

                return new Complex(real, imaginary);
            }
        }

        public static final Complex cos(BNum a, BNum b) {
            if (!a.isZero() && b.isZero()) {
                // real arguement
                return new Complex(a.cos(), 0);
            } else if (a.isZero() && !b.isZero()) {
                // imaginary argumenet
                return new Complex(0, b.cosh());
            } else {
                // complex arguement
                if (a.isNegative()) {
                    return TrigUtils.Complex.cos(a.negate(), b.negate());
                }

                BNum real, imaginary;
                real = ValueUtils.multiply(a.cos(), b.cosh());
                if (b.isNegative()) {
                    imaginary = ValueUtils.multiply(a.sin(), b.sinh());
                } else {
                    imaginary = ValueUtils.multiply(a.sin(), b.sinh()).negate();
                }

                return new Complex(real, imaginary.negate());
            }
        }

        public static final Complex tan(BNum a, BNum b) {
            if (!a.isZero() && b.isZero()) {
                // real arguement
                return new Complex(a.tan(), 0);
            } else if (a.isZero() && !b.isZero()) {
                // imaginary arguement
                return new Complex(0, b.tanh());
            } else {
                // complex arguement
                if (a.isNegative()) {
                    return TrigUtils.Complex.tan(a.negate(), b.negate()).negate();
                }

                Complex num, denom;
                if (b.isNegative()) {
                    num = new Complex(a.tan(), b.tanh());
                    denom = new Complex(1, ValueUtils.multiply(a.tan(), b.tanh()).negate());
                } else {
                    num = new Complex(a.tan(), b.tanh().negate());
                    denom = new Complex(1, ValueUtils.multiply(a.tan(), b.tanh()));
                }

                return NumberUtils.divide(num, denom);
            }
        }

        public static final Complex csc(BNum a, BNum b) {
            if (!a.isZero() && b.isZero()) {
                // real arguement
                return new Complex(a.csc(), 0);
            } else if (a.isZero() && !b.isZero()) {
                // imaginary arguement
                return new Complex(0, b.csch().negate());
            } else {
                // complex arguement
                Complex sinVal = TrigUtils.Complex.sin(a, b);
                if (sinVal.isZero()) {
                    return NumberUtils.DNE();
                }
                return NumberUtils.divide(new Complex(1, 0), sinVal);
            }
        }

        public static final Complex sec(BNum a, BNum b) {
            if (!a.isZero() && b.isZero()) {
                // real arguement
                return new Complex(a.sec(), 0);
            } else if (a.isZero() && !b.isZero()) {
                // imaginary arguement
                return new Complex(0, b.sech());
            } else {
                // complex arguement
                Complex cosVal = TrigUtils.Complex.cos(a, b);
                if (cosVal.isZero()) {
                    return NumberUtils.DNE();
                }
                return NumberUtils.divide(new Complex(1, 0), cosVal);
            }
        }

        public static final Complex cot(BNum a, BNum b) {
            if (!a.isZero() && b.isZero()) {
                // real arguement
                return new Complex(a.cot(), 0);
            } else if (a.isZero() && !b.isZero()) {
                // imaginary arguement
                return new Complex(0, b.coth().negate());
            } else {
                // complex arguement
                Complex tanVal = TrigUtils.Complex.tan(a, b);
                if (tanVal.isZero()) {
                    return NumberUtils.DNE();
                }
                return NumberUtils.divide(new Complex(1, 0), tanVal);
            }
        }

        // #region Circle Inv-Cmplx
        public static final Complex asin(BNum a, BNum b) {
            if (!a.isZero() && b.isZero()) {
                // real arguement
                return new Complex(a.asin(), 0);
            } else if (a.isZero() && !b.isZero()) {
                // imaginary arguement
            } else {
                // complex arguement
            }
            // TODO: this method is not a thing lol
            throw new UnsupportedOperationException();
        }

        public static final Complex acos(BNum a, BNum b) {
            if (!a.isZero() && b.isZero()) {
                // real arguement
                return new Complex(a.acos(), 0);
            } else if (a.isZero() && !b.isZero()) {
                // imaginary arguement
            } else {
                // complex arguement
            }
            // TODO: this method is not a thing lol
            throw new UnsupportedOperationException();
        }

        public static final Complex atan(BNum a, BNum b) {
            if (!a.isZero() && b.isZero()) {
                // real arguement
                return new Complex(a.atan(), 0);
            } else if (a.isZero() && !b.isZero()) {
                // imaginary arguement
            } else {
                // complex arguement
            }
            // TODO: this method is not a thing lol
            throw new UnsupportedOperationException();
        }

        public static final Complex acsc(BNum a, BNum b) {
            if (!a.isZero() && b.isZero()) {
                // real arguement
                return new Complex(a.acsc(), 0);
            } else if (a.isZero() && !b.isZero()) {
                // imaginary arguement
            } else {
                // complex arguement
            }
            // TODO: this method is not a thing lol
            throw new UnsupportedOperationException();
        }

        public static final Complex asec(BNum a, BNum b) {
            if (!a.isZero() && b.isZero()) {
                // real arguement
                return new Complex(a.asec(), 0);
            } else if (a.isZero() && !b.isZero()) {
                // imaginary arguement
            } else {
                // complex arguement
            }
            // TODO: this method is not a thing lol
            throw new UnsupportedOperationException();
        }

        public static final Complex acot(BNum a, BNum b) {
            if (!a.isZero() && b.isZero()) {
                // real arguement
                return new Complex(a.acot(), 0);
            } else if (a.isZero() && !b.isZero()) {
                // imaginary arguement
            } else {
                // complex arguement
            }
            // TODO: this method is not a thing lol
            throw new UnsupportedOperationException();
        }
        // #endregion

        // #region Hyper-Cmplx
        public static final Complex sinh(BNum a, BNum b) {
            if (!a.isZero() && b.isZero()) {
                // real arguement
                return new Complex(a.sinh(), 0);
            } else if (a.isZero() && !b.isZero()) {
                // imaginary arguement
            } else {
                // complex arguement
            }
            // TODO: this method is not a thing lol
            throw new UnsupportedOperationException();
        }

        public static final Complex cosh(BNum a, BNum b) {
            if (!a.isZero() && b.isZero()) {
                // real arguement
                return new Complex(a.cosh(), 0);
            } else if (a.isZero() && !b.isZero()) {
                // imaginary arguement
            } else {
                // complex arguement
            }
            // TODO: this method is not a thing lol
            throw new UnsupportedOperationException();
        }

        public static final Complex tanh(BNum a, BNum b) {
            if (!a.isZero() && b.isZero()) {
                // real arguement
                return new Complex(a.tanh(), 0);
            } else if (a.isZero() && !b.isZero()) {
                // imaginary arguement
            } else {
                // complex arguement
            }
            // TODO: this method is not a thing lol
            throw new UnsupportedOperationException();
        }

        public static final Complex csch(BNum a, BNum b) {
            if (!a.isZero() && b.isZero()) {
                // real arguement
                return new Complex(a.csch(), 0);
            } else if (a.isZero() && !b.isZero()) {
                // imaginary arguement
            } else {
                // complex arguement
            }
            // TODO: this method is not a thing lol
            throw new UnsupportedOperationException();
        }

        public static final Complex sech(BNum a, BNum b) {
            if (!a.isZero() && b.isZero()) {
                // real arguement
                return new Complex(a.sech(), 0);
            } else if (a.isZero() && !b.isZero()) {
                // imaginary arguement
            } else {
                // complex arguement
            }
            // TODO: this method is not a thing lol
            throw new UnsupportedOperationException();
        }

        public static final Complex coth(BNum a, BNum b) {
            if (!a.isZero() && b.isZero()) {
                // real arguement
                return new Complex(a.coth(), 0);
            } else if (a.isZero() && !b.isZero()) {
                // imaginary arguement
            } else {
                // complex arguement
            }
            // TODO: this method is not a thing lol
            throw new UnsupportedOperationException();
        }
        // #endregion

        // #region Hyper Inv-Cmplx
        public static final Complex asinh(BNum a, BNum b) {
            if (!a.isZero() && b.isZero()) {
                // real arguement
                return new Complex(a.asinh(), 0);
            } else if (a.isZero() && !b.isZero()) {
                // imaginary arguement
            } else {
                // complex arguement
            }
            // TODO: this method is not a thing lol
            throw new UnsupportedOperationException();
        }

        public static final Complex acosh(BNum a, BNum b) {
            if (!a.isZero() && b.isZero()) {
                // real arguement
                return new Complex(a.acosh(), 0);
            } else if (a.isZero() && !b.isZero()) {
                // imaginary arguement
            } else {
                // complex arguement
            }
            // TODO: this method is not a thing lol
            throw new UnsupportedOperationException();
        }

        public static final Complex atanh(BNum a, BNum b) {
            if (!a.isZero() && b.isZero()) {
                // real arguement
                return new Complex(a.atanh(), 0);
            } else if (a.isZero() && !b.isZero()) {
                // imaginary arguement
            } else {
                // complex arguement
            }
            // TODO: this method is not a thing lol
            throw new UnsupportedOperationException();
        }

        public static final Complex acsch(BNum a, BNum b) {
            if (!a.isZero() && b.isZero()) {
                // real arguement
                return new Complex(a.acsch(), 0);
            } else if (a.isZero() && !b.isZero()) {
                // imaginary arguement
            } else {
                // complex arguement
            }
            // TODO: this method is not a thing lol
            throw new UnsupportedOperationException();
        }

        public static final Complex asech(BNum a, BNum b) {
            if (!a.isZero() && b.isZero()) {
                // real arguement
                return new Complex(a.asech(), 0);
            } else if (a.isZero() && !b.isZero()) {
                // imaginary arguement
            } else {
                // complex arguement
            }
            // TODO: this method is not a thing lol
            throw new UnsupportedOperationException();
        }

        public static final Complex acoth(BNum a, BNum b) {
            if (!a.isZero() && b.isZero()) {
                // real arguement
                return new Complex(a.acoth(), 0);
            } else if (a.isZero() && !b.isZero()) {
                // imaginary arguement
            } else {
                // complex arguement
            }
            // TODO: this method is not a thing lol
            throw new UnsupportedOperationException();
        }
        // #endregion
    }

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
