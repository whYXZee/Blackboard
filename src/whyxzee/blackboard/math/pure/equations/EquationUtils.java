package whyxzee.blackboard.math.pure.equations;

import java.util.ArrayList;

import whyxzee.blackboard.math.pure.terms.PowerTerm;
import whyxzee.blackboard.math.pure.terms.Term;
import whyxzee.blackboard.math.pure.terms.TermUtils;
import whyxzee.blackboard.math.pure.terms.variables.USub;

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

    public static final Term simplifyMultiply(MultiplyEQ eq) {
        ArrayList<Term> terms = deepCopyTerms(eq.getTerms());
        terms = TermUtils.MultiplyTerms.performMultiply(terms);
        if (terms.size() == 1) {
            return terms.get(0);
        }
        return new PowerTerm(1, new USub(new MultiplyEQ(terms)));
    }

    ///
    /// Boolean Methods
    ///
}
