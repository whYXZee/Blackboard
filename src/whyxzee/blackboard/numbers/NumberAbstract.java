package whyxzee.blackboard.numbers;

public abstract class NumberAbstract {
    /* Variables */
    private double value;

    public NumberAbstract(double value) {
        this.value = value;
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

    public abstract boolean isInteger();
}
