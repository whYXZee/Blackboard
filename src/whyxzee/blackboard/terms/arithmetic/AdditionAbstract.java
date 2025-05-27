package whyxzee.blackboard.terms.arithmetic;

import java.util.ArrayList;

import whyxzee.blackboard.terms.Term;

/**
 * A general-use package for adding terms. This package works if the
 * ArrayList<Term> contains varying types of terms.
 * 
 * <p>
 * The functionality of this class has been checked on {@code 5/27/2025} and
 * nothing has changed since.
 */
public class AdditionAbstract {
    /* Variables */
    private ArrayList<Term> addedTerms;

    public AdditionAbstract() {
        addedTerms = new ArrayList<Term>();
    }

    public final ArrayList<Term> performAddition(ArrayList<Term> terms) {
        if (terms.size() == 0) {
            return terms;
        }

        if (!areAddedTermsEmpty()) {
            addedTerms.clear();
        }

        /* Algorithm */
        for (Term i : terms) {
            double coef = i.getCoef();

            if (contains(i)) {
                update(i, coef);
            } else {
                add(i);
            }
        }

        return addedTerms;
    }

    //
    // Get & Set Methods
    //
    private int getIndexOf(Term term) {
        int index = 0;

        for (Term i : addedTerms) {
            if (i.similarTo(term)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    private void add(Term term) {
        addedTerms.add(term);
    }

    /**
     * Updates a term to have a new coefficient.
     * 
     * @param term   the term that should be changed
     * @param addend how much should be added to the coefficient
     */
    private void update(Term term, double newCoef) {
        addedTerms.get(getIndexOf(term)).addToCoef(newCoef);
    }

    //
    // Boolean Methods
    //
    private boolean contains(Term term) {
        return getIndexOf(term) != -1;
        // if the index is -1, then the term doesn't exist
    }

    private boolean areAddedTermsEmpty() {
        return addedTerms.size() == 0;
    }
}
