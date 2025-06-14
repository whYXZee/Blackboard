package whyxzee.blackboard.math.applied.settheory;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.pure.numbers.BNumber;

/**
 * A package that is a set of numbers.
 * 
 * <p>
 * The functionality of this class has been checked on {@code 6/5/2025} and
 * nothing has changed since.
 */
public class DefinedList extends SetAbstract {
    private ArrayList<BNumber> numbers;

    public DefinedList(String setName, ArrayList<BNumber> numbers) {
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

    // @Override
    // public IntervalSet toInterval() {
    // throw new UnsupportedOperationException();
    // }

    @Override
    public DefinedList toDefinedList() {
        return this;
    }

    // @Override
    // public SetBuilder toBuilder() {
    // throw new UnsupportedOperationException("Unimplemented method
    // 'toDefinedList'");
    // }

    //
    // Get & Set Methods
    //
    public final ArrayList<BNumber> getNumbers() {
        return numbers;
    }

    //
    // Arithmetic Methods
    //
    @Override
    public final BNumber cardinality() {
        return new BNumber(numbers.size(), 0);
    }

    @Override
    public SetAbstract union(SetAbstract other) {
        // ArrayList<PredicateAbstract> predicates = new ArrayList<PredicateAbstract>();
        // int[] domainsNeeded;
        // String unionName = SetUtils.unionString(this, other);

        // switch (other.getAmbiguousList()) {
        // case AMBIGUOUS_LIST: // functional
        // AmbiguousList domain = (AmbiguousList) other;
        // domainsNeeded = SetUtils.UnionHelper.needsTwoDomains(numbers, domain);
        // predicates.add(domain.toPredicate());

        // /* Extra Predicates */
        // if (domainsNeeded[0] == 0) {
        // return new SetBuilder(unionName, Constants.NumberConstants.DEFAULT_VAR,
        // predicates);

        // } else if (domainsNeeded[0] == 1) {
        // predicates.add(
        // new EqualPredicate(Constants.NumberConstants.DEFAULT_VAR,
        // numbers.get(domainsNeeded[1])));
        // } else {
        // predicates.add(new ElementOf(Constants.NumberConstants.DEFAULT_VAR, this));
        // }

        // return new SetBuilder(unionName, Constants.NumberConstants.DEFAULT_VAR,
        // new OrPredicate(predicates).toPredicateList());

        // case BUILDER:
        // SetBuilder builder = (SetBuilder) other;
        // domainsNeeded = SetUtils.UnionHelper.needsTwoDomains(numbers, builder);
        // /* Extra Predicates */
        // if (domainsNeeded[0] == 0) {
        // return builder;

        // } else if (domainsNeeded[0] == 1) {
        // predicates.add(
        // new EqualPredicate(builder.getVar(), numbers.get(domainsNeeded[1])));
        // } else {
        // predicates.add(new ElementOf(builder.getVar(), this));
        // }
        // builder.unionPredicates(predicates);
        // return builder;

        // case DEFINED_LIST:
        // return UnionDefinedLists.performUnion(this, (DefinedList) other);
        // case INTERVAL:
        // return new SetBuilder(unionName, Constants.NumberConstants.DEFAULT_VAR,
        // SetUtils.UnionHelper.numbersInInterval(this, other.toInterval()));

        // case NULL:
        // return this;
        // default:
        // return null;
        // }
        throw new UnsupportedOperationException("Unimplemented method 'disjoint'");
    }

    @Override
    public SetAbstract intersection(SetAbstract other) {
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
    public boolean inSet(BNumber number) {
        for (BNumber i : numbers) {
            if (i.equals(number)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public final boolean isSuperset(SetAbstract other) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(SetAbstract other) {
        throw new UnsupportedOperationException();
    }
}
