package whyxzee.blackboard.math.pure.terms;

import java.util.ArrayList;

import whyxzee.blackboard.math.pure.numbers.BNumber;

public class TermUtils {

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
            for (Term i : terms) {
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
            return addedTerms;
        }

        ///
        /// Get & Set Methods
        ///
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

}
