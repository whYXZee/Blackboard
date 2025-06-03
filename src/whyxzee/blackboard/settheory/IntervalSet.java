package whyxzee.blackboard.settheory;

import whyxzee.blackboard.numbers.NumberAbstract;

/**
 * <p>
 * The functionality of this package has been checkced on {@code 6/3/2025} and
 * nothing has changed since.
 */
public class IntervalSet extends SetAbstract {
    /* Variables */
    private NumberAbstract lowBound;
    private boolean isLowOpen;
    private NumberAbstract upBound;
    private boolean isUpOpen;

    public IntervalSet(String setName) {
        super(setName, SetType.INTERVAL);
    }

    public IntervalSet(String setName, NumberAbstract lowBound, boolean isLowOpen, boolean isUpOpen,
            NumberAbstract upBound) {
        super(setName, SetType.INTERVAL);
        this.lowBound = lowBound;
        this.isLowOpen = isLowOpen;
        this.isUpOpen = isUpOpen;
        this.upBound = upBound;
    }

    @Override
    public String printConsole() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printConsole'");
    }

    @Override
    public IntervalSet toInterval() {
        return this;
    }

    @Override
    public DefinedList toDefinedList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toDefinedList'");
    }

    @Override
    public SetBuilder toBuilder() {
        throw new UnsupportedOperationException("Unimplemented method 'toDefinedList'");
    }

    //
    // Get & Set Methods
    //
    public final NumberAbstract getLowBound() {
        return lowBound;
    }

    public final boolean isLowOpen() {
        return isLowOpen;
    }

    public final NumberAbstract getUpBound() {
        return upBound;
    }

    public final boolean isUpOpen() {
        return isUpOpen;
    }

    //
    // Arithmetic Methods
    //
    @Override
    public SetAbstract union(SetAbstract other) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'union'");
    }

    @Override
    public SetAbstract disjunction(SetAbstract other) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disjoint'");
    }

    @Override
    public SetAbstract complement(SetAbstract universe) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'complement'");
    }

    //
    // Boolean Methods
    //
    @Override
    public boolean inSet(NumberAbstract number) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'inSet'");
    }

    @Override
    public boolean equals(SetAbstract other) {
        throw new UnsupportedOperationException();
    }

}
