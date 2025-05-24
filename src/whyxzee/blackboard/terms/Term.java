package whyxzee.blackboard.terms;

import java.util.ArrayList;

import whyxzee.blackboard.terms.variables.Variable;

/**
 * The abstract class which all term types inherit from.
 */
public abstract class Term {
    /* Logic */
    private TermType termType;

    /* Outer Variables */
    private double coefficient;
    private Variable var;

    public static enum TermType {
        POLYNOMIAL,
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
        this.coefficient = coefficient;
        this.var = var;
        this.termType = termType;
    }

    public abstract String printConsole();

    //
    // Get & Set Methods
    //
    public final double getCoefficient() {
        return coefficient;
    }

    public final void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
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
    // Boolean Methods
    //
    public boolean isNegative() {
        return 0 > coefficient;
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
}
