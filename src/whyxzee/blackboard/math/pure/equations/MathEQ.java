package whyxzee.blackboard.math.pure.equations;

import java.util.ArrayList;

import whyxzee.blackboard.math.applied.settheory.DefinedList;
import whyxzee.blackboard.math.pure.numbers.ComplexNum;
import whyxzee.blackboard.math.pure.terms.Term;
import whyxzee.blackboard.math.pure.terms.Term.TermType;
import whyxzee.blackboard.math.pure.terms.variables.Variable;
import whyxzee.blackboard.math.pure.terms.variables.VariableUtils;

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
    public final EQType getType() {
        return type;
    }

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

    /**
     * @deprecated develop multivariate :sob:
     * @param value
     * @return
     */
    public abstract ComplexNum solve(ComplexNum value);

    /**
     * Should only be used for equations that do not have any variables in them, but
     * have multiple term types.
     * 
     * @return
     */
    public abstract DefinedList solutions();

    // #region Comparison Bools
    @Deprecated
    public final boolean containsVar(Variable var) {
        // switch (var.getVarType()) {
        // case U_SUB_EQ:
        // return VariableUtils.eqContainsEQ(this, (USub) var);
        // case U_SUB_TERM:
        // return VariableUtils.eqContainsTerm(this, (USub) var);
        // case VARIABLE:
        // return VariableUtils.eqContainsVar(this, var);
        // default:
        // break;

        // }
        return false;

    }

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
        if (other.getClass() != this.getClass() || other.getTerms().size() > terms.size()) {
            // A cannot be a superset if B contains more terms
            return false;
        }

        for (Term i : other.getTerms()) {
            if (!terms.contains(i)) {
                return false;
            }
        }
        return true;

    }

    public final boolean contains(Object var1) {
        if (var1 == null) {
            return false;
        }

        if (var1 instanceof MathEQ) {
            return isSupersetOfEQ((MathEQ) var1);

        } else if (var1 instanceof Term) {

        } else if (var1 instanceof Variable) {
            Variable var = (Variable) var1;
            switch (var.getVarType()) {
                case U_SUB_EQ:
                    break;
                case U_SUB_TERM:
                    break;
                case VARIABLE:
                    break;
                default:
                    break;
            }
        }
        return false;

    }
    // #endregion
}
