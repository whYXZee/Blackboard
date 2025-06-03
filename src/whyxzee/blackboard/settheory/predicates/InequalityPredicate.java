package whyxzee.blackboard.settheory.predicates;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.Infinity;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.settheory.Bound;
import whyxzee.blackboard.settheory.IntervalSet;
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
    private NumberAbstract value;
    private InequalityType inequality;

    public enum InequalityType {
        LESS_THAN,
        LESS_THAN_EQUAL,
        GREATER_THAN,
        GREATER_THAN_EQUAL
    }

    public InequalityPredicate(String var, InequalityType inequality, NumberAbstract value) {
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

        if (ArithmeticUtils.isInteger(value.getValue())) {
            return output + (int) value.getValue();
        }
        return output + value;
    }

    public final IntervalSet toInterval() {
        Bound lBound, uBound;

        switch (inequality) {
            case LESS_THAN:
                lBound = new Bound(new Infinity(true), true);
                uBound = new Bound(value, true);
                break;
            case LESS_THAN_EQUAL:
                lBound = new Bound(new Infinity(true), true);
                uBound = new Bound(value, false);
                break;
            case GREATER_THAN:
                lBound = new Bound(value, true);
                uBound = new Bound(new Infinity(false), true);
                break;
            case GREATER_THAN_EQUAL:
                lBound = new Bound(value, false);
                uBound = new Bound(new Infinity(false), true);
                break;
            default:
                lBound = uBound = null;
                break;
        }
        return new IntervalSet("", getVar(), lBound, uBound);
    }

    @Override
    public final String printConsole() {
        return toString();
    }

    //
    // Get & Set Methods
    //
    public final NumberAbstract getValue() {
        return value;
    }

    public final void setValue(NumberAbstract value) {
        this.value = value;
    }

    //
    // Boolean Methods
    //
    @Override
    public boolean checkPredicate(NumberAbstract number) {
        switch (inequality) {
            case LESS_THAN:
                return number.lessThan(value);
            case LESS_THAN_EQUAL:
                return number.lessThanEqual(value);
            case GREATER_THAN:
                return number.greaterThan(value);
            case GREATER_THAN_EQUAL:
                return number.greaterThanEqual(value);
            default:
                return false;
        }
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

    public final boolean isInequality(InequalityType inequality) {
        return this.inequality == inequality;
    }

}
