package whyxzee.blackboard.math.pure.terms;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.pure.numbers.ComplexNum;
import whyxzee.blackboard.math.pure.terms.variables.Variable;

/**
 * Maybe this class can be implemented somehow else?
 */
public class PlusMinusTerm extends Term {
    // #region Constructors
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
    public PlusMinusTerm(ComplexNum coef) {
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
    public PlusMinusTerm(ComplexNum coef, Variable var) {
        super(coef, var, TermType.PLUS_MINUS);
    }
    // #endregion

    // #region String / Display
    @Override
    public final String toString() {
        String output = "";
        output += Constants.Unicode.PLUS_MINUS;

        /* Coefficient */
        if (getCoef().isZero()) {
            return "0";
        }
        output += coefString();
        output += getVar();

        return output;
    }
    // #endregion

    // #region Copying / Cloning
    @Override
    public final Term clone() {
        return new PlusMinusTerm(getCoef().clone(), getVar());
    }
    // #endregion

    // #region Arithmetic w/ Coef
    @Override
    public Term negate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'negate'");
    }
    // #endregion

    @Override
    public ComplexNum solve(ComplexNum value) {
        ComplexNum output = getCoef().clone();

        if (getVar().equals("")) {
            return output;
        }

        output = ComplexNum.multiply(output, getVar().solve(value));
        return output;
    }

    // #region Term Bools
    @Override
    public boolean similarTo(Term term) {
        // TODO: this term cannot be combined with others
        // +- 2 +- 2 is not +- 4, as it can output to 0, +-2, or +-4
        if (!term.isTermType(TermType.PLUS_MINUS)) {
            return false;
        }

        return getVar().equals(term.getVar());
    }

    @Override
    public boolean contains(Object var1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'contains'");
    }
    // #endregion

}
