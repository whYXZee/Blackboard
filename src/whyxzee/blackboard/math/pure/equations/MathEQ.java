package whyxzee.blackboard.math.pure.equations;

import java.util.ArrayList;

import whyxzee.blackboard.math.pure.equations.terms.PowerTerm;
import whyxzee.blackboard.math.pure.numbers.ComplexNum;

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

    /**
     * @param variable
     * @param value
     * @return
     */
    public abstract PowerTerm solve(String variable, ComplexNum value);

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

    // #region Overlap Bools
    /**
     * *
     * Checks each term to see if <b>this</b> is a superset of <b>other</b>.
     * 
     * @param other
     * @return
     */
    public final boolean isSupersetOfEQ(MathEQ other) {
        if (other.getClass() != this.getClass()) {
            return false;
        }

        return terms.isSupersetOf(other.getTerms());
    }
    // #endregion
}
