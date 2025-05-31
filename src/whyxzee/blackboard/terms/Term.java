package whyxzee.blackboard.terms;

import whyxzee.blackboard.terms.variables.Variable;

/**
 * The abstract class which all term types inherit from.
 */
public abstract class Term {
    /* Logic */
    private TermType termType;

    /* Outer Variables */
    private double coef;
    private Variable var;

    public static enum TermType {
        POWER,
        TRIGONOMETRIC,
        EXPONENTIAL,
        LOGARITHMIC,
        ABSOLUTE_VALUE,

        /* Niche */
        FACTORIAL,
        SIGNUM
        // DERIVATIVE, // save for multivariate cuz implicit
    }

    /**
     * 
     * @param coefficient The constant outside the polynomial, trig, etc.
     * @param var         The variable of the term; inside the polynomial, trig
     *                    function, etc.
     * @param termType
     */
    public Term(double coefficient, Variable var, TermType termType) {
        this.coef = coefficient;
        this.var = var;
        this.termType = termType;
    }

    public abstract String printConsole();

    /**
     * Condenses the term.
     */
    public void condense() {
    }

    //
    // Get & Set Methods
    //
    public final double getCoef() {
        return coef;
    }

    public final void setCoef(double coefficient) {
        this.coef = coefficient;
    }

    /**
     * 
     * @param addend the number that should be added to the coefficient
     */
    public final void addToCoef(double addend) {
        coef += addend;
    }

    public final void multiplyCoefBy(double factor) {
        coef *= factor;
    }

    public final TermType getTermType() {
        return termType;
    }

    public final void setTermType(TermType termType) {
        this.termType = termType;
    }

    public final Variable getVar() {
        return var;
    }

    public final void setVar(Variable var) {
        this.var = var;
    }

    //
    // Arithmetic Methods
    //

    /**
     * Inputs a value into the polynomial, as if it was a function.
     * 
     * @param value the value of the variable
     * @return
     */
    public abstract double solve(double value);

    /**
     * Multiplies the number by -1, negating it.
     * 
     * @return the negated term
     */
    public abstract Term negate();

    /**
     * Derive the term once.
     * 
     * @return
     */
    public abstract Term derive();

    /**
     * Evaluates a function at the limit as the variable approaches infinity.
     */
    public abstract double limInfSolve();

    public abstract double limNegInfSolve();

    //
    // Boolean Methods
    //
    /**
     * If a term is similar to another term (meaning the two can are like terms and
     * can be combined)
     * 
     * @param term the other term
     * @return {@code true} if the two terms are alike, {@code false} if otherwise.
     * 
     */
    public final boolean isTermType(TermType termType) {
        return this.termType == termType;
    }

    public abstract boolean similarTo(Term term);

    public abstract boolean equals(Term other);

    public final boolean isNegative() {
        return 0 > coef;
    }
}
