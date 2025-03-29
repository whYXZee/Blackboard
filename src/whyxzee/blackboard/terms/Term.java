package whyxzee.blackboard.terms;

/**
 * The abstract class which all term types inherit from.
 */
public abstract class Term {
    private TermType termType;
    //
    // Outer Variables
    //
    private double num;

    //
    // Inner Variables
    //
    Variable var;

    public static enum TermType {
        POLYNOMIAL,
        TRIGONOMETRIC,
        EXPONENTIAL,
        LOGARITHMIC,
        INTEGRAL,
        // DERIVATIVE, // save for multivariate cuz implicit
    }

    /**
     * 
     * @param num      The constant outside the polynomial, trig, etc.
     * @param var      The variable of the term; inside the polynomial, trig
     *                 function, etc.
     * @param termType
     */
    public Term(double num, Variable var, TermType termType) {
        this.num = num;
        this.var = var;
        this.termType = termType;
    }

    //
    // Get & Set Methods
    //
    public double getNum() {
        return num;
    }

    public TermType getTermType() {
        return termType;
    };

    public Variable getVar() {
        return var;
    }

    //
    // Boolean Methods
    //
    public boolean isNegative() {
        return 0 > num;
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
     * 
     * @param degree The amount of times the derivative should occur.
     * @return
     */
    public abstract Term derive(int degree);
}
