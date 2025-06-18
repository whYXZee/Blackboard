package whyxzee.blackboard.math.pure.equations;

import java.util.ArrayList;

import whyxzee.blackboard.math.pure.numbers.BNumber;
import whyxzee.blackboard.math.pure.terms.PowerTerm;
import whyxzee.blackboard.math.pure.terms.Term;
import whyxzee.blackboard.math.pure.terms.TermUtils;
import whyxzee.blackboard.math.pure.terms.variables.USub;

/**
 * The functionality of this class has been checked on <b>6/16/2025</b> and the
 * following has changed:
 * <ul>
 * <li>simplifyAdditive()
 */
public class EquationUtils {
    ///
    ///
    ///
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

    ///
    /// Boolean Methods
    ///
}
