package whyxzee.blackboard.settheory;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.numbers.uncountable.AlephNaught;
import whyxzee.blackboard.settheory.predicates.*;
import whyxzee.blackboard.utils.SetUtils;

/**
 * An abstract package for ambiguous lists such as an IntegerSet or a
 * NaturalSet.
 * 
 * <p>
 * The functionality of this class was checked on {@code 6/3/2025} and the
 * following has changed since:
 * <ul>
 * <li>union() for IntervalSet
 */
public abstract class AmbiguousList extends SetAbstract implements Comparable<AmbiguousList> {
    /* Variables */
    /**
     * The number used to dictate the order.
     */
    private int order;

    public AmbiguousList(String setName, int order) {
        super(setName, SetType.AMBIGUOUS_LIST);
        this.order = order;
    }

    @Override
    public final String toString() {
        return getSetName();
    }

    @Override
    public final String printConsole() {
        return toString();
    }

    public final ElementOf toPredicate() {
        return new ElementOf(Constants.NumberConstants.DEFAULT_VAR, this);
    }

    @Override
    public final SetBuilder toBuilder() {
        return new SetBuilder(getSetName(), Constants.NumberConstants.DEFAULT_VAR, new ArrayList<PredicateAbstract>() {
            {
                add(toPredicate());
            }
        });
    }

    //
    // Get & Set Methods
    //
    public final int getOrder() {
        return order;
    }

    //
    // Arithmetic Methods
    //
    @Override
    public NumberAbstract cardinality() {
        return new AlephNaught();
    }

    @Override
    public final SetAbstract union(SetAbstract other) {
        ArrayList<PredicateAbstract> predicates = new ArrayList<PredicateAbstract>();

        switch (other.getType()) {
            case AMBIGUOUS_LIST:
                return this.getOrder() < ((AmbiguousList) other).getOrder() ? other : this;

            case BUILDER:
                SetBuilder builder = (SetBuilder) other;
                builder.unionPredicate(this.toPredicate());
                return builder;

            case DEFINED_LIST:
                ArrayList<NumberAbstract> numbers = ((DefinedList) other).getNumbers();
                int[] domainsNeeded = SetUtils.UnionHelper.needsTwoDomains(numbers, this);
                predicates.add(this.toPredicate());

                /* Extra Predicates */
                if (domainsNeeded[0] == 0) {
                    return new SetBuilder(other.getSetName(), Constants.NumberConstants.DEFAULT_VAR,
                            predicates);

                } else if (domainsNeeded[0] == 1) {
                    predicates.add(new EqualPredicate(Constants.NumberConstants.DEFAULT_VAR,
                            numbers.get(domainsNeeded[1])));
                } else {
                    predicates.add(new ElementOf(Constants.NumberConstants.DEFAULT_VAR, other));

                }

                return new SetBuilder(other.getSetName() + "'", Constants.NumberConstants.DEFAULT_VAR,
                        new OrPredicate(predicates).toPredicateList());

            case INTERVAL:
                predicates.add(this.toPredicate());
                predicates.add(new RangePredicate(Constants.NumberConstants.DEFAULT_VAR, (IntervalSet) other));
                return new SetBuilder(other.getSetName(), Constants.NumberConstants.DEFAULT_VAR,
                        new OrPredicate(predicates).toPredicateList());

            case NULL:
                return this;
            default:
                return null;
        }
    }

    @Override
    public final SetAbstract disjunction(SetAbstract other) {
        // TODO: add more disjunction functionality
        switch (other.getType()) {
            case AMBIGUOUS_LIST:
                return getOrder() > ((AmbiguousList) other).getOrder() ? other : this;
            case BUILDER:
                break;
            case DEFINED_LIST:
                break;
            case INTERVAL:
                break;
            case NULL:
                return other;
            default:
                break;

        }
        return null;
    }

    //
    // Boolean Methods
    //
    @Override
    public final boolean equals(SetAbstract other) {
        if (!other.isType(SetType.AMBIGUOUS_LIST)) {
            return false;
        }
        return getSetName().equals(other.getSetName());
    }

    @Override
    public final int compareTo(AmbiguousList other) {
        return Integer.compare(order, other.getOrder());
    }
}
