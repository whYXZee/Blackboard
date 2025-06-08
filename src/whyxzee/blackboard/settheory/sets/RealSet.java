package whyxzee.blackboard.settheory.sets;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.numbers.uncountable.Aleph;
import whyxzee.blackboard.settheory.AmbiguousList;
import whyxzee.blackboard.settheory.DefinedList;
import whyxzee.blackboard.settheory.IntervalSet;
import whyxzee.blackboard.settheory.SetAbstract;

/**
 * A package for the set of all real numbers.
 * 
 * <p>
 * The functionality of this class has not been checked.
 */

public class RealSet extends AmbiguousList {
    public RealSet() {
        super(Constants.Unicode.REAL_SET, DomainType.REAL);
    }

    @Override
    public IntervalSet toInterval() {
        // All real numbers extends from negative infinity to positive infinity
        return new IntervalSet("");
    }

    @Override
    public DefinedList toDefinedList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toDefinedList'");
    }

    //
    // Arithmetic Methods
    //
    @Override
    public SetAbstract complement(SetAbstract universe) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'complement'");
    }

    @Override
    public final NumberAbstract cardinality() {
        return new Aleph(1);
    }

    //
    // Boolean Methods
    //
    @Override
    public boolean inSet(NumberAbstract number) {
        return number.isReal();
    }
}
