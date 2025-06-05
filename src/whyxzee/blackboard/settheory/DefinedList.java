package whyxzee.blackboard.settheory;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.numbers.RealNumber;
import whyxzee.blackboard.settheory.predicates.ElementOf;
import whyxzee.blackboard.settheory.predicates.PredicateAbstract;
import whyxzee.blackboard.settheory.predicates.RangePredicate;

/**
 * A package that is a set of numbers.
 * 
 * <p>
 * The functionality of this class has not been checked.
 */
public class DefinedList extends SetAbstract {
    private ArrayList<NumberAbstract> numbers;

    public DefinedList(String setName, ArrayList<NumberAbstract> numbers) {
        super(setName, SetType.DEFINED_LIST);
        this.numbers = numbers;
    }

    @Override
    public final String toString() {
        String output = getSetName() + " = ";
        if (numbers.size() == 0) {
            return output + Constants.Unicode.NULL_SET;
        }

        output += "{" + numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            output += ", " + numbers.get(i);
        }
        return output + "}";
    }

    @Override
    public final String printConsole() {
        return toString();
    }

    @Override
    public IntervalSet toInterval() {
        throw new UnsupportedOperationException();
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
    public final ArrayList<NumberAbstract> getNumbers() {
        return numbers;
    }

    //
    // Arithmetic Methods
    //
    @Override
    public final NumberAbstract cardinality() {
        return new RealNumber(numbers.size());
    }

    @Override
    public SetAbstract union(SetAbstract other) {
        switch (other.getType()) {
            case AMBIGUOUS_LIST:
                break;
            case BUILDER:
                break;
            case DEFINED_LIST:
                break;
            case INTERVAL:
                ArrayList<PredicateAbstract> predicates = new ArrayList<PredicateAbstract>();
                predicates.add(new RangePredicate(Constants.NumberConstants.DEFAULT_VAR, other.toInterval()));
                predicates.add(new ElementOf(getSetName(), this));
                return new SetBuilder(getSetName(), Constants.NumberConstants.DEFAULT_VAR, predicates);
            case NULL:
                return this;
            default:
                return null;
        }
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
