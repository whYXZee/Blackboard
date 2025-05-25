package whyxzee.blackboard.equations;

import java.util.ArrayList;

import whyxzee.blackboard.terms.Term;

/**
 * The highest-class for generic math functions.
 */
public abstract class MathFunction {
    /* Terms */
    // private Term[] termArray;
    private ArrayList<Term> termArray = new ArrayList<Term>();
    private ArrayList<Term> polynomialTerms = new ArrayList<Term>();
    private ArrayList<Term> exponentialTerms = new ArrayList<Term>();
    private ArrayList<Term> logTerms = new ArrayList<Term>();
    private ArrayList<Term> trigTerms = new ArrayList<Term>();
    private ArrayList<Term> factorialTerms = new ArrayList<Term>();
    private ArrayList<Term> signumTerms = new ArrayList<Term>();

    /* Function Identification */
    private FunctionType functionType;

    public enum FunctionType {
        SEQUENCE,
        MULTIPLICATION
    }

    public MathFunction(FunctionType functionType, Term... terms) {
        for (Term i : terms) {
            termArray.add(i);
        }
        sortTerms();

        /* Identification */
        this.functionType = functionType;
    }

    public MathFunction(FunctionType functionType, ArrayList<Term> terms) {
        /* Transfer terms from ArrayList to array */
        termArray = terms;
        sortTerms();

        /* Identification */
        this.functionType = functionType;
    }

    public abstract String toString();

    public abstract String printConsole();

    public abstract void simplify();

    /**
     * Sorts terms into separate ArrayList<Term> for easier simplification and
     * organization.
     */
    public final void sortTerms() {
        polynomialTerms = new ArrayList<Term>();
        exponentialTerms = new ArrayList<Term>();
        logTerms = new ArrayList<Term>();
        trigTerms = new ArrayList<Term>();
        factorialTerms = new ArrayList<Term>();
        signumTerms = new ArrayList<Term>();

        for (Term term : getTermArray()) {
            addTerm(term);
        }

        organizeTerms();
    }

    public final void organizeTerms() {
        /* Polynomial Term */
        // sorted by greatest power -> lowest power
        // TODO sorting algorithm for terms

    }

    public abstract void merge(MathFunction function);

    //
    // Arithmetic Functions
    //

    /**
     * Solves the function with an input.
     * 
     * @param value
     * @return
     */
    public abstract double solve(double value);

    /**
     * 
     * @return
     */
    public abstract MathFunction derive();

    //
    // Get & Set Methods
    //
    public final ArrayList<Term> getTermArray() {
        return termArray;
    }

    public final void setTermArray(Term... terms) {
        ArrayList<Term> newTermArray = new ArrayList<>();
        for (Term i : terms) {
            newTermArray.add(i);
        }
        termArray = newTermArray;
        sortTerms();
    }

    public final void setTermArray(ArrayList<Term> terms) {
        termArray = terms;
        sortTerms();
    }

    public final void addToTermArray(ArrayList<Term> terms) {
        termArray.addAll(terms);
        sortTerms();
    }

    public final FunctionType getFunctionType() {
        return functionType;
    }

    /**
     * Augments the {@code polynomialTerms} ArrayList.
     * 
     * @param term
     */
    public final void addPolynomialTerm(Term term) {
        polynomialTerms.add(term);
    }

    public final ArrayList<Term> getPolynomialTerms() {
        return polynomialTerms;
    }

    /**
     * Augments the {@code exponentialTerms} ArrayList.
     * 
     * @param term
     */
    public final void addExponentialTerm(Term term) {
        exponentialTerms.add(term);
    }

    public final ArrayList<Term> getExponentialTerms() {
        return exponentialTerms;
    }

    /**
     * Augments the {@code logTerms} ArrayList.
     * 
     * @param term
     */
    public final void addLogTerm(Term term) {
        logTerms.add(term);
    }

    /**
     * Augments the {@code trigTerms} ArrayList.
     * 
     * @param term
     */
    public final void addTrigTerm(Term term) {
        trigTerms.add(term);
    }

    /**
     * Augments the {@code factorialTerms} ArrayList.
     * 
     * @param term
     */
    public final void addFactorialTerm(Term term) {
        factorialTerms.add(term);
    }

    /**
     * Augments the {@code signumTerms} ArrayList.
     * 
     * @param term
     */
    public final void addSignumTerms(Term term) {
        signumTerms.add(term);
    }

    /**
     * Augments the terms, and then sorts them.
     */
    public final void addTerm(Term term) {
        switch (term.getTermType()) {
            case POLYNOMIAL:
                addPolynomialTerm(term);
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

            /* Niche */
            case FACTORIAL:
                addFactorialTerm(term);
                break;
            case SIGNUM:
                addSignumTerms(term);
                break;
            default:
                break;
        }
    }

    //
    // Boolean Methods
    //
    public final boolean equals(MathFunction other) {
        if (functionType == other.getFunctionType()) {
            return termArray == other.getTermArray();
        }
        return false;
    }
}
