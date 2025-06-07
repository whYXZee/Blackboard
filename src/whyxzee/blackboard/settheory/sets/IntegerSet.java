package whyxzee.blackboard.settheory.sets;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.settheory.AmbiguousList;
import whyxzee.blackboard.settheory.DefinedList;
import whyxzee.blackboard.settheory.IntervalSet;
import whyxzee.blackboard.settheory.SetAbstract;

/**
 * A package that is the set of all integers. Denoted as {..., -3, -2, -1, 0, 1,
 * 2, 3, ...}
 * 
 * <p>
 * The functionality of this class has been checked on {@code 6/5/2025} and
 * nothing has changed since.
 */
public class IntegerSet extends AmbiguousList {
    public IntegerSet() {
        super(Constants.Unicode.INTEGER_SET, DomainType.INTEGER);
    }

    @Override
    public IntervalSet toInterval() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toInterval'");
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

    //
    // Boolean Methods
    //
    @Override
    public boolean inSet(NumberAbstract number) {
        return number.isInteger();
    }
}
