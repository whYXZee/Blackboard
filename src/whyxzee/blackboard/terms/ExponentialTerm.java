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
        double coef = getCoefficient();
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
        double coef = getCoefficient();
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

    public final void simplify() {
        int numOfBase = 0; // n * base = coef
        while (ArithmeticUtils.isInteger(getCoefficient() / getBase())) {
            numOfBase++;
            setCoefficient(getCoefficient() / getBase());
        }

        if (numOfBase > 0) {
            setCoefficient(1);
            Variable var = getVar();

            /* Changing variable */
            switch (var.getVarType()) {
                case VARIABLE:
                    setVar(new USub(new EQSequence(
                            new PolynomialTerm(1, var),
                            new PolynomialTerm(numOfBase))));
                    break;
                case U_SUB_EQ:
                    EQSequence eq = (EQSequence) var.getInnerFunction();
                    eq.addPolynomialTerm(new PolynomialTerm(numOfBase));
                    setVar(new USub(eq));
                    break;
                case U_SUB_TERM:
                    setVar(new USub(new EQSequence(
                            var.getInnerTerm(),
                            new PolynomialTerm(numOfBase))));
                    break;
                default:
                    break;
            }
        }
    }

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
    // Arithmetic Methods (Static)
    //

    public static Term add(ArrayList<Term> terms) {
        /* Initializing variables */
        ExponentialData expData = new ExponentialData();

        /* Addition algorithm */
        for (int i = 0; i < terms.size(); i++) {
            ExponentialTerm term = (ExponentialTerm) terms.get(i);
            double coef = term.getCoefficient();
            Variable var = term.getVar();
            double base = term.getBase();

            if (expData.containsTerm(term)) {
                // term in data
                ExponentialTerm oldTerm = expData.get(term);
                expData.update(base, coef + oldTerm.getCoefficient(), var);
            } else {
                // term not in data
                expData.add(term);
            }
        }

        expData.simplifyTerms();

        if (expData.size() > 1) {
            EQSequence eq = new EQSequence(expData.getTermArray());
            return new PolynomialTerm(1, new USub(eq));
        } else if (expData.size() == 1) {
            return expData.getExpTerm(0);
        }

        return null;
    }

    /**
     * Multiplies the terms together based on the base. Can be used to simplify
     * ExponentialTerms in a multiplicative equation.
     * 
     * @param terms
     * @return
     */
    public static Term multiply(ArrayList<Term> terms) {
        /* Initializing variables */
        ExponentialData expData = new ExponentialData();

        /* Simplifying algorithm */
        for (int i = 0; i < terms.size(); i++) {
            ExponentialTerm term = (ExponentialTerm) terms.get(i);
            double coef = term.getCoefficient();
            double base = term.getBase();
            Variable var = term.getVar();

            if (expData.containsBase(base)) {
                /* Old Term */
                ExponentialTerm oldTerm = expData.get(base);
                Variable oldVar = oldTerm.getVar();
                VarType oldVarType = oldVar.getVarType();

                /* Added Term */
                PolynomialTerm newVar = new PolynomialTerm(1, var);
                if (var.getVarType() == VarType.U_SUB_TERM) {
                    if (var.getInnerTerm().getTermType() == TermType.POLYNOMIAL) {
                        newVar = (PolynomialTerm) var.getInnerTerm();
                    }
                }

                EQSequence innerEQ;
                switch (oldVarType) {
                    case VARIABLE:
                        if (var.getVarType() == VarType.U_SUB_EQ) {
                            innerEQ = (EQSequence) var.getInnerFunction();
                            innerEQ.addPolynomialTerm(new PolynomialTerm(1, oldVar));
                        } else {
                            innerEQ = new EQSequence(
                                    new PolynomialTerm(1, oldVar),
                                    newVar);
                        }
                        break;
                    case U_SUB_EQ:
                        if (var.getVarType() == VarType.U_SUB_EQ) {
                            innerEQ = (EQSequence) oldVar.getInnerFunction();
                            innerEQ.merge(var.getInnerFunction());
                        } else {
                            innerEQ = (EQSequence) oldVar.getInnerFunction();
                            innerEQ.addPolynomialTerm(newVar);
                        }
                        break;
                    case U_SUB_TERM:
                        if (var.getVarType() == VarType.U_SUB_EQ) {
                            innerEQ = (EQSequence) var.getInnerFunction();
                            innerEQ.addTerm(oldVar.getInnerTerm());
                        } else {
                            innerEQ = new EQSequence(
                                    oldVar.getInnerTerm(),
                                    newVar);
                        }
                        break;
                    default:
                        innerEQ = new EQSequence();
                        break;
                }
                expData.update(base, coef * oldTerm.getCoefficient(), new USub(innerEQ));
            } else {
                expData.add(term);
            }
        }

        expData.simplifyVar();

        if (expData.size() > 1) {
            EQMultiplication eq = new EQMultiplication(expData.getTermArray());
            return new PolynomialTerm(1, new USub(eq));
        } else if (expData.size() == 1) {
            return expData.getExpTerm(0);
        }

        return null;
    }

    //
    // Arithmetic Methods (Object-related)
    //
    @Override
    public double solve(double value) {
        return getCoefficient() * Math.pow(base, getVar().solve(value));
    }

    @Override
    public Term negate() {
        return new ExponentialTerm(-1 * getCoefficient(), getVar(), base);
    }

    @Override
    public Term derive() {
        /* Initiating variables */
        double coef = getCoefficient();
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

            return new PolynomialTerm(coef, new USub(eq), 1);
        } else {
            // no chain rule
            return new ExponentialTerm(coef, variable, base);
        }
    }

    @Override
    public double limInfSolve() {
        /* Without respect to the variable */

        /* Number */
        double coef = getCoefficient();
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
        double coef = getCoefficient();
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

    static class ExponentialData {
        /* Variables */
        private ArrayList<ExponentialTerm> expTerms;

        public ExponentialData() {
            expTerms = new ArrayList<ExponentialTerm>();
        }

        public void simplifyVar() {
            for (ExponentialTerm i : expTerms) {
                Variable var = i.getVar();
                if (var.getVarType() == VarType.U_SUB_EQ) {
                    var.getInnerFunction().simplify();
                }
            }
        }

        public void simplifyTerms() {
            for (ExponentialTerm i : expTerms) {
                i.simplify();
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
