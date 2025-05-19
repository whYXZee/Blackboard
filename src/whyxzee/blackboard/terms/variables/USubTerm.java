package whyxzee.blackboard.terms.variables;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.Constants.VariableConstants;
import whyxzee.blackboard.terms.Term;
import whyxzee.blackboard.terms.TrigTerm;
import whyxzee.blackboard.terms.TrigTerm.TrigType;

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
        super("u", power, VarType.U_SUB_TERM);
        this.innerTerm = innerTerm;

        setShouldChainRule(true);
    }

    public USubTerm(int numPower, int denomPower, Term innerTerm) {
        super("u", numPower, denomPower, VarType.U_SUB_TERM);
        this.innerTerm = innerTerm;

        setShouldChainRule(true);
    }

    @Override
    public double solve(double value) {
        return Math.pow(innerTerm.solve(value), (double) getNumeratorPower() / getDenominatorPower());
    }

    @Override
    public Term integrate() {
        /* Initiating Variables */
        int nPower = getNumeratorPower();
        int dPower = getDenominatorPower();

        /* Integration Algorithm */
        if (getShouldChainRule()) {

        } else {
            // no chain rule

            if (nPower == 2 && dPower == 1 && innerTerm.equals(Constants.TrigConstants.SECANT)) {
                // (sec(x))^2
                return new TrigTerm(1, VariableConstants.BASE_VAR, TrigType.TANGENT);
            } else if (nPower == 2 && dPower == 1
                    && innerTerm.equals(Constants.TrigConstants.COSECANT)) {
                return new TrigTerm(-1, VariableConstants.BASE_VAR, TrigType.COTANGENT);
            }
        }

        return null;
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
