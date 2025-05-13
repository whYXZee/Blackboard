package whyxzee.blackboard.terms.variables;

import whyxzee.blackboard.terms.Term;

/**
 * A type of variable where a term is subsituted into the Variable class.
 * 
 * The functionality of this class has not been checked.
 */
public class USubTerm extends Variable {
    //
    // Variables
    //
    private Term innerTerm;

    public USubTerm(int power, Term innerTerm) {
        super("u", power);
        this.innerTerm = innerTerm;

        setShouldChainRule(true);
    }

    public USubTerm(int numPower, int denomPower, Term innerTerm) {
        super("u", numPower, denomPower);
        this.innerTerm = innerTerm;

        setShouldChainRule(true);
    }

    @Override
    public double solve(double value) {
        return Math.pow(innerTerm.solve(value), (double) getNumeratorPower() / getDenominatorPower());
    }

    @Override
    public String toString() {
        if (getNumeratorPower() == 0) {
            return "";
        } else if (getDenominatorPower() == 1) {
            return innerTerm.toString();
        } else {
            return "(" + innerTerm.toString() + ")" + getPowerUnicode();
        }
    }
}
