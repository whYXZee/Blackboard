package whyxzee.blackboard.math.pure.algebra;

import java.util.ArrayList;

import whyxzee.blackboard.math.pure.numbers.BNumber;
import whyxzee.blackboard.math.pure.terms.PowerTerm;
import whyxzee.blackboard.math.pure.terms.Term;

/**
 * <p>
 * The functionality of this class has not been checked.
 */
public class AlgebraUtils {
    ///
    /// Boolean Methods
    ///
    public static final boolean isQuadratic(ArrayList<Term> terms) {
        // TODO: complex/imaginary numbers?

        // quadratics must have 3 terms
        if (terms.size() != 3) {
            return false;
        }

        // quadratics have at least 2 power terms

        BNumber[] powOfTerms = new BNumber[3];
        for (int i = 0; i < 3; i++) {
            try {
                powOfTerms[i] = ((PowerTerm) terms.get(i)).getPower();
            } catch (java.lang.ClassCastException e) {
                // could be something like a trig term, but the first one would be a power with
                // u-sub
                powOfTerms[i] = new BNumber(1, 0);
            }
        }
        if (!powOfTerms[2].equals(0)) {
            // constant must have a power of 0
            return false;
        }

        // TODO: what if it is 1 + 2x^(-1) + x^(-2)?

        // the power of term 1 must be two times the power of the second
        powOfTerms[0].divide(powOfTerms[1]);
        if (!powOfTerms[0].equals(2)) {
            return false;
        }

        return true;
    }
}
