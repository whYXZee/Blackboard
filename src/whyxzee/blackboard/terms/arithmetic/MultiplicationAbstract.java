package whyxzee.blackboard.terms.arithmetic;

import java.util.ArrayList;

import whyxzee.blackboard.terms.PowerTerm;
import whyxzee.blackboard.terms.Term;
import whyxzee.blackboard.terms.Term.TermType;
import whyxzee.blackboard.terms.variables.USub;
import whyxzee.blackboard.terms.variables.Variable.VarType;

/**
 * A general-use package for multiplying terms. At the moment, this package does
 * not work for ExponentialTerms and PolynomialTerms.
 * 
 * <p>
 * The functionality of this class has been checked on {@code 5/27/2025} and
 * nothing has changed since.
 */
public class MultiplicationAbstract {
    /* Variables */
    private ArrayList<Integer> numPowers;
    private ArrayList<Integer> denomPowers;
    private ArrayList<Term> multipliedTerms;

    public MultiplicationAbstract() {
        numPowers = new ArrayList<Integer>();
        denomPowers = new ArrayList<Integer>();
        multipliedTerms = new ArrayList<Term>();
    }

    public ArrayList<Term> performMultiplication(ArrayList<Term> terms) {
        if (terms.size() == 0) {
            return null;
        }

        /* Multiplication Algorithm */
        for (Term i : terms) {
            double coef = i.getCoef();
            int numPower = 1;
            int denomPower = 1;

            /* Polynomial clean up */
            if (isUSubTerm(i)) {
                PowerTerm polyTerm = (PowerTerm) i;
                numPower = polyTerm.getNumeratorPower();
                denomPower = polyTerm.getDenominatorPower();
                i = polyTerm.getVar().getInnerTerm();
                coef *= Math.pow(i.getCoef(), polyTerm.getPower());
            }

            /* Adding to the ArrayLists */
            int index = getIndexOf(i);
            if (index != -1) {
                Term term = getTerm(index);
                update(index, coef * term.getCoef(),
                        (numPower * getDenomPower(index)) + (denomPower * getNumPower(index)),
                        denomPower * getDenomPower(index));
            } else {
                add(i, numPower, denomPower);
            }
        }

        return getTermArray();
    }

    //
    // Get & Set Methods
    //
    private ArrayList<Term> getTermArray() {
        ArrayList<Term> output = new ArrayList<Term>();

        for (int i = 0; i < multipliedTerms.size(); i++) {
            Term term = getTerm(i);
            double coef = term.getCoef();

            if (hasPowerOne(i)) {
                output.add(term);
            } else {
                term.setCoef(1);
                output.add(new PowerTerm(coef, new USub(term), getNumPower(i), getDenomPower(i)));
            }
        }

        return output;
    }

    private int getIndexOf(Term term) {
        for (int i = 0; i < multipliedTerms.size(); i++) {
            if (term.similarTo(getTerm(i))) {
                return i;
            }
        }
        return -1;
    }

    private void add(Term term, int numPower, int denomPower) {
        numPowers.add(numPower);
        denomPowers.add(denomPower);
        multipliedTerms.add(term);
    }

    /**
     * 
     * @param index      the index of the term that needs to be updated
     * @param coef       the new coefficient of the term
     * @param numPower   the new numerator power for the term
     * @param denomPower the new denominator power for the term
     */
    private void update(int index, double coef, int numPower, int denomPower) {
        numPowers.set(index, numPower);
        denomPowers.set(index, denomPower);
        getTerm(index).setCoef(coef);
    }

    private Term getTerm(int index) {
        return multipliedTerms.get(index);
    }

    private int getNumPower(int index) {
        return numPowers.get(index);
    }

    private int getDenomPower(int index) {
        return denomPowers.get(index);
    }

    //
    // Boolean Methods
    //
    private boolean hasPowerOne(int index) {
        return (getNumPower(index) == 1) && (getDenomPower(index) == 1);
    }

    /**
     * Checks if the inputted term is a Polynomial with a USubbed term.
     * 
     * @param term
     * @return
     */
    private boolean isUSubTerm(Term term) {
        if (!isPowerTerm(term)) {
            return false;
        }
        return term.getVar().getVarType() == VarType.U_SUB_TERM;
    }

    private boolean isPowerTerm(Term term) {
        return term.getTermType() == TermType.POWER;
    }
}