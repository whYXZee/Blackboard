package whyxzee.blackboard.terms;

import whyxzee.blackboard.equations.MultiplicativeEQ;
import whyxzee.blackboard.equations.SequentialEQ;
import whyxzee.blackboard.terms.variables.USub;
import whyxzee.blackboard.terms.variables.Variable;
import whyxzee.blackboard.utils.ArithmeticUtils;

/**
 * The package for an exponential term, where the term is constructed as
 * num*base^variable.
 * 
 * <p>
 * The package is constructed as an y=b^x equation.
 * 
 * <p>
 * The methods in this class have been checked on {@code 5/20/2025} and the
 * following has changed:
 * <ul>
 * <li>simplify()
 * <li>condense()
 * <li>equals()
 */
public class ExponentialTerm extends Term {
    /* Variables */
    private double base;

    /**
     * 
     * @param coefficient the constant in front of the exponent
     * @param var         the variable in the exponent
     * @param base        the base of the exponent
     */
    public ExponentialTerm(double coefficient, Variable var, double base) {
        super(coefficient, var, TermType.EXPONENTIAL);
        this.base = base;
    }

    @Override
    public final String toString() {
        /* Decision via Coefficient */
        double coefValue = getCoef();
        if (coefValue == 0) {
            return "0";
        }

        /* Decision via Function */
        String coef = ArithmeticUtils.valueToString(coefValue);
        return coef + "(" + (isBaseE() ? "e" : base) + ")^(" + getVar().toString() + ")";
    }

    @Override
    public final String printConsole() {
        /* Decision via Coefficient */
        double coef = getCoef();
        if (coef == 0) {
            return "0";
        }

        /* Decision via Function */
        boolean isBaseE = base == Math.E;
        if (coef == 1) {
            return (isBaseE ? "e" : base) + "^(" + getVar().printConsole() + ")";
        } else {
            return Double.toString(coef) + "(" + (isBaseE ? "e" : base) + ")^(" + getVar().printConsole() + ")";
        }
    }

    @Override
    public final void condense() {
        /* Condensing variables with like coefs and bases */
        int numOfBase = 0; // n * base = coef
        while (ArithmeticUtils.isInteger(getCoef() / getBase())) {
            numOfBase++;
            setCoef(getCoef() / getBase());
        }

        if (numOfBase > 0) {
            setCoef(1);
            Variable var = getVar();

            /* Changing variable */
            switch (var.getVarType()) {
                case VARIABLE:
                    setVar(new USub(new SequentialEQ(
                            new PowerTerm(1, var),
                            new PowerTerm(numOfBase))));
                    break;
                case U_SUB_EQ:
                    SequentialEQ eq = (SequentialEQ) var.getInnerFunction();
                    eq.addPowerTerm(new PowerTerm(numOfBase));
                    setVar(new USub(eq));
                    break;
                case U_SUB_TERM:
                    setVar(new USub(new SequentialEQ(
                            var.getInnerTerm(),
                            new PowerTerm(numOfBase))));
                    break;
                default:
                    break;
            }
        }
    }

    // public final Term condense() {
    // /* Initializing variables */
    // Variable var = getVar();

    // /* Condensing Algorithm */
    // switch (var.getVarType()) {
    // case U_SUB_TERM:
    // if (var.getInnerTerm().getTermType() == TermType.LOGARITHMIC) {
    // LogarithmicTerm innerTerm = (LogarithmicTerm) var.getInnerTerm();
    // if (innerTerm.getBase() == base) {
    // return new PolynomialTerm(1, innerTerm.getVar());
    // }
    // }
    // default:
    // return this;
    // }
    // }

    //
    // Get and Set Methods
    //
    public final double getBase() {
        return base;
    }

    public final void setBase(double base) {
        this.base = base;
    }

    //
    // Arithmetic Methods (Object-related)
    //
    @Override
    public double solve(double value) {
        return getCoef() * Math.pow(base, getVar().solve(value));
    }

    @Override
    public final Term negate() {
        return new ExponentialTerm(-1 * getCoef(), getVar(), base);
    }

    @Override
    public final Term derive() {
        /* Initiating variables */
        double coef = getCoef();
        Variable variable = getVar().clone();

        /* The coefficient */
        if (base != Math.E) {
            coef *= Math.log(base);
        }

        /* The variable */
        if (variable.needsChainRule()) {
            // chain rule
            MultiplicativeEQ eq = new MultiplicativeEQ(
                    // outer function (b^x)
                    new ExponentialTerm(1, variable, base),

                    // inner function (x)
                    variable.derive());

            return new PowerTerm(coef, new USub(eq), 1);
        } else {
            // no chain rule
            return new ExponentialTerm(coef, variable, base);
        }
    }

    @Override
    public final double limInfSolve() {
        /* Without respect to the variable */

        /* Number */
        double coef = getCoef();
        if (coef == 0) {
            return 0;
        }
        boolean isNumberNegative = coef < 0;

        /* Function */
        if (base < 1) {
            // -1 < b < 1 so converging
            return isNumberNegative ? -0 : 0;
        } else if (base == 1) {
            // b = 1
            return coef;
        } else if (base >= -1) {
            // negative base less than than or equal to -1
            // alternating
            return Double.NaN;
        } else {
            return isNumberNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        }
    }

    public final double limNegInfSolve() {
        /* Number */
        double coef = getCoef();
        if (coef == 0) {
            return 0;
        }
        boolean isNumberNegative = coef < 0;

        if (base < 1) {
            // -1 < b < 1
            return isNumberNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        } else if (base == 1) {
            // b = 1
            return coef;
        } else if (base > -1) {
            // negative base less than to -1
            // converging alternating
            return isNumberNegative ? -0 : 0;
        } else {
            // negative base greater than or equal to -1
            return Double.NaN;
        }
    }

    //
    // Boolean Methods
    //
    @Override
    public final boolean similarTo(Term term) {
        switch (term.getTermType()) {
            case EXPONENTIAL:
                ExponentialTerm expTerm = (ExponentialTerm) term;
                return (base == expTerm.getBase()) &&
                        (getVar().equals(expTerm.getVar()));
            default:
                return false;
        }
    }

    @Override
    public final boolean equals(Term other) {
        if ((other.getTermType() == TermType.EXPONENTIAL) && (getCoef() == other.getCoef())) {
            ExponentialTerm expTerm = (ExponentialTerm) other;
            return (base == expTerm.getBase()) &&
                    (getVar().equals(expTerm.getVar()));
        }
        return false;
    }

    public final boolean isBaseE() {
        return base == Math.E;
    }
}
