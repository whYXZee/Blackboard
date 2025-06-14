package whyxzee.blackboard.math.pure.numbers;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.utils.UnicodeUtils;

/**
 * The functionality of this class has not been checked.
 */
public class BUncountable extends BNumber {
    /* Variables */
    /**
     * The size of the uncountable in relation to similar types.
     */
    private int realSize;
    private int imaginarySize;
    private UncountableType realType;
    private UncountableType imaginaryType;
    private String realChar;
    private String imaginaryChar;

    public enum UncountableType {
        NONE(0),
        ALEPH(1),
        GEN_INFINITY(10);

        private final int order;

        private UncountableType(int order) {
            this.order = order;
        }

        public final int getOrder() {
            return order;
        }
    }

    /**
     * Constructor for a real uncountable number.
     * 
     * @param rVal  the value of the real uncountable.
     * @param rSize the size of the real uncountable.
     * @param rType the type of the real uncountable.
     * @param rChar the character that represents the uncountable.
     */
    public BUncountable(double rVal, int rSize, UncountableType rType, String rChar) {
        super(rVal, 0);
        this.realSize = rSize;
        this.realType = rType;
        this.realChar = rChar;
        this.imaginarySize = -1;
        this.imaginaryType = UncountableType.NONE;
        this.imaginaryChar = "";
    }

    /**
     * Constructor for a number which has a real and/or imaginary uncountable. It
     * can be mixed with numbers.
     * 
     * @param rVal  the value of the real uncountable.
     * @param rSize the size of the real uncountable.
     * @param rType the type of the real uncountable.
     * @param rChar tye character that represents the real uncountable.
     * @param iVal  the value of the imaginary uncountable.
     * @param iSize the size of the imaginary uncountable.
     * @param iType the type of the imaginary uncountable.
     * @param iChar the character that represents the imaginary uncountable.
     */
    private BUncountable(double rVal, int rSize, UncountableType rType, String rChar, double iVal, int iSize,
            UncountableType iType, String iChar) {
        super(rVal, iVal);
        this.realSize = rSize;
        this.realType = rType;
        this.realChar = rChar;
        this.imaginarySize = iSize;
        this.imaginaryType = iType;
        this.imaginaryChar = iChar;
    }

    public static final BNumber createCustomUncountable(BNumber real, BNumber imaginary) {
        if (!real.isUncountable() && !imaginary.isUncountable()) {
            BNumber output = new BNumber(0, 0);
            output.add(real, imaginary);
            return output;
        }

        if (real.isUncountable() && imaginary.isUncountable()) {
            BUncountable realUnc = (BUncountable) real;
            BUncountable imagUnc = (BUncountable) imaginary;
            return new BUncountable(realUnc.getA(), realUnc.getRealSize(), realUnc.getRealType(), realUnc.getRealChar(),
                    imagUnc.getA(), imagUnc.getRealSize(), imagUnc.getRealType(), imagUnc.getRealChar());
        }

        if (real.isUncountable()) {
            BUncountable realUnc = (BUncountable) real;
            return new BUncountable(realUnc.getA(), realUnc.getRealSize(), realUnc.getRealType(), realUnc.getRealChar(),
                    imaginary.getB(), -1, UncountableType.NONE, "");
        }
        BUncountable imagUnc = (BUncountable) imaginary;
        return new BUncountable(real.getA(), -1, UncountableType.NONE, "",
                imagUnc.getA(), imagUnc.getRealSize(), imagUnc.getRealType(), imagUnc.getRealChar());

    }

    @Override
    public final String toString() {
        if (!isAZero() && isBZero()) {
            return (getA() < 0 ? "-" : "") + realChar + UnicodeUtils.intToSubscript(realSize);
        } else if (isAZero() && isBZero()) {
            return (getB() < 0 ? "-" : "") + imaginaryChar + UnicodeUtils.intToSubscript(imaginarySize)
                    + Constants.Unicode.IMAGINARY_NUMBER;
        }

        /* */
        String output = "";
        if (isRealType(UncountableType.NONE)) {
            output += NumberUtils.valueToString(getA());
        } else {
            output += (getA() < 0 ? "-" : "") + realChar + UnicodeUtils.intToSubscript(realSize);
        }

        boolean isBNegative = getB() < 0;
        output += (isBNegative) ? " - " : " + ";
        if (isImaginaryType(UncountableType.NONE)) {
            output += NumberUtils.valueToString(isBNegative ? -getB() : getB());
        } else {
            output += imaginaryChar + UnicodeUtils.intToSubscript(imaginarySize);
        }
        output += Constants.Unicode.IMAGINARY_NUMBER;
        return output;
    }

    @Override
    public final BUncountable clone() {
        return new BUncountable(getA(), realSize, realType, realChar, getB(), imaginarySize,
                imaginaryType, imaginaryChar);
    }

    //
    // Get & Set Methods
    //
    // #region
    public final int getRealSize() {
        return realSize;
    }

    public final UncountableType getRealType() {
        return realType;
    }

    public final String getRealChar() {
        return realChar;
    }

    public final int getImaginarySize() {
        return imaginarySize;
    }

    public final UncountableType getImaginaryType() {
        return imaginaryType;
    }

    public final String getImaginaryChar() {
        return imaginaryChar;
    }
    // #endregion

    //
    // Boolean Methods
    //
    public final boolean isRealType(UncountableType type) {
        return this.realType == type;
    }

    public final boolean isImaginaryType(UncountableType type) {
        return this.imaginaryType == type;
    }

    @Override
    public final boolean isUncountable() {
        return true;
    }

    //
    // Equality and Inequality
    //

}