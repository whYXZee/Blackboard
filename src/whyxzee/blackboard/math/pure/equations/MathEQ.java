package whyxzee.blackboard.math.pure.equations;

import java.util.ArrayList;

import whyxzee.blackboard.math.applied.settheory.DefinedList;
import whyxzee.blackboard.math.pure.numbers.BNumber;
import whyxzee.blackboard.math.pure.terms.Term;
import whyxzee.blackboard.math.pure.terms.Term.TermType;

public abstract class MathEQ {
    /* Terms */
    private ArrayList<Term> terms = new ArrayList<Term>();
    private ArrayList<Term> powTerms = new ArrayList<Term>();
    private ArrayList<Term> plusMinTerms = new ArrayList<Term>();

    /* Logic */
    private EQType type = EQType.ADDITIVE;

    public enum EQType {
        ADDITIVE,
        MULTIPLY,
    }

    public MathEQ(EQType type, ArrayList<Term> terms) {
        this.type = type;
        for (Term i : terms) {
            sort(i);
        }
    }

    public MathEQ(EQType type, Term... terms) {
        this.type = type;
        for (Term i : terms) {
            sort(i);
        }
    }

    public final void sort(Term term) {
        switch (term.getTermType()) {
            case POWER:
                powTerms.add(term);
                break;
            case PLUS_MINUS:
                plusMinTerms.add(term);
                break;
            default:
                break;
        }

        terms.add(term);
    }

    // #region Get/Set
    public final ArrayList<Term> getTerms() {
        return terms;
    }

    public final void setTerms(ArrayList<Term> terms) {
        this.terms = terms;
    }

    public final ArrayList<Term> getTermArr(TermType termType) {
        switch (termType) {
            case POWER:
                return powTerms;
            case PLUS_MINUS:
                return plusMinTerms;
            default:
                return null;
        }
    }
    // #endregion

    ///
    /// Arithmetic Methods
    ///
    /**
     * @deprecated develop multivariate :sob:
     * @param value
     * @return
     */
    public abstract BNumber solve(BNumber value);

    /**
     * Should only be used for equations that do not have any variables in them, but
     * have multiple term types.
     * 
     * @return
     */
    public abstract DefinedList solutions();

    ///
    /// Boolean Methods
    ///
    /**
     * Checks if the math function contains the given variable.
     * 
     * @param var
     * @return
     */
    public final boolean containsVar(String var) {
        for (Term i : terms) {
            if (i.containsVar(var)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isType(EQType type) {
        return this.type == type;
    }
}
