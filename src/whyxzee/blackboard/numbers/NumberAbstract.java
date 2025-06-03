package whyxzee.blackboard.numbers;

public abstract class NumberAbstract {
    /* Variables */
    private double value;
    private NumType numType;

    public enum NumType {
        REAL,
        IMAGINARY,
        INFINITY
    }

    public NumberAbstract(double value, NumType numType) {
        this.value = value;
        this.numType = numType;
    }

    public abstract String printConsole();

    public static final RealNumber valueOf(double value) {
        return new RealNumber(value);
    }

    //
    // Get & Set Methods
    //
    public final double getValue() {
        return value;
    }

    public final void setValue(double value) {
        this.value = value;
    }

    public final NumType getNumType() {
        return numType;
    }

    public final void setNumType(NumType numType) {
        this.numType = numType;
    }

    //
    // Boolean Methods
    //
    public abstract boolean equals(NumberAbstract other);

    public final boolean isNumType(NumType numType) {
        return this.numType == numType;
    }

    public abstract boolean isImaginary();

    public abstract boolean isComplex();

    public abstract boolean isRational();

    public abstract boolean isInteger();

    /**
     * is <b>this</b> < <b>other</b>?
     * 
     * @param other
     * @return
     */
    public final boolean lessThan(NumberAbstract other) {
        if (other.isComplex() || isComplex()) {
            // TODO: implement later
            return false;
        }

        return value < other.getValue();
    }

    /**
     * is <b>this</b> <= <b>other</b>?
     * 
     * @param other
     * @return
     */
    public final boolean lessThanEqual(NumberAbstract other) {
        if (other.isComplex() || isComplex()) {
            // TODO: implement later
            return false;
        }

        return value <= other.getValue();
    }

    /**
     * is <b>this</b> > <b>other</b>?
     * 
     * @param other
     * @return
     */
    public final boolean greaterThan(NumberAbstract other) {
        if (other.isComplex() || isComplex()) {
            // TODO: implement later
            return false;
        }

        return value > other.getValue();
    }

    /**
     * is <b>this</b> >= <b>other</b>?
     * 
     * @param other
     * @return
     */
    public final boolean greaterThanEqual(NumberAbstract other) {
        if (other.isComplex() || isComplex()) {
            // TODO: implement later
            return false;
        }

        return value >= other.getValue();
    }
}
