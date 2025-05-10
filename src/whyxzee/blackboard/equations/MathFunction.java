package whyxzee.blackboard.equations;

import java.util.ArrayList;

import whyxzee.blackboard.terms.Term;

/**
 * The highest-class for
 */
public abstract class MathFunction {
    /* Terms */
    private Term[] termArray;
    private ArrayList<Term> polynomialTerms = new ArrayList<Term>();
    private ArrayList<Term> integralTerms = new ArrayList<Term>();
    private ArrayList<Term> exponentialTerms = new ArrayList<Term>();
    private ArrayList<Term> logTerms = new ArrayList<Term>();
    private ArrayList<Term> trigTerms = new ArrayList<Term>();

    public MathFunction(Term... terms) {
        termArray = terms;
        sortTerms();
    }

    public abstract void simplify();

    /**
     * Sorts terms into separate ArrayList<Term> for easier simplification and
     * organization.
     */
    public void sortTerms() {
        for (Term term : getTermArray()) {
            switch (term.getTermType()) {
                case POLYNOMIAL:
                    addPolynomialTerm(term);
                    break;
                case INTEGRAL:
                    addIntegralTerm(term);
                    break;
                case EXPONENTIAL:
                    addExponentialTerm(term);
                    break;
                case LOGARITHMIC:
                    addLogTerm(term);
                    break;
                case TRIGONOMETRIC:
                    addTrigTerm(term);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Solves the function with an input.
     * 
     * @param value
     * @return
     */
    public abstract double solve(double value);

    /**
     * 
     * @param degree how many times the derivative should be taken.
     * @return
     */
    public abstract MathFunction derive();

    //
    // Get & Set Methods
    //
    public Term[] getTermArray() {
        return termArray;
    }

    public void setTermArray(Term... terms) {
        termArray = terms;
    }

    /**
     * Augments the {@code polynomialTerms} ArrayList.
     * 
     * @param term
     */
    public void addPolynomialTerm(Term term) {
        polynomialTerms.add(term);
    }

    /**
     * Augments the {@code integralTerms} ArrayList.
     * 
     * @param term
     */
    public void addIntegralTerm(Term term) {
        integralTerms.add(term);
    }

    /**
     * Augments the {@code exponentialTerms} ArrayList.
     * 
     * @param term
     */
    public void addExponentialTerm(Term term) {
        exponentialTerms.add(term);
    }

    /**
     * Augments the {@code logTerms} ArrayList.
     * 
     * @param term
     */
    public void addLogTerm(Term term) {
        logTerms.add(term);
    }

    /**
     * Augments the {@code trigTerms} ArrayList.
     * 
     * @param term
     */
    public void addTrigTerm(Term term) {
        trigTerms.add(term);
    }
}
