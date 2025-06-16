package whyxzee.blackboard.math.pure.terms;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.pure.numbers.BNumber;
import whyxzee.blackboard.math.pure.terms.variables.Variable;

/**
 * Maybe this class can be implemented somehow else?
 */
public class PlusMinusTerm extends Term {
    ///
    /// Constructor Methods
    ///
    // #region
    /**
     * Constructor for a plus-or-minus term with a real number.
     * 
     * @param coef
     */
    public PlusMinusTerm(double coef) {
        super(coef, Variable.noVar, TermType.PLUS_MINUS);
    }

    /**
     * Constructor for a plus-or-minus term with a complex number.
     * 
     * @param coef
     */
    public PlusMinusTerm(BNumber coef) {
        super(coef, Variable.noVar, TermType.PLUS_MINUS);
    }

    /**
     * Constructor for a plus-or-minus term with a real number and variable.
     * 
     * @param coef
     */
    public PlusMinusTerm(double coef, Variable var) {
        super(coef, var, TermType.PLUS_MINUS);
    }

    /**
     * Constructor for a plus-or-minus term with a complex number and variable.
     * 
     * @param coef
     */
    public PlusMinusTerm(BNumber coef, Variable var) {
        super(coef, var, TermType.PLUS_MINUS);
    }
    // #endregion

    @Override
    public final String toString() {
        return Constants.Unicode.PLUS_MINUS + " " + getCoef() + getVar();
    }

    @Override
    public final Term clone() {
        return new PlusMinusTerm(getCoef().clone(), getVar());
    }

    ///
    /// Arithmetic Methods
    ///
    @Override
    public Term negate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'negate'");
    }

    @Override
    public BNumber solve(BNumber value) {
        BNumber output = getCoef().clone();

        if (getVar().getVar().equals("")) {
            return output;
        }

        output.multiply(getVar().solve(value));
        return output;
    }

    ///
    /// Boolean Methods
    ///
    @Override
    public boolean similarTo(Term term) {
        if (!term.isTermType(TermType.PLUS_MINUS)) {
            return false;
        }

        return getVar().equals(term.getVar());
    }
}
