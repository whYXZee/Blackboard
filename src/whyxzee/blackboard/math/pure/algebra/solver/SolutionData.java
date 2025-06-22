package whyxzee.blackboard.math.pure.algebra.solver;

import java.util.ArrayList;

import whyxzee.blackboard.math.applied.settheory.DefinedList;
import whyxzee.blackboard.math.pure.equations.terms.PowerTerm;
import whyxzee.blackboard.math.pure.equations.variables.Variable;
import whyxzee.blackboard.math.pure.numbers.ComplexNum;

/**
 * A class which contains data for the solutions to an algebraic solver.
 * 
 * <p>
 * The functionality of this class has not been checked.
 */
public class SolutionData {
    /* Variables */
    private PowerTerm term; // multivariate
    private DefinedList list;
    private Variable<?> var;

    public SolutionData(Variable<?> var, PowerTerm term) {
        this.var = var;
        this.term = term;
        list = null;
    }

    public SolutionData(Variable<?> var, ArrayList<ComplexNum> solutions) {
        this.var = var;
        term = null;
        list = new DefinedList(var.toString(), solutions);
    }

    @Override
    public final String toString() {
        if (isList()) {
            return list.toString();
        }
        return var + " = " + term.toString();
    }

    // #region Term
    public final PowerTerm getTerm() {
        return term;
    }
    // #endregion

    // #region List
    public final DefinedList getList() {
        return list;
    }

    public final boolean isList() {
        return list != null;
    }
    // #endregion
}