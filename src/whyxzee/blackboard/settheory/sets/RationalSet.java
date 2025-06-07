package whyxzee.blackboard.settheory.sets;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.settheory.AmbiguousList;
import whyxzee.blackboard.settheory.DefinedList;
import whyxzee.blackboard.settheory.IntervalSet;
import whyxzee.blackboard.settheory.SetAbstract;

/**
 * A package that is the set of all rational numbers (meaning a number that is
 * created from the ratio of two integers).
 * 
 * <p>
 * The functionality of this class has been checke don {@code 6/5/2025} and
 * nothing has changed since..
 */
public class RationalSet extends AmbiguousList {

    public RationalSet() {
        super(Constants.Unicode.RATIONAL_SET, DomainType.RATIONAL);
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
        return number.isRational();
    }
}
