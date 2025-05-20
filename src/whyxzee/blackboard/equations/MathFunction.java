package whyxzee.blackboard.equations;

import java.util.ArrayList;

import whyxzee.blackboard.terms.Term;

/**
 * The highest-class for generic math functions.
 */
public abstract class MathFunction {
    /* Terms */
    private Term[] termArray;
    private ArrayList<Term> polynomialTerms = new ArrayList<Term>();
    private ArrayList<Term> exponentialTerms = new ArrayList<Term>();
    private ArrayList<Term> logTerms = new ArrayList<Term>();
    private ArrayList<Term> trigTerms = new ArrayList<Term>();

    /* Function Identification */
    private FunctionType functionType;

    public enum FunctionType {
        SEQUENCE,
        MULTIPLICATION
    }

    public MathFunction(FunctionType functionType, Term... terms) {
        termArray = terms;
        sortTerms();

        /* Identification */
        this.functionType = functionType;
    }

    public MathFunction(FunctionType functionType, ArrayList<Term> terms) {
        /* Transfer terms from ArrayList to array */
        Term[] tempTermArray = new Term[terms.size()];
        for (int i = 0; i < terms.size(); i++) {
            tempTermArray[i] = terms.get(i);
        }
        termArray = tempTermArray;
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
        for (Term term : getTermArray()) {
            addTerm(term);
        }
    }

    public final void organizeTerms() {
        // TODO sorting algorithm for terms

    }

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
    public final Term[] getTermArray() {
        return termArray;
    }

    public final void setTermArray(Term... terms) {
        termArray = terms;
        sortTerms();
    }

    public final void createTermArray() {
        int totalSize = polynomialTerms.size() + exponentialTerms.size() + logTerms.size() + trigTerms.size();
        Term[] array = new Term[totalSize];
        int index = 0;

        /* Ordering */
        for (Term i : exponentialTerms) {
            array[index] = i;
            index++;
        }
        for (Term i : polynomialTerms) {
            array[index] = i;
            index++;
        }
        for (Term i : logTerms) {
            array[index] = i;
            index++;
        }
        for (Term i : trigTerms) {
            array[index] = i;
            index++;
        }
        setTermArray(array);
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

    /**
     * Augments the {@code exponentialTerms} ArrayList.
     * 
     * @param term
     */
    public final void addExponentialTerm(Term term) {
        exponentialTerms.add(term);
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
