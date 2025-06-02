package whyxzee.blackboard.settheory.predicates;

import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.utils.ArithmeticUtils;

/**
 * A package that checks if a value is even or odd.
 * 
 * <p>
 * The functionality of this method has been checked on {@code 6/2/2025} and
 * nothing has changed since.
 */
public class SetEvenOdd extends PredicateAbstract {
    /* Variables */
    private boolean isConditionOdd = false;

    public SetEvenOdd(String var) {
        super(var, PredicateType.EVEN_ODD);
    }

    public SetEvenOdd(String var, boolean isConditionOdd) {
        super(var, PredicateType.EVEN_ODD);
        this.isConditionOdd = isConditionOdd;
    }

    @Override
    public final String toString() {
        return getVar() + " is " + (isConditionOdd ? "odd" : "even");
    }

    @Override
    public final String printConsole() {
        return toString();
    }

    /**
     * Returns false if the following criteria are met:
     * <ul>
     * <li><b>value</b> is not an integer.
     * <li><b>value</b> is odd when <b>isConditionOdd</b> is false
     * <li><b>value</b> is false when <b>isConditionOdd</b> is true
     * </ul>
     * 
     * Returns ture if the following criteria are met:
     * <ul>
     * <li><b>value</b> is odd when <b>isConditionOdd</b> is true
     * <li><b>value</b> is even when <b>isConditionOdd</b> is false
     * 
     * @return
     */
    @Override
    public final boolean checkPredicate(NumberAbstract number) {
        if (!number.isInteger()) {
            return false;
        }

        if (isConditionOdd) {
            return !ArithmeticUtils.isEven(number.getValue());
        }
        return ArithmeticUtils.isEven(number.getValue());
    }
}
