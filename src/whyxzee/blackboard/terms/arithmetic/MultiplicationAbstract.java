package whyxzee.blackboard.terms.arithmetic;

import java.util.ArrayList;

import whyxzee.blackboard.equations.EQSequence;
import whyxzee.blackboard.equations.MathFunction.FunctionType;
import whyxzee.blackboard.terms.ExponentialTerm;
import whyxzee.blackboard.terms.PowerTerm;
import whyxzee.blackboard.terms.Term;
import whyxzee.blackboard.terms.Term.TermType;
import whyxzee.blackboard.terms.variables.USub;
import whyxzee.blackboard.terms.variables.Variable;
import whyxzee.blackboard.terms.variables.Variable.VarType;

/**
 * A general-use package for multiplying terms. At the moment, this package does
 * not work for ExponentialTerms and PolynomialTerms.
 * 
 * <p>
 * The functionality of this class has been checked on {@code 5/28/2025} and
 * nothing has changed since.
 */
public class MultiplicationAbstract {
    /* Variables */
    private ArrayList<Integer> numPowers;
    private ArrayList<Integer> denomPowers;
    private ArrayList<Term> multipliedTerms;
    private boolean powerMode;
    private boolean expMode;

    public MultiplicationAbstract() {
        numPowers = new ArrayList<Integer>();
        denomPowers = new ArrayList<Integer>();
        multipliedTerms = new ArrayList<Term>();
    }

    public final ArrayList<Term> performMultiplication(ArrayList<Term> terms) {
        if (terms.size() == 0) {
            return terms;
        }

        if (areMultipliedTermsEmpty()) {
            numPowers.clear();
            denomPowers.clear();
            multipliedTerms.clear();
        }

        /* Multiplication Algorithm */
        for (Term i : terms) {
            System.out.println(multipliedTerms);
            powerMode = false;
            expMode = false;

            double coef = i.getCoef();
            int numPower = 1;
            int denomPower = 1;

            /* Polynomial clean up */
            if (isUSubTerm(i)) {
                PowerTerm powerTerm = (PowerTerm) i;
                numPower = powerTerm.getNumeratorPower();
                denomPower = powerTerm.getDenominatorPower();
                i = powerTerm.getVar().getInnerTerm();
                coef *= Math.pow(i.getCoef(), powerTerm.getPower());
            }

            if (isPowerTerm(i)) {
                powerMode = true;
                PowerTerm powerTerm = (PowerTerm) i;
                numPower = powerTerm.getNumeratorPower();
                denomPower = powerTerm.getDenominatorPower();
                powerTerm.setPower(1, 1);
                i = powerTerm;
            } else if (isExpTerm(i)) {
                expMode = true;
            }

            /* Adding to the ArrayLists */
            int index = getIndexOf(i);
            if (index != -1) {
                Term term = getTerm(index);
                if (expMode) {
                    update(index, coef * term.getCoef(), (ExponentialTerm) i);
                } else {
                    update(index, coef * term.getCoef(),
                            (numPower * getDenomPower(index)) + (denomPower * getNumPower(index)),
                            denomPower * getDenomPower(index));
                }
            } else {
                add(i, numPower, denomPower);
            }
        }

        return getTermArray();
    }

    //
    // Get & Set Methods
    //
    private final ArrayList<Term> getTermArray() {
        ArrayList<Term> output = new ArrayList<Term>();

        for (int i = 0; i < multipliedTerms.size(); i++) {
            Term term = getTerm(i);
            double coef = term.getCoef();

            if (hasPowerOne(i)) {
                output.add(term);
            } else if (isPowerTerm(term)) {
                PowerTerm powTerm = (PowerTerm) term;
                powTerm.setPower(getNumPower(i), getDenomPower(i));
                output.add(powTerm);
            } else {
                term.setCoef(1);
                output.add(new PowerTerm(coef, new USub(term), getNumPower(i), getDenomPower(i)));
            }
        }

        return output;
    }

    private final int getIndexOf(Term term) {
        for (int i = 0; i < multipliedTerms.size(); i++) {
            Term indexTerm = getTerm(i);
            if (term.similarTo(indexTerm) && !powerMode && !expMode) {
                return i;
            }

            if (powerMode && hasPowerSimilarity(term, indexTerm)) {
                return i;
            }

            if (expMode && hasExpSimilarity(term, indexTerm)) {
                return i;
            }
        }
        return -1;
    }

    private final void add(Term term, int numPower, int denomPower) {
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
    private final void update(int index, double coef, int numPower, int denomPower) {
        numPowers.set(index, numPower);
        denomPowers.set(index, denomPower);
        getTerm(index).setCoef(coef);
    }

    /**
     * Special method for updating an exponential term
     * 
     * @param index   the index of the term that needs to be updated
     * @param coef    the new coefficient of the term
     * @param expTerm the new term
     */
    private final void update(int index, double coef, ExponentialTerm expTerm) {
        Variable var = expTerm.getVar();
        Variable oldVar = getTerm(index).getVar();
        VarType varType = var.getVarType();
        EQSequence outputEQ;

        if (isEQSeq(oldVar) && isEQSeq(var)) {
            // both are SequentialEQs
            outputEQ = (EQSequence) oldVar.getInnerFunction();
            outputEQ.addToTermArray(var.getInnerFunction().getTermArray());

        } else if (isEQSeq(oldVar)) {
            // oldVar is a SequentialEQ
            /* Addend Term */
            Term termToAdd = new PowerTerm(1, var);
            if (varType == VarType.U_SUB_TERM) {
                termToAdd = var.getInnerTerm();
            }
            System.out.println(termToAdd.printConsole());

            /* Sequence */
            outputEQ = (EQSequence) oldVar.getInnerFunction();
            outputEQ.add(termToAdd);
            outputEQ.organizeTerms();

        } else {
            // oldVar is a MultiplicativeEQ or a normal variable
            /* Addend Term */
            Term termToAdd = new PowerTerm(1, var);
            if (varType == VarType.U_SUB_TERM) {
                termToAdd = var.getInnerTerm();
            }

            /* Sequence */
            ArrayList<Term> termArray = new ArrayList<Term>();
            termArray.add(new PowerTerm(1, oldVar));
            termArray.add(termToAdd);
            outputEQ = new EQSequence(termArray);
        }

        multipliedTerms.set(index, new ExponentialTerm(coef, new USub(outputEQ), expTerm.getBase()));
    }

    private final Term getTerm(int index) {
        return multipliedTerms.get(index);
    }

    private final int getNumPower(int index) {
        return numPowers.get(index);
    }

    private final int getDenomPower(int index) {
        return denomPowers.get(index);
    }

    //
    // Boolean Methods
    //
    private final boolean hasPowerOne(int index) {
        return (getNumPower(index) == 1) && (getDenomPower(index) == 1);
    }

    /**
     * Checks if the inputted term is a Polynomial with a USubbed term.
     * 
     * @param term
     * @return
     */
    private final boolean isUSubTerm(Term term) {
        if (!isPowerTerm(term)) {
            return false;
        }
        return term.getVar().getVarType() == VarType.U_SUB_TERM;
    }

    private final boolean isPowerTerm(Term term) {
        return term.getTermType() == TermType.POWER;
    }

    private final boolean isExpTerm(Term term) {
        return term.getTermType() == TermType.EXPONENTIAL;
    }

    private final boolean areMultipliedTermsEmpty() {
        return multipliedTerms.size() == 0;
    }

    private final boolean hasPowerSimilarity(Term one, Term two) {
        if (!isPowerTerm(one) || !isPowerTerm(two)) {
            return false;
        }

        return one.getVar().equals(two.getVar());
    }

    private final boolean hasExpSimilarity(Term one, Term two) {
        if (!isExpTerm(one) || !isExpTerm(two)) {
            return false;
        }

        ExponentialTerm expOne = (ExponentialTerm) one;
        ExponentialTerm expTwo = (ExponentialTerm) two;
        return expOne.getBase() == expTwo.getBase();
    }

    private final boolean isEQSeq(Variable var) {
        if (var.getVarType() != VarType.U_SUB_EQ) {
            return false;
        }

        return var.getInnerFunction().getFunctionType() == FunctionType.SEQUENCE;
    }
}