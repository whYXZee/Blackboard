package whyxzee.blackboard.terms.variables;

import whyxzee.blackboard.terms.Term;

/**
 * A type of
 */
public class USubTerm extends Variable {
    //
    // Variables
    //
    private Term innerTerm;

    public USubTerm(int power, Term innerTerm) {
        super("u", power);
        this.innerTerm = innerTerm;
    }

    public USubTerm(int numPower, int denomPower, Term innerTerm) {
        super("u", numPower, denomPower);
        this.innerTerm = innerTerm;
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
