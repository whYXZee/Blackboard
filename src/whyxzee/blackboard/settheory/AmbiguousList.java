package whyxzee.blackboard.settheory;

/**
 * An abstract package for ambiguous lists such as an IntegerSet or a
 * NaturalSet.
 * 
 * <p>
 * The functionality of this class was checked on {@code 6/3/2025} and nothing
 * has changed since.
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
    public final SetAbstract union(SetAbstract other) {
        // TODO: add more union functionality
        switch (other.getType()) {
            case AMBIGUOUS_LIST:
                System.out.println(this.getOrder() + "<" + ((AmbiguousList) other).getOrder());
                return this.getOrder() < ((AmbiguousList) other).getOrder() ? other : this;
            case BUILDER:
                SetBuilder builder = (SetBuilder) other;
                builder.unionDomain(this);
                return builder;
            case DEFINED_LIST:
                break;
            case INTERVAL:
                break;
            case NULL:
                System.out.println("null");
                return this;
            case SOLUTION:
                break;
            default:
                break;

        }
        return null;
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
            case SOLUTION:
                break;
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
