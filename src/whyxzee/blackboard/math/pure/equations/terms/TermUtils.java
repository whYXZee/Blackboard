package whyxzee.blackboard.math.pure.equations.terms;

import java.util.ArrayList;

import whyxzee.blackboard.math.pure.equations.TermArray;
import whyxzee.blackboard.math.pure.numbers.Complex;
import whyxzee.blackboard.math.utils.pure.NumberUtils;

public class TermUtils {
    // #region Plus Minus Addition
    /**
     * A recursive function that calculates all the values of multiple constant
     * {@link whyxzee.blackboard.math.pure.equations.terms.PlusMinusTerm}. The
     * following have
     * been implemented:
     * <ul>
     * <li>TODO: complex numbers
     * <li>TODO: uncountables
     * </ul>
     * 
     * TODO: write this logic in the journal
     * 
     * 
     * @param pmTerms
     * @return
     */
    public static final ArrayList<Complex> addConstantPlusMinusTerms(TermArray pmTerms) {
        if (pmTerms == null || pmTerms.size() == 0) {
            return new ArrayList<Complex>();
        }

        ArrayList<Complex> out = new ArrayList<Complex>();
        TermArray clonedPmTerms = pmTerms.clone();
        Complex coef = pmTerms.get(0).getCoef();
        clonedPmTerms.remove(0);

        if (pmTerms.size() == 1) {
            out.add(coef);
            out.add(coef.negate());
            return out;
        }

        ArrayList<Complex> nextI = addConstantPlusMinusTerms(clonedPmTerms);
        for (Complex i : nextI) {
            Complex iNum = NumberUtils.add(coef, i);
            if (out.contains(coef))
                out.add(iNum);
            out.add(iNum.negate());
        }
        return out;
    }
    // #endregion
}
