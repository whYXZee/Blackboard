package whyxzee.blackboard.math.pure.equations;

import java.util.ArrayList;

import whyxzee.blackboard.math.pure.numbers.BNumber;
import whyxzee.blackboard.math.pure.terms.PowerTerm;
import whyxzee.blackboard.math.pure.terms.Term;
import whyxzee.blackboard.math.pure.terms.TermUtils;
import whyxzee.blackboard.math.pure.terms.Term.TermType;
import whyxzee.blackboard.math.pure.terms.variables.USub;

/**
 * The functionality of this class has been checked on <b>6/16/2025</b> and the
 * following has changed:
 * <ul>
 * <li>simplifyAdditive()
 */
public class EquationUtils {
    /**
     * Copies an ArrayList<Term> so the original stays unchanged, while the returned
     * ArrayList<Term> can change.
     * 
     * @param original
     * @return
     */
    public static final ArrayList<Term> deepCopyTerms(ArrayList<Term> original) {
        ArrayList<Term> copy = new ArrayList<>(original.size());
        for (Term i : original) {
            copy.add(i.clone());
        }
        return copy;
    }

    /**
     * Performs a deep copy. TODO: finish writing this
     * 
     * @param eq
     * @return
     */
    public static final Term simplifyAdditive(AdditiveEQ eq) {
        ArrayList<Term> terms = deepCopyTerms(eq.getTerms());
        terms = TermUtils.AddTerms.performAddition(terms);
        if (terms.size() == 1) {
            // AdditiveEQ not needed if there is only one term.
            return terms.get(0);
        }
        return new PowerTerm(1, new USub(new AdditiveEQ(terms)));
    }

    public static final Term simplifyMultiply(MultiplyEQ eq) {
        ArrayList<Term> terms = deepCopyTerms(eq.getTerms());
        terms = TermUtils.MultiplyTerms.performMultiply(terms);
        if (terms.size() == 1) {
            // MultiplyEQ not needed if there is only one term.
            return terms.get(0);
        }

        // brings out the coefficient
        BNumber coef = findMultiplyConstant(terms);
        return new PowerTerm(coef, new USub(new MultiplyEQ(terms)));
    }

    /**
     * Finds the constant or coefficient term for a MultiplyEQ type equation. If the
     * term is found, it is removed from the ArrayList<Term>.
     * 
     * @param terms
     * @return
     */
    public static final BNumber findMultiplyConstant(ArrayList<Term> terms) {
        for (Term i : terms) {
            switch (i.getTermType()) {
                case POWER:
                    PowerTerm powTerm = (PowerTerm) i;
                    if (powTerm.isConstant()) {
                        terms.remove(i); // so the coef isn't duplicated, removing it from terms removes it from the
                                         // input
                        return powTerm.getCoef();
                    }
                    break;
                default:
                    break;
            }
        }
        return new BNumber(1, 0);
    }

    // #region TermType Getters
    /**
     * 
     * @param terms
     * @param termType
     * @return an ArrayList<Term> of the <b>termType</b> with deep copies of the
     *         terms.
     */
    public static final ArrayList<Term> getTermTypeFromArray(ArrayList<Term> terms, TermType termType) {
        if (terms == null || terms.size() == 0) {
            return new ArrayList<Term>();
        }

        ArrayList<Term> output = new ArrayList<Term>();
        for (Term i : terms) {
            if (i.isTermType(termType)) {
                output.add(i.clone());
            }
        }
        return output;
    }

    /**
     * 
     * @param terms
     * @param termType
     * @return an ArrayList<Term> without the given <b>termType</b> with deep copies
     *         of the terms.
     */
    public static final ArrayList<Term> getTermsExcludingType(ArrayList<Term> terms, TermType termType) {
        if (terms == null || terms.size() == 0) {
            return new ArrayList<Term>();
        }

        ArrayList<Term> output = new ArrayList<Term>();
        for (Term i : terms) {
            if (!i.isTermType(termType)) {
                output.add(i.clone());
            }
        }
        return output;
    }
    // #endregion

    // #region .contains() Bools
    /**
     * Utilizes the
     * {@link whyxzee.blackboard.math.pure.equations.EquationUtils#containsTermType(ArrayList, TermType)}
     * method to perform the check.
     * 
     * @param eq       a Math equation, can be Additive or Multiplicative
     * @param termType the type of term to look for
     * @return
     */
    public static final boolean containsTermType(MathEQ eq, TermType termType) {
        return containsTermType(eq.getTerms(), termType);
    }

    /**
     * Iterates through an ArrayList<Term> to check if the inputted TermType is
     * present.
     * 
     * @param terms    a set of terms
     * @param termType the type of term to look for within the terms
     * @return
     */
    public static final boolean containsTermType(ArrayList<Term> terms, TermType termType) {
        for (Term i : terms) {
            if (i.isTermType(termType)) {
                return true;
            }
        }
        return false;
    }
    // #endregion

    // #region Overlap Bools
    /**
     * <em>Assumes that a and b are the same Equation type.</em>
     * 
     * <p>
     * Checks each term to see if <b>a</b> is a superset of <b>b</b>.
     * 
     * @param a the tested superset
     * @param b
     * @return
     */
    public static final boolean isSupersetOf(ArrayList<Term> a, ArrayList<Term> b) {
        if (b.size() > a.size()) {
            // A cannot be a superset if B contains more terms
            return false;
        }

        for (Term i : b) {
            boolean containsJ = false;
            for (Term j : a) {
                if (j.equals(i)) {
                    containsJ = true;
                    break;
                }
            }

            if (containsJ == false) {
                // if one term is not in A, then it is not a superset
                return false;
            }
        }
        return true;

    }
    // #endregion
}
