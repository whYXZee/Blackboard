package whyxzee.blackboard.numbers;

public abstract class NumberAbstract {
    /* Variables */
    private double value;
    private NumType type;

    public enum NumType {
        UNCOUNTABLE,
        REAL
    }

    public NumberAbstract(double value) {
        this.value = value;
        this.type = NumType.REAL;
    }

    public NumberAbstract(double value, NumType type) {
        this.value = value;
        this.type = type;
    }

    public abstract String printConsole();

    //
    // Get & Set Methods
    //
    public final double getValue() {
        return value;
    }

    public final void setValue(double value) {
        this.value = value;
    }

    //
    // Boolean Methods
    //
    public final boolean isType(NumType type) {
        return this.type == type;
    }

    public abstract boolean isInteger();

    public abstract boolean isRational();

    public abstract boolean isReal();

    public abstract boolean isComplex();

    public boolean equals(NumberAbstract other) {
        return value == other.getValue();
    }

    public boolean lessThan(NumberAbstract other) {
        return value < other.getValue();
    }

    public boolean lessThanEqual(NumberAbstract other) {
        return value <= other.getValue();
    }
}
