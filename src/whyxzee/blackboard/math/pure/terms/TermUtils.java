package whyxzee.blackboard.math.pure.terms;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.pure.equations.EquationUtils;
import whyxzee.blackboard.math.pure.numbers.ComplexNum;
import whyxzee.blackboard.math.pure.terms.Term.TermType;
import whyxzee.blackboard.utils.Loggy;

public class TermUtils {
    // #region CompareTerms
    public static class CompareTerms {
        /**
         * Checks if the two terms meet the criteria for Power multiplication. This
         * criteria is that both terms are PowerTerms and both have equal variables.
         * 
         * @param one the first term
         * @param two the second term
         * @return
         */
        public static final boolean hasPowerSimilarity(Term a, Term b) {
            if (!a.isTermType(TermType.POWER) || !b.isTermType(TermType.POWER)) {
                return false;
            }

            return a.getVar().equals(b.getVar());
        }
    }
    // #endregion

    ///
    /// Operation Methods and Classes
    ///
    /**
     * If a term has a complex number, then it distributes the term to the real and
     * imaginary parts.
     * 
     * @param term
     * @return
     */
    public static final ArrayList<Term> distributeTerm(Term term) {
        if (!term.getCoef().isComplex()) {
            return term.toTermArray();
        }

        Term real = term.clone();
        real.setCoef(term.getCoef().getA());
        Term imaginary = term.clone();
        imaginary.setCoef(new ComplexNum(0, term.getCoef().getB()));
        return new ArrayList<Term>() {
            {
                add(real);
                add(imaginary);
            }
        };
    }

    // #region AddTerms
    /**
     * A general-use package for adding terms. This package works if the
     * ArrayList<Term> contains varying types of terms.
     * 
     * TODO: cannot add PlusMinusTerms
     * 
     * <p>
     * The functionality of this class has been checked on <b>6/14/2025</b> and
     * nothing has changed since.
     */
    public static class AddTerms {
        /* Variables */
        private static ArrayList<Term> addedTerms = new ArrayList<Term>();

        public static final ArrayList<Term> performAddition(ArrayList<Term> terms) {
            if (terms == null || terms.size() == 0) {
                return new ArrayList<Term>();
            } else if (terms.size() == 1) {
                return terms;
            }

            if (!addedTerms.isEmpty()) {
                addedTerms = new ArrayList<Term>();
            }

            /* Algorithm */
            ArrayList<Term> expandedTerms = new ArrayList<Term>();
            // for (Term i : EquationUtils.deepCopyTerms(terms)) {
            for (Term i : terms) {
                // unsure if deep copy is needed
                expandedTerms.addAll(distributeTerm(i));
            }

            for (Term i : expandedTerms) {
                ComplexNum coef = i.getCoef();

                if (contains(i)) {
                    update(i, coef);
                } else {
                    add(i);
                }
            }

            // updates coef data
            for (Term i : addedTerms) {
                i.getCoef().refreshModulus();
                i.getCoef().refreshTheta();
            }
            return getTerms();
        }

        ///
        /// Get & Set Methods
        ///
        private static final ArrayList<Term> getTerms() {
            ArrayList<Term> output = new ArrayList<Term>();
            for (Term i : addedTerms) {
                if (!i.getCoef().isZero()) {
                    output.add(i);
                }
            }
            return output;
        }

        private static final int getIndexOf(Term term) {
            int index = 0;

            for (Term i : addedTerms) {
                if (i.similarTo(term)) {
                    return index;
                }
                index++;
            }
            return -1;
        }

        private static final void add(Term term) {
            addedTerms.add(term);
        }

        /**
         * Updates a term to have a new coefficient.
         * 
         * @param term   the term that should be changed
         * @param addend how much should be added to the coefficient
         */
        private static final void update(Term term, ComplexNum newCoef) {
            addedTerms.get(getIndexOf(term)).addToCoef(newCoef);
        }

        ///
        /// Boolean Methods
        ///
        private static final boolean contains(Term term) {
            // if the index is -1, then the term doesn't exist
            return getIndexOf(term) != -1;
        }
    }
    // #endregion

    // #region MultiplyTerms
    /**
     * A general-use package for multiplying terms. This package works if the
     * ArrayList<Term> contains varying types of terms.
     * 
     * <p>
     * The functionality of this class has been checked on <b>6/15/2025</b> and
     * nothing has changed since.
     */
    public static class MultiplyTerms {
        private static final Loggy mLoggy = new Loggy(Constants.Loggy.MULTIPLY_TERMS_LOGGY);
        /* Variables */
        private static ArrayList<ComplexNum> powers = new ArrayList<ComplexNum>();
        private static ArrayList<Term> mTerms = new ArrayList<Term>();
        private static ComplexNum coef = new ComplexNum(1, 0);
        private static boolean powerMode = false;

        public static final ArrayList<Term> performMultiply(ArrayList<Term> terms) {
            if (terms == null || terms.size() == 0) {
                return new ArrayList<Term>();
            } else if (terms.size() == 1) {
                return terms.get(0).toTermArray();
            }

            if (!isDataEmpty()) {
                powers = new ArrayList<ComplexNum>();
                mTerms = new ArrayList<Term>();
                coef = new ComplexNum(1, 0);
            }

            mLoggy.log("----multiplying " + terms + "----");

            for (Term i : terms) {
                powerMode = false;
                ComplexNum iPower = new ComplexNum(1, 0);
                coef = ComplexNum.multiply(coef, i.getCoef());
                i.setCoef(1);

                /* Term Specific */
                if (i.isTermType(TermType.POWER)) {
                    PowerTerm powTerm = (PowerTerm) i;
                    if (powTerm.isConstant()) {
                        continue;
                    }
                    powerMode = true;
                    iPower = powTerm.getPower();
                    powTerm.setPower(1);
                    i = powTerm;
                }

                int index = getIndexOf(i);
                if (index != -1) {
                    update(index, iPower);
                } else {
                    powers.add(iPower);
                    mTerms.add(i);
                }
            }

            return getTermArray();
        }

        ///
        /// Get & Set Methods
        ///
        private static final ArrayList<Term> getTermArray() {
            ArrayList<Term> output = new ArrayList<Term>();

            for (int i = 0; i < mTerms.size(); i++) {
                ComplexNum iPow = powers.get(i);
                Term iTerm = mTerms.get(i);
                if (iTerm.isTermType(TermType.POWER)) {
                    PowerTerm powTerm = (PowerTerm) iTerm;
                    powTerm.setPower(iPow);
                    output.add(powTerm);
                } else if (!iPow.equals(1)) {
                    output.add(new PowerTerm(1, new USub(iTerm), iPow));
                } else {
                    output.add(iTerm);
                }
            }
            if (output.size() == 1) {
                output.get(0).setCoef(coef);
            } else {
                output.add(new PowerTerm(coef));
            }

            return output;
        }

        private static final void update(int index, ComplexNum power) {
            /* Variables */
            ComplexNum oldPower = powers.get(index);

            mLoggy.log("updating " + mTerms.get(index) + " with power of " + oldPower + " by " + power);
            powers.set(index, ComplexNum.add(oldPower, power));
        }

        private static final int getIndexOf(Term term) {
            for (int i = 0; i < mTerms.size(); i++) {
                Term iTerm = mTerms.get(i);
                if (powerMode && CompareTerms.hasPowerSimilarity(term, iTerm)) {
                    return i;
                }

                if (term.similarTo(iTerm)) {
                    return i;
                }
            }
            return -1;
        }

        ///
        /// Boolean Methods
        ///
        private static final boolean isDataEmpty() {
            return powers.isEmpty() && mTerms.isEmpty() && coef.equals(1);
        }
    }
    // #endregion

    // #region Plus Minus Addition
    /**
     * A recursive function that calculates all the values of multiple constant
     * {@link whyxzee.blackboard.math.pure.terms.PlusMinusTerm}. The following have
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
    public static final ArrayList<ComplexNum> addConstantPlusMinusTerms(ArrayList<Term> pmTerms) {
        if (pmTerms == null || pmTerms.size() == 0) {
            return new ArrayList<ComplexNum>();
        }

        ArrayList<ComplexNum> out = new ArrayList<ComplexNum>();
        ArrayList<Term> clonedPmTerms = EquationUtils.deepCopyTerms(pmTerms);
        ComplexNum coef = pmTerms.get(0).getCoef();
        clonedPmTerms.remove(0);

        if (pmTerms.size() == 1) {
            out.add(coef);
            out.add(coef.negate());
            return out;
        }

        ArrayList<ComplexNum> nextI = addConstantPlusMinusTerms(clonedPmTerms);
        for (ComplexNum i : nextI) {
            ComplexNum iNum = ComplexNum.add(coef, i);
            if (out.contains(coef))
                out.add(iNum);
            out.add(iNum.negate());
        }
        return out;
    }
    // #endregion
}
