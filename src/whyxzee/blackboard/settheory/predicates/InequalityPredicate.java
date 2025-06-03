package whyxzee.blackboard.settheory.predicates;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.utils.ArithmeticUtils;

/**
 * A package for a boolean predicate for a set.
 * 
 * <p>
 * This package is built similar to a <b>var</b> <b>inequalityType</b> number
 * (example: x < 5)
 */
public class InequalityPredicate extends PredicateAbstract {
    /* Variables */
    private double value;
    private InequalityType inequality;

    public enum InequalityType {
        LESS_THAN,
        LESS_THAN_EQUAL,
        GREATER_THAN,
        GREATER_THAN_EQUAL
    }

    public InequalityPredicate(String var, InequalityType inequality, double value) {
        super(var, PredicateType.INEQUALITY);
        this.inequality = inequality;
        this.value = value;
    }

    @Override
    public final String toString() {
        String output = getVar();
        switch (inequality) {
            case LESS_THAN:
                output += Constants.Unicode.LESS_THAN;
                break;
            case LESS_THAN_EQUAL:
                output += Constants.Unicode.LESS_THAN_EQUAL;
                break;
            case GREATER_THAN:
                output += Constants.Unicode.GREATER_THAN;
                break;
            case GREATER_THAN_EQUAL:
                output += Constants.Unicode.GREATER_THAN_EQUAL;
                break;
            default:
                break;
        }

        if (ArithmeticUtils.isInteger(value)) {
            return output + (int) value;
        }
        return output + value;
    }

    @Override
    public final String printConsole() {
        return toString();
    }

    //
    // Get & Set Methods
    //
    public final double getValue() {
        return value;
    }

    public final void setValue(double value) {
        this.value = value;
    }

    //
    // Boolean Methods
    //
    @Override
    public boolean checkPredicate(NumberAbstract number) {
        double value = number.getValue();

        switch (inequality) {
            case LESS_THAN:
                return value < value;
            case LESS_THAN_EQUAL:
                return value <= value;
            case GREATER_THAN:
                return value > value;
            case GREATER_THAN_EQUAL:
                return value >= value;
            default:
                return false;
        }
    }

    public final boolean isInequality(InequalityType inequality) {
        return this.inequality == inequality;
    }

    @Override
    public final boolean equals(PredicateAbstract other) {
        if (!other.isType(PredicateType.INEQUALITY) || !other.getVar().equals(getVar())) {
            return false;
        }

        InequalityPredicate ineq = (InequalityPredicate) other;
        return (value == ineq.getValue()) &&
                (ineq.isInequality(inequality));
    }

}
