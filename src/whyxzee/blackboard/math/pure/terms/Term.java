package whyxzee.blackboard.math.pure.terms;

import java.util.ArrayList;

import whyxzee.blackboard.math.pure.numbers.BNumber;
import whyxzee.blackboard.math.pure.terms.variables.Variable;

/**
 * The abstract class which all term types inherit from.
 */
public abstract class Term {
    /* Logic */
    private TermType termType;

    /* Outer Variables */
    private BNumber coef;
    private Variable var;

    public static enum TermType {
        POWER,
        TRIGONOMETRIC,
        EXPONENTIAL,
        LOGARITHMIC,
        ABSOLUTE_VALUE,
        PLUS_MINUS,

        /* Niche */
        FACTORIAL,
        SIGNUM
    }

    /**
     * 
     * @param coef     The real constant outside the polynomial, trig, etc.
     * @param var      The variable of the term; inside the polynomial, trig
     *                 function, etc.
     * @param termType
     */
    public Term(double coef, Variable var, TermType termType) {
        this.coef = new BNumber(coef, 0);
        this.var = var;
        this.termType = termType;
    }

    public Term(BNumber coef, Variable var, TermType termType) {
        this.coef = coef;
        this.var = var;
        this.termType = termType;
    }

    public abstract Term clone();

    public final ArrayList<Term> toTermArray() {
        ArrayList<Term> output = new ArrayList<Term>();
        output.add(this);
        return output;
    }

    //
    // Get & Set Methods
    //
    // #region
    public final BNumber getCoef() {
        return coef;
    }

    /**
     * Sets the coefficient to a real number.
     * 
     * @param coef the real number
     */
    public final void setCoef(double coef) {
        this.coef = new BNumber(coef, 0);
    }

    public final void setCoef(BNumber coef) {
        this.coef = coef;
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
    // #endregion

    //
    // Arithmetic Methods
    //

    /**
     * Adds the value to the coefficient.
     * 
     * @param value
     */
    public final void addToCoef(BNumber value) {
        coef.add(value);
    }

    public final void divideCoefBy(BNumber value) {
        coef.divide(value);
    }

    public abstract Term negate();

    /**
     * Inputs a value into the term, as if it was a function.
     * 
     * @param value the value of the variable
     * @return
     */
    public abstract BNumber solve(BNumber value);

    /**
     * Inputs a real number into the term, as if it was a function.
     * 
     * @param value
     * @return
     */
    public final BNumber solve(double value) {
        return solve(new BNumber(value, 0));
    }

    //
    // Boolean Methods
    //
    /*
     * Checks if the term contains the var.
     */
    public final boolean containsVar(String var) {
        return this.var.containsVar(var);
    }

    public final boolean isTermType(TermType termType) {
        return this.termType == termType;
    }

    /**
     * If a term is similar to another term (meaning the two can are like terms and
     * can be combined)
     * 
     * @param term the other term
     * @return <b>true</b> if the two terms are alike, <b>false</b> if otherwise.
     * 
     */
    public abstract boolean similarTo(Term term);

    public final boolean equals(Term other) {
        if (!similarTo(other)) {
            return false;
        }
        return getCoef().equals(other.getCoef());
    }
}
