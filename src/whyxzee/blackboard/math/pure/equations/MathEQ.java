package whyxzee.blackboard.math.pure.equations;

import java.util.ArrayList;

import whyxzee.blackboard.math.pure.equations.terms.PowerTerm;

public abstract class MathEQ {
    /* Terms */
    private TermArray terms;

    /* Logic */
    private EQType type = EQType.ADDITIVE;

    public enum EQType {
        ADDITIVE,
        MULTIPLY,
    }

    // #region Constructors
    /**
     * 
     * @param type
     * @param terms <em>Does not perform a deep copy of the terms</em>
     */
    public MathEQ(EQType type, ArrayList<PowerTerm> terms) {
        this.type = type;
        this.terms = new TermArray(terms);
    }

    /**
     * 
     * @param type
     * @param terms <em>Does not perform a deep copy of the terms</em>
     */
    public MathEQ(EQType type, PowerTerm... terms) {
        this.type = type;
        this.terms = new TermArray(terms);
    }

    public MathEQ(EQType type, TermArray terms) {
        this.type = type;
        this.terms = terms;
    }
    // #endregion

    // #region Copying/Cloning
    /**
     * Creates a deep copy of <b>this</b>.
     * 
     * @return
     */
    public abstract MathEQ clone();
    // #endregion

    // #region Conversion Methods
    /**
     * Performs a deep copy of the MathEQ into a new PowerTerm. If the MathEQ
     * consists of a singular term, then it returns that.
     * 
     * @return
     */
    public abstract PowerTerm toTerm();
    // #endregion

    // #region Get/Set
    public final EQType getType() {
        return type;
    }

    public final TermArray getTerms() {
        return terms;
    }

    public final void setTerms(ArrayList<PowerTerm> terms) {
        this.terms = new TermArray(terms);
    }

    public final void setTerms(PowerTerm... terms) {
        this.terms = new TermArray(terms);
    }

    public final void setTerms(TermArray terms) {
        this.terms = terms;
    }
    // #endregion

    // #region Solve
    /**
     * Plugs in a term in place of the variable.
     * 
     * @param variable the variable to replace.
     * @param value
     * @return
     */
    public abstract PowerTerm solve(String variable, PowerTerm value);
    // #endregion

    // /**
    // * Should only be used for equations that do not have any variables in them,
    // but
    // * have multiple term types.
    // *
    // * @return
    // */
    // public abstract DefinedList solutions();

    // #region Comparison Bools
    public final boolean isType(EQType type) {
        return this.type == type;
    }
    // #endregion
}
