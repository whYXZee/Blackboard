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
public abstract class AmbiguousList extends SetAbstract {
    /* Variables */
    private DomainType domain;

    public enum DomainType {
        COMPLEX(6),
        REAL(5),
        RATIONAL(4),
        INTEGER(3),
        NATURAL(2),

        IMAGINARY(-1);

        /**
         * The number used to dictate the highest-level list. A greater number indicates
         * that it encompasses more numbers.
         */
        private final int order;

        private DomainType(int order) {
            this.order = order;
        }

        public final int getOrder() {
            return order;
        }
    }

    public AmbiguousList(String setName, DomainType domain) {
        super(setName, SetType.AMBIGUOUS_LIST);
        this.domain = domain;
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
    public final DomainType getDomain() {
        return domain;
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

        switch (other.getAmbiguousList()) {
            case AMBIGUOUS_LIST:
                return domain.getOrder() < ((AmbiguousList) other).getDomain().getOrder() ? other : this;

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
        switch (other.getAmbiguousList()) {
            case AMBIGUOUS_LIST:
                return domain.getOrder() > ((AmbiguousList) other).getDomain().getOrder() ? other : this;
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
    public final boolean isSuperset(SetAbstract other) {
        switch (other.getAmbiguousList()) {
            case AMBIGUOUS_LIST:
                AmbiguousList oList = (AmbiguousList) other;
                if (oList.getDomain().getOrder() == -1) {
                    return domain == DomainType.COMPLEX;
                }

                return domain.getOrder() > oList.getDomain().getOrder();
            case BUILDER:
                break;
            case DEFINED_LIST:
                break;
            case INTERVAL:
                break;
            case NULL:
                break;
            default:
                break;
        }

        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean equals(SetAbstract other) {
        if (!other.isType(SetType.AMBIGUOUS_LIST)) {
            return false;
        }
        return getSetName().equals(other.getSetName());
    }
}
