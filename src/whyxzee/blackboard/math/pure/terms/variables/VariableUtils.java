package whyxzee.blackboard.math.pure.terms.variables;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.pure.equations.EquationUtils;
import whyxzee.blackboard.math.pure.equations.MathEQ;
import whyxzee.blackboard.math.pure.terms.Term;
import whyxzee.blackboard.utils.Loggy;

public class VariableUtils {
    private static final Loggy loggy = new Loggy(Constants.Loggy.VARIABLE_UTILS_LOGGY);

    // #region EQ .contains()
    /**
     * Checks if <b>this</b> eq is a superset of <b>other</b> eq. If
     * not, then it checks if <b>other</b> is a variable of one of the terms.
     * 
     * @param eq
     * @param other USubbed function, so that a new one isn't created to check
     *              within each term.
     * @return
     */
    public static final boolean eqContainsEQ(MathEQ eq, USub other) {
        // if additive & multiplicate or vice versa, then there is no subset
        MathEQ otherEQ = other.getInnerFunction();
        boolean isBidirectional = eq.isType(otherEQ.getType());
        ArrayList<Term> objTerms = eq.getTerms();

        if (isBidirectional) {
            ArrayList<Term> othTerms = otherEQ.getTerms();
            if (EquationUtils.isSupersetOf(objTerms, othTerms)) {
                return true;
            }
        }

        for (Term i : objTerms) {
            if (i.containsVar(other)) {
                return true;
            }
        }
        return false;

    }

    /**
     * @param eq
     * @param other
     * @return
     */
    public static final boolean eqContainsTerm(MathEQ eq, USub other) {
        loggy.logHeader(eq + " contains " + other);

        Term inner = other.getInnerTerm();
        for (Term i : eq.getTerms()) {
            loggy.logDetail(i.toString());

            if (i.equals(inner) || i.containsVar(other)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @param eq
     * @param var
     * @return
     */
    public static final boolean eqContainsVar(MathEQ eq, Variable var) {
        for (Term i : eq.getTerms()) {
            if (i.containsVar(var)) {
                return true;
            }
        }
        return false;
    }
    // #endregion
}
