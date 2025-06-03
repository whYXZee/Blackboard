package whyxzee.blackboard.settheory;

import whyxzee.blackboard.numbers.Infinity;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.numbers.NumberAbstract.NumType;

public class Bound implements Comparable<Bound> {
    /* General Use */
    public static final Bound negInfBound = new Bound(new Infinity(true), true);
    public static final Bound posInfBound = new Bound(new Infinity(false), true);

    /* Variables */
    private NumberAbstract value;
    private boolean isOpen;
    private boolean isLower;

    public Bound(NumberAbstract value, boolean isOpen) {
        this.value = value;
        this.isOpen = isOpen;
        this.isLower = false;
    }

    public Bound(NumberAbstract value, boolean isOpen, boolean isLower) {
        this.value = value;
        this.isOpen = isOpen;
        this.isLower = isLower;
    }

    @Override
    public final String toString() {
        if (isLower) {
            return (isOpen ? "(" : "[") + value.toString();
        }
        return value.toString() + (isOpen ? ")" : "]");
    }

    public final String printConsole() {
        if (isLower) {
            return (isOpen ? "(" : "[") + value.printConsole();
        }
        return value.printConsole() + (isOpen ? ")" : "]");
    }

    //
    // Get & Set Methods
    //
    public final NumberAbstract getValue() {
        return value;
    }

    public final void setValue(NumberAbstract value) {
        this.value = value;
    }

    public final boolean isOpen() {
        return isOpen;
    }

    public final void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public final boolean getLower() {
        return isLower;
    }

    public final void setLower(boolean isLower) {
        this.isLower = isLower;
    }

    //
    // Boolean Methods
    //
    public final boolean equals(Bound other) {
        return value.equals(other.getValue()) && (isOpen == other.isOpen());
    }

    public final boolean isInfinite() {
        return value.isNumType(NumType.INFINITY);
    }

    @Override
    public final int compareTo(Bound other) {
        if (equals(other)) {
            return 0;
        } else if (value.lessThan(other.getValue())) {
            return -1;
        }

        return 1;
    }
}
