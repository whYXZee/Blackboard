package whyxzee.blackboard.settheory.predicates;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.utils.ArithmeticUtils;

/**
 * A package for a boolean predicate for a set.
 * 
 * <p>
 * This package is built similar to a <b>var</b> <b>inequalityType</b> number
 * (example: x < 5)
 */
public class SetInequality extends PredicateAbstract {
    /* Variables */
    private double comparison;
    private InequalityType inequality;

    public enum InequalityType {
        LESS_THAN,
        LESS_THAN_EQUAL,
        GREATER_THAN,
        GREATER_THAN_EQUAL
    }

    public SetInequality(String var, InequalityType inequality, double comparison) {
        super(var, PredicateType.INEQUALITY);
        this.inequality = inequality;
        this.comparison = comparison;
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

        if (ArithmeticUtils.isInteger(comparison)) {
            return output + (int) comparison;
        }
        return output + comparison;
    }

    @Override
    public final String printConsole() {
        return toString();
    }

    //
    // Boolean
    //
    @Override
    public boolean checkPredicate(double value) {
        switch (inequality) {
            case LESS_THAN:
                return value < comparison;
            case LESS_THAN_EQUAL:
                return value <= comparison;
            case GREATER_THAN:
                return value > comparison;
            case GREATER_THAN_EQUAL:
                return value >= comparison;
            default:
                return false;
        }
    }

}
