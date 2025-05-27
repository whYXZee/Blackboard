package whyxzee.blackboard.terms;

import java.util.ArrayList;

import whyxzee.blackboard.equations.EQMultiplication;
import whyxzee.blackboard.equations.EQSequence;
import whyxzee.blackboard.terms.variables.USub;
import whyxzee.blackboard.terms.variables.Variable;
import whyxzee.blackboard.utils.ArithmeticUtils;
import whyxzee.blackboard.utils.UnicodeUtils;

/**
 * The package for a logarithmic term, where the term is constructed as
 * log_b(variable)
 * 
 * <p>
 * The package is constructed as a y=log_b(x) equation.
 * 
 * <p>
 * The methods in this class has been checked on {@code 5/20/2025} and the
 * following has changed:
 * <ul>
 * <li>equals()
 */
public class LogarithmicTerm extends Term {
    /* Variables */
    private double base;

    public LogarithmicTerm(double coefficient, Variable var, double base) {
        super(coefficient, var, TermType.LOGARITHMIC);
        this.base = base;
    }

    @Override
    public String toString() {
        /* Initializing variables */
        double coef = getCoef();

        if (base == Math.E) {
            // natural log

            /* Coefficient */
            if (coef == 0) {
                return "0";
            } else if (coef == 1) {
                return "ln(" + getVar().toString() + ")";
            } else {
                return Double.toString(coef) + "ln(" + getVar().toString() + ")";
            }
        } else {
            // base shouldn't be int
            if (coef == 0) {
                return "0";
            }

            /* Base */
            boolean hasIntegerBase = ArithmeticUtils.isInteger(base);
            if (coef == 1) {
                return "log" + (hasIntegerBase ? UnicodeUtils.intToSubscript((int) base)
                        : UnicodeUtils.doubleToSubscript(base)) + "(" + getVar().toString() + ")";
            } else {
                return Double.toString(coef) + "log" + (hasIntegerBase ? UnicodeUtils.intToSubscript((int) base)
                        : UnicodeUtils.doubleToSubscript(base)) + "(" + getVar().toString() + ")";
            }
        }
    }

    @Override
    public String printConsole() {
        /* Initializing variables */
        double coef = getCoef();

        if (base == Math.E) {
            // natural log

            /* Coefficient */
            if (coef == 0) {
                return "0";
            } else if (coef == 1) {
                return "ln(" + getVar().toString() + ")";
            } else {
                return Double.toString(coef) + "ln(" + getVar().toString() + ")";
            }
        } else {
            // numeric base

            /* Coefficient */
            if (coef == 0) {
                return "0";
            } else if (coef == 1) {

                return "log_" + base + "(" + getVar().toString() + ")";
            } else {
                return Double.toString(coef) + "log_" + base + "(" + getVar().toString() + ")";
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

    public static Term multiply(ArrayList<Term> terms) {
        /* Initializing variables */
        LogMultiplication logMulti = new LogMultiplication();

        /* Addition algorithm */
        for (int i = 0; i < terms.size(); i++) {
            LogarithmicTerm term = (LogarithmicTerm) terms.get(i);
            double coef = term.getCoef();

            if (logMulti.containsTerm(term)) {
                // term in data
                LogarithmicTerm oldTerm = logMulti.getLog(term);
                logMulti.update(term, coef + oldTerm.getCoef(), 1);
            } else {
                // term not in data
                logMulti.add(term, 1);
            }
        }

        return logMulti.exportTerm();
    }

    //
    // Arithmetic Methods (Object-related)
    //
    @Override
    public double solve(double value) {
        /* Initializing variables */
        double number = getCoef();

        /* Decision based on Base */
        if (base == 10) {
            // log10 operation
            return number * Math.log10(getVar().solve(value));
        } else if (base == Math.E) {
            // ln operation
            return number * Math.log(getVar().solve(value));
        } else {
            // change of base formula
            return number * (Math.log(getVar().solve(value)) / Math.log(base));
        }
    }

    @Override
    public Term negate() {
        return new LogarithmicTerm(-1 * getCoef(), getVar(), base);
    }

    @Override
    public Term derive() {
        /* Initializing terms */
        double coef = getCoef();
        Variable variable = getVar();

        if (getVar().needsChainRule()) {
            /* Chain rule */
            if (base == Math.E) {
                EQMultiplication eq = new EQMultiplication(
                        new PolynomialTerm(1, variable, -1),
                        variable.derive());
                return new PolynomialTerm(coef, new USub(eq), 1);
            } else {
                /* base is not e */
                return new LogarithmicTerm(coef / Math.log(base), variable, Math.E).derive();
            }
        } else {
            /* No Chain rule */

            if (base == Math.E) {
                /* base is e */
                return new PolynomialTerm(getCoef(), variable, -1);
            } else {
                /* base is not e */
                return new LogarithmicTerm(coef / Math.log(base), variable, Math.E).derive();

            }
        }
    }

    @Override
    public double limInfSolve() {
        /* Number */
        double number = getCoef();
        if (number == 0) {
            return 0;
        }
        boolean isNumberNegative = number < 0;

        /* Function */
        return isNumberNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
    }

    @Override
    public double limNegInfSolve() {
        return Double.NaN;
    }

    //
    // Boolean Methods
    //
    @Override
    public boolean similarTo(Term term) {
        switch (term.getTermType()) {
            case LOGARITHMIC:
                LogarithmicTerm logTerm = (LogarithmicTerm) term;
                return (logTerm.getBase() == base) && (getVar().equals(logTerm.getVar()));
            default:
                return false;
        }
    }

    @Override
    public final boolean equals(Term other) {
        if ((other.getTermType() == TermType.LOGARITHMIC) && (getCoef() == other.getCoef())) {
            LogarithmicTerm logTerm = (LogarithmicTerm) other;
            return (base == logTerm.getBase()) && (getVar().equals(logTerm.getVar()));
        }
        return false;
    }

    static class LogMultiplication {
        /* Variables */
        private ArrayList<Integer> numPowers;
        private ArrayList<LogarithmicTerm> logTerms;

        public LogMultiplication() {
            logTerms = new ArrayList<LogarithmicTerm>();
            numPowers = new ArrayList<Integer>();
        }

        public Term exportTerm() {
            if (logTerms.size() > 1) {
                EQMultiplication eq = new EQMultiplication(getTermArray());
                return new PolynomialTerm(1, new USub(eq));
            } else if (logTerms.size() == 1) {
                return getLogTermWithPower(0);
            } else {
                return null;
            }
        }

        //
        // Get & Set Methods
        //
        public ArrayList<Term> getTermArray() {
            ArrayList<Term> terms = new ArrayList<Term>();
            double coef = 1;

            for (int i = 0; i < logTerms.size(); i++) {
                coef *= logTerms.get(i).getCoef();
                logTerms.get(i).setCoef(1);

                terms.add(getLogTermWithPower(i));
            }
            terms.add(new PolynomialTerm(coef));

            return terms;
        }

        public int getIndexOf(LogarithmicTerm term) {
            for (int i = 0; i < logTerms.size(); i++) {
                if (logTerms.get(i).similarTo(term)) {
                    return i;
                }
            }
            return -1;
        }

        public LogarithmicTerm getLog(int index) {
            return logTerms.get(index);
        }

        public LogarithmicTerm getLog(LogarithmicTerm term) {
            return getLog(getIndexOf(term));
        }

        public int getPower(int index) {
            return numPowers.get(index);
        }

        public int getPower(LogarithmicTerm term) {
            return getPower(getIndexOf(term));
        }

        public void add(LogarithmicTerm term, int numPower) {
            logTerms.add(term);
            numPowers.add(numPower);
        }

        /**
         * Alters a LogarithmicTerm in the logTerms ArrayList. Utilized when multiplying
         * log terms.
         * 
         * @param term        the log term
         * @param coefficient the new coefficient
         * @param numPower    how much to add to the numerator power
         */
        public void update(LogarithmicTerm term, double coefficient, int numPower) {
            int index = getIndexOf(term);
            logTerms.set(index, new LogarithmicTerm(coefficient, term.getVar(), term.getBase()));
            numPowers.set(index, getPower(index) + numPower);
        }

        public Term getLogTermWithPower(int index) {
            int numPower = getPower(index);
            LogarithmicTerm term = getLog(index);

            return (numPower == 1) ? term
                    : new PolynomialTerm(term.getCoef(),
                            new USub(new LogarithmicTerm(1, term.getVar(), term.getBase())), numPower);
        }

        //
        // Boolean Methods
        //
        public boolean containsTerm(LogarithmicTerm term) {
            for (LogarithmicTerm i : logTerms) {
                if (i.similarTo(term)) {
                    return true;
                }
            }
            return false;
        }
    }

}
