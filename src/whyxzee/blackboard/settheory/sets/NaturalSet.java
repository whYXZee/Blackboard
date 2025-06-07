package whyxzee.blackboard.settheory.sets;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.settheory.AmbiguousList;
import whyxzee.blackboard.settheory.DefinedList;
import whyxzee.blackboard.settheory.IntervalSet;
import whyxzee.blackboard.settheory.SetAbstract;

/**
 * A package that is the set of all natural numbers. Denoted as {0, 1, 2, 3,
 * ...}
 * 
 * <p>
 * the functionality of this class has not been checked.
 */
public class NaturalSet extends AmbiguousList {
    public NaturalSet() {
        super(Constants.Unicode.NATURAL_SET, DomainType.NATURAL);
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
        return number.isInteger() && number.getValue() > 0;
    }
}
