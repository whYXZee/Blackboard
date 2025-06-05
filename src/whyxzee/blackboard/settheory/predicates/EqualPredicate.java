package whyxzee.blackboard.settheory.predicates;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.settheory.IntervalSet;

public class EqualPredicate extends PredicateAbstract {
    /* Variables */
    private boolean isNotEqual;
    private NumberAbstract value;

    public EqualPredicate(String var, NumberAbstract value) {
        super(var, PredicateType.EQUAL);
        this.value = value;
        this.isNotEqual = false;
    }

    public EqualPredicate(String var, NumberAbstract value, boolean isNotEqual) {
        super(var, PredicateType.EQUAL);
        this.value = value;
        this.isNotEqual = isNotEqual;
    }

    @Override
    public final String toString() {
        return getVar() + (isNotEqual ? Constants.Unicode.NOT_EQUAL : Constants.Unicode.EQUAL) + value.toString();
    }

    @Override
    public String printConsole() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printConsole'");
    }

    @Override
    public IntervalSet toInterval() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toInterval'");
    }

    //
    // Get & Set Methods
    //
    public final boolean isNotEqual() {
        return isNotEqual;
    }

    public final NumberAbstract getValue() {
        return value;
    }

    //
    // Boolean Methods
    //
    @Override
    public boolean checkPredicate(NumberAbstract number) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkPredicate'");
    }

    @Override
    public final boolean equals(PredicateAbstract other) {
        if (!other.isType(PredicateType.EQUAL) || !(getVar().equals(other.getVar()))) {
            return false;
        }

        EqualPredicate eq = (EqualPredicate) other;

        return (isNotEqual == eq.isNotEqual())
                && (value.equals(eq.getValue()));
    }

}
