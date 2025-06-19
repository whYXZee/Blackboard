package whyxzee.blackboard.math.pure.algebra.solver;

import java.util.ArrayList;

import whyxzee.blackboard.math.applied.settheory.DefinedList;
import whyxzee.blackboard.math.pure.numbers.BNumber;
import whyxzee.blackboard.math.pure.terms.Term;
import whyxzee.blackboard.math.pure.terms.variables.Variable;

/**
 * A class which contains data for the solutions to an algebraic solver.
 * 
 * <p>
 * The functionality of this class has not been checked.
 */
public class SolutionData {
    /* Variables */
    private Term term; // multivariate
    private DefinedList list;

    public SolutionData(Term term) {
        this.term = term;
        list = null;
    }

    public SolutionData(Variable var, ArrayList<BNumber> solutions) {
        list = new DefinedList(var.toString(), solutions);
        term = null;
    }

    @Override
    public final String toString() {
        if (isList()) {
            return list.toString();
        }
        return term.toString();
    }

    // #region Term
    public final Term getTerm() {
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