package whyxzee.blackboard.numbers;

/**
 * An abstract class for all uncountable numbers, such as infinity. This class
 * is utilized for specific sizes of infinity as well as the generic one.
 */
public abstract class Uncountable extends NumberAbstract {
    /* Variables */
    private int order;

    public Uncountable(int order) {
        super(Double.POSITIVE_INFINITY, NumType.UNCOUNTABLE);
        this.order = order;
    }

    public Uncountable(int order, double value) {
        super(value, NumType.UNCOUNTABLE);
        this.order = order;
    }

    //
    // Get & Set Methods
    //
    public final int getOrder() {
        return order;
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
    public final boolean isComplex() {
        return false;
    }

    @Override
    public final boolean lessThan(NumberAbstract other) {
        if (!other.isType(NumType.UNCOUNTABLE)) {
            return false;
        }

        return order < ((Uncountable) other).getOrder();
    }

    @Override
    public final boolean lessThanEqual(NumberAbstract other) {
        if (!other.isType(NumType.UNCOUNTABLE)) {
            return false;
        }

        return order <= ((Uncountable) other).getOrder();
    }
}
