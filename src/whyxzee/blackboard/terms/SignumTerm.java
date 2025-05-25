package whyxzee.blackboard.terms;

import java.util.ArrayList;

import whyxzee.blackboard.equations.EQMultiplication;
import whyxzee.blackboard.equations.EQSequence;
import whyxzee.blackboard.terms.variables.*;

/**
 * A package for the signum function, with a*sgn(x).
 * 
 * <p>
 * The class is constructed as a y=sgn(x)
 * 
 * <p>
 * The functionality of this class has been checked on {@code 5/20/2025} and the
 * following has changed since:
 * <ul>
 * <li>similarTo()
 */
public class SignumTerm extends Term {

    public SignumTerm(double coefficient, Variable var) {
        super(coefficient, var, TermType.SIGNUM);
    }

    @Override
    public String toString() {
        /* Initializing variables */
        double coef = getCoefficient();

        if (coef == 0) {
            return "0";
        } else if (coef == 1) {
            return "sgn(" + getVar().toString() + ")";
        } else {
            return Double.toString(coef) + "sgn(" + getVar().toString() + ")";
        }
    }

    @Override
    public String printConsole() {
        /* Initializing variables */
        double coef = getCoefficient();

        if (coef == 0) {
            return "0";
        } else if (coef == 1) {
            return "sgn(" + getVar().printConsole() + ")";
        } else {
            return Double.toString(coef) + "sgn(" + getVar().printConsole() + ")";
        }
    }

    //
    // Arithmetic Methods (Static)
    //
    public static Term add(ArrayList<Term> terms) {
        /* Initializing variables */
        SignumData signData = new SignumData();

        for (int i = 0; i < terms.size(); i++) {
            SignumTerm term = (SignumTerm) terms.get(i);
            double coef = term.getCoefficient();

            if (signData.containsTerm(term)) {
                SignumTerm oldTerm = signData.getSignTerm(term);
                signData.update(term, coef + oldTerm.getCoefficient());
            } else {
                signData.add(term);
            }
        }

        if (signData.size() > 1) {
            EQSequence eq = new EQSequence(new ArrayList<Term>(signData.getTermArray()));
            return new PolynomialTerm(1, new USub(eq));
        } else if (signData.size() == 1) {
            return signData.getSignumTerm(0);
        }

        return null;
    }

    public static Term multiply(ArrayList<Term> terms) {
        /* Initializing variables */
        SignumData signData = new SignumData();

        /* Multiplication algorithm */
        for (int i = 0; i < terms.size(); i++) {
            SignumTerm term = (SignumTerm) terms.get(i);
            double coef = term.getCoefficient();

            if (signData.containsTerm(term)) {
                SignumTerm oldTerm = signData.getSignTerm(term);

                signData.update(oldTerm, coef * oldTerm.getCoefficient(), signData.getSignumPower(term) + 1);
            } else {
                signData.add(term, 1);
            }
        }

        if (signData.size() > 1) {
            EQMultiplication eq = new EQMultiplication(new ArrayList<Term>(signData.getTermArray()));
            return new PolynomialTerm(1, new USub(eq));
        } else if (signData.size() == 1) {
            return signData.getSignumTerm(0);
        }

        return null;
    }

    //
    // Arithmetic Methods (Object-related)
    //
    @Override
    public double solve(double value) {
        return getCoefficient() * Math.signum(getVar().solve(value));
    }

    @Override
    public Term negate() {
        return new SignumTerm(-1 * getCoefficient(), getVar());
    }

    @Override
    public Term derive() {
        return PolynomialTerm.ZERO_TERM;
    }

    @Override
    public double limInfSolve() {
        /* Number */
        double number = getCoefficient();
        if (number == 0) {
            return 0;
        }

        /* Function */
        return number;
    }

    @Override
    public double limNegInfSolve() {

        /* Number */
        double number = getCoefficient();
        if (number == 0) {
            return 0;
        }

        /* Function */
        return -number;
    }

    //
    // Boolean Methods
    //
    @Override
    public boolean similarTo(Term term) {
        switch (term.getTermType()) {
            case SIGNUM:
                SignumTerm signTerm = (SignumTerm) term;
                return getVar().equals(signTerm.getVar());
            default:
                return false;
        }
    }

    static class SignumData {
        /* Variables */
        private ArrayList<Integer> numPowers;
        private ArrayList<SignumTerm> signumTerms;

        public SignumData() {
            numPowers = new ArrayList<Integer>();
            signumTerms = new ArrayList<SignumTerm>();
        }

        //
        // Get & Set Methods
        //
        public void add(SignumTerm term) {
            signumTerms.add(term);
        }

        public void add(SignumTerm term, int numPower) {
            signumTerms.add(term);
            numPowers.add(numPower);
        }

        public void update(SignumTerm term, double coefficient, int numPower) {
            int index = getIndexOf(term);
            signumTerms.set(index, new SignumTerm(coefficient, term.getVar()));
            numPowers.set(index, numPower);
        }

        public void update(SignumTerm term, double coefficient) {
            signumTerms.set(getIndexOf(term), new SignumTerm(coefficient, term.getVar()));
        }

        public SignumTerm getSignumTerm(int index) {
            return signumTerms.get(index);
        }

        public int getSignumPower(int index) {
            return numPowers.get(index);
        }

        public SignumTerm getSignTerm(SignumTerm term) {
            return getSignumTerm(getIndexOf(term));
        }

        public int getSignumPower(SignumTerm term) {
            return getSignumPower(getIndexOf(term));
        }

        public ArrayList<SignumTerm> getTermArray() {
            return signumTerms;
        }

        public int getIndexOf(SignumTerm term) {
            for (int i = 0; i < signumTerms.size(); i++) {
                if (signumTerms.get(i).similarTo(term)) {
                    return i;
                }
            }
            return -1;
        }

        public int size() {
            return signumTerms.size();
        }

        //
        // Boolean Methods
        //
        public boolean containsTerm(SignumTerm term) {
            for (SignumTerm i : signumTerms) {
                if (i.similarTo(term)) {
                    return true;
                }
            }
            return false;
        }
    }
}
