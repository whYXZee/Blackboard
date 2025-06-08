package whyxzee.blackboard.numbers;

/**
 * An abstract class for all uncountable numbers, such as infinity. This class
 * is utilized for specific sizes of infinity as well as the generic one.
 * 
 * <p>
 * The functionality of this class has not been checked.
 */
public abstract class Uncountable extends NumberAbstract {
    /* Variables */
    /**
     * The size of the infinity. For example, aleph_0 would have a size of 0, while
     * aleph_1 would have a size of 1.
     */
    private int size;
    private InfType infType;
    private boolean isNegative;

    public enum InfType {
        ALEPH(0),
        INFINITY(1);

        /* Methods */
        public final int order;

        private InfType(int order) {
            this.order = order;
        }

        public final int getOrder() {
            return order;
        }
    }

    public Uncountable(int size, InfType infType) {
        super(Double.POSITIVE_INFINITY, NumType.UNCOUNTABLE);
        this.size = size;
        this.infType = infType;
        this.isNegative = false;
    }

    public Uncountable(int size, InfType infType, boolean isNegative) {
        super(isNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY, NumType.UNCOUNTABLE);
        this.size = size;
        this.infType = infType;
        this.isNegative = isNegative;
    }

    //
    // Get & Set Methods
    //
    public final int getSize() {
        return size;
    }

    public final InfType getInfType() {
        return infType;
    }

    public final boolean isNegative() {
        return isNegative;
    }

    public final void setNegative(boolean isNegative) {
        this.isNegative = isNegative;
        setValue(isNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
    }

    //
    // Boolean Methods
    //
    @Override
    public final boolean isInteger() {
        return false;
    }

    @Override
    public final boolean isRational() {
        return false;
    }

    @Override
    public final boolean isReal() {
        return false;
    }

    @Override
    public final boolean isImaginary() {
        return false;
    }

    @Override
    public final boolean isComplex() {
        return false;
    }

    @Override
    public final boolean isInfinite() {
        return true;
    }

    @Override
    public final boolean equals(NumberAbstract other) {
        if (!other.isType(NumType.UNCOUNTABLE)) {
            return false;
        }

        Uncountable uncountable = (Uncountable) other;
        if (!uncountable.isInfType(infType)) {
            return false;
        }

        // same order, different sizes
        return size == uncountable.getSize();
    }

    @Override
    public final boolean lessThan(NumberAbstract other) {
        if (!other.isType(NumType.UNCOUNTABLE)) {
            return getValue() < other.getValue();
        }

        Uncountable uncountable = (Uncountable) other;
        if (!uncountable.isInfType(infType)) {
            return false;
        }

        // same order, different sizes
        return size < uncountable.getSize();
    }

    @Override
    public final boolean lessThanEqual(NumberAbstract other) {
        if (!other.isType(NumType.UNCOUNTABLE)) {
            return getValue() <= other.getValue();
        }

        Uncountable uncountable = (Uncountable) other;
        if (!uncountable.isInfType(infType)) {
            return false;
        }

        // same order, different sizes
        return size <= uncountable.getSize();
    }

    public final boolean isInfType(InfType infType) {
        return this.infType == infType;
    }

}
