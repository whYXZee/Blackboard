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
