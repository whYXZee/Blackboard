package whyxzee.blackboard.terms;

import java.util.ArrayList;

import whyxzee.blackboard.equations.EQMultiplication;
import whyxzee.blackboard.equations.EQSequence;
import whyxzee.blackboard.terms.variables.USub;
import whyxzee.blackboard.terms.variables.Variable;
import whyxzee.blackboard.terms.variables.Variable.VarType;
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
    public String toString() {
        /* Decision via Coefficient */
        double coef = getCoef();
        if (coef == 0) {
            return "0";
        }

        /* Decision via Function */
        boolean isBaseE = base == Math.E;
        if (coef == 1) {
            return (isBaseE ? "e" : base) + "^(" + getVar().toString() + ")";
        } else {
            return Double.toString(coef) + "(" + (isBaseE ? "e" : base) + ")^(" + getVar().toString() + ")";
        }
    }

    @Override
    public String printConsole() {
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
                    setVar(new USub(new EQSequence(
                            new PowerTerm(1, var),
                            new PowerTerm(numOfBase))));
                    break;
                case U_SUB_EQ:
                    EQSequence eq = (EQSequence) var.getInnerFunction();
                    eq.addPowerTerm(new PowerTerm(numOfBase));
                    setVar(new USub(eq));
                    break;
                case U_SUB_TERM:
                    setVar(new USub(new EQSequence(
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
    public Term negate() {
        return new ExponentialTerm(-1 * getCoef(), getVar(), base);
    }

    @Override
    public Term derive() {
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
            EQMultiplication eq = new EQMultiplication(
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
    public double limInfSolve() {
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

    public double limNegInfSolve() {
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
    public boolean similarTo(Term term) {
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
    public boolean equals(Term other) {
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

    static class ExponentialData {
        /* Variables */
        private ArrayList<ExponentialTerm> expTerms;

        public ExponentialData() {
            expTerms = new ArrayList<ExponentialTerm>();
        }

        /**
         * Simplifies the variable in exponential terms in order to have the simplest
         * equation.
         */
        public void simplifyVar() {
            for (ExponentialTerm i : expTerms) {
                Variable var = i.getVar();
                if (var.getVarType() == VarType.U_SUB_EQ) {
                    var.getInnerFunction().simplify();
                }
            }
        }

        /**
         * Simplifies terms so that if the following occurs: b(b)^x, then it simplifies
         * to b^(x+1)
         */
        public void simplifyTerms() {
            for (ExponentialTerm i : expTerms) {
                i.condense();
            }
        }

        //
        // Get & Set Methods
        //
        public ArrayList<Term> getTermArray() {
            return new ArrayList<Term>(expTerms);
        }

        public void add(ExponentialTerm term) {
            expTerms.add(term);
        }

        public void update(double base, double coefficient, Variable var) {
            expTerms.set(getIndexOf(base), new ExponentialTerm(coefficient, var, base));
        }

        public void update(ExponentialTerm term, double coefficient) {
            expTerms.set(getIndexOf(term), new ExponentialTerm(coefficient, term.getVar(), term.getBase()));
        }

        public ExponentialTerm getExpTerm(int index) {
            return expTerms.get(index);
        }

        public ExponentialTerm get(double base) {
            return getExpTerm(getIndexOf(base));
        }

        public ExponentialTerm get(ExponentialTerm term) {
            return getExpTerm(getIndexOf(term));
        }

        public int getIndexOf(double base) {
            for (int i = 0; i < expTerms.size(); i++) {
                ExponentialTerm term = expTerms.get(i);
                if (term.getBase() == base) {
                    return i;
                }
            }
            return -1;
        }

        public int getIndexOf(ExponentialTerm term) {
            for (int i = 0; i < expTerms.size(); i++) {
                if (expTerms.get(i).similarTo(term)) {
                    return i;
                }
            }
            return -1;
        }

        public int size() {
            return expTerms.size();
        }

        //
        // Boolean Methods
        //
        public boolean containsBase(double base) {
            for (ExponentialTerm i : expTerms) {
                if (i.getBase() == base) {
                    return true;
                }
            }
            return false;
        }

        public boolean containsTerm(ExponentialTerm term) {
            for (ExponentialTerm i : expTerms) {
                if (i.similarTo(term)) {
                    return true;
                }
            }
            return false;
        }
    }
}
