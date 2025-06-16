package whyxzee.blackboard.math.pure.terms;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.pure.numbers.BNumber;
import whyxzee.blackboard.math.pure.terms.Term.TermType;
import whyxzee.blackboard.math.pure.terms.variables.USub;

public class TermUtils {

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
        imaginary.setCoef(new BNumber(0, term.getCoef().getB()));
        return new ArrayList<Term>() {
            {
                add(real);
                add(imaginary);
            }
        };
    }

    /**
     * A general-use package for adding terms. This package works if the
     * ArrayList<Term> contains varying types of terms.
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
            for (Term i : terms) {
                expandedTerms.addAll(distributeTerm(i));
            }

            for (Term i : expandedTerms) {
                BNumber coef = i.getCoef();

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
        private static final void update(Term term, BNumber newCoef) {
            addedTerms.get(getIndexOf(term)).addToCoef(newCoef);
        }

        ///
        /// Boolean Methods
        ///
        private static final boolean contains(Term term) {
            return getIndexOf(term) != -1;
            // if the index is -1, then the term doesn't exist
        }
    }

    /**
     * A general-use package for multiplying terms. This package works if the
     * ArrayList<Term> contains varying types of terms.
     * 
     * <p>
     * The functionality of this class has been checked on <b>6/15/2025</b> and
     * nothing has changed since.
     */
    public static class MultiplyTerms {
        private static final boolean telemetryOn = Constants.TelemetryConstants.MULTIPLY_TERMS_TELEMETRY;
        /* Variables */
        private static ArrayList<BNumber> powers = new ArrayList<BNumber>();
        private static ArrayList<Term> mTerms = new ArrayList<Term>();
        private static BNumber coef = new BNumber(1, 0);
        private static boolean powerMode = false;

        public static final ArrayList<Term> performMultiply(ArrayList<Term> terms) {
            if (terms == null || terms.size() == 0) {
                return new ArrayList<Term>();
            } else if (terms.size() == 1) {
                return terms.get(0).toTermArray();
            }

            if (!isDataEmpty()) {
                powers = new ArrayList<BNumber>();
                mTerms = new ArrayList<Term>();
                coef = new BNumber(1, 0);
            }

            if (telemetryOn)
                System.out.println("----multiplying " + terms + "----");

            for (Term i : terms) {
                powerMode = false;
                BNumber iPower = new BNumber(1, 0);
                coef.multiply(i.getCoef());
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
                BNumber iPow = powers.get(i);
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

        private static final void update(int index, BNumber power) {
            /* Variables */
            BNumber oldPower = powers.get(index);

            /* Telemetry */
            if (telemetryOn)
                System.out.println("updating " + mTerms.get(index) + " with power of " + oldPower + " by " + power);

            /* Arithmetic */
            oldPower.add(power);
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
}
