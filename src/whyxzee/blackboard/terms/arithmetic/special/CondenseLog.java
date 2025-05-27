package whyxzee.blackboard.terms.arithmetic.special;

import java.util.ArrayList;

import whyxzee.blackboard.equations.EQMultiplication;
import whyxzee.blackboard.terms.LogarithmicTerm;
import whyxzee.blackboard.terms.PolynomialTerm;
import whyxzee.blackboard.terms.Term;
import whyxzee.blackboard.terms.Term.TermType;
import whyxzee.blackboard.terms.variables.USub;
import whyxzee.blackboard.terms.variables.Variable;
import whyxzee.blackboard.utils.ArithmeticUtils;

/**
 * A package which is used to condense logarithmic terms.
 * 
 * <p>
 * The methods in this class have not been checked.
 */
public class CondenseLog {
    /* Variables */
    private ArrayList<Double> bases;
    private ArrayList<EQMultiplication> eqs;

    public CondenseLog() {
        bases = new ArrayList<Double>();
        eqs = new ArrayList<EQMultiplication>();
    }

    public ArrayList<Term> performFunction(ArrayList<Term> terms) {
        for (int i = 0; i < terms.size(); i++) {
            /* Turning terms into -> log_b(x^n) form */
            LogarithmicTerm logTerm = (LogarithmicTerm) terms.get(i);
            double base = logTerm.getBase();
            Term insideLog;
            if (logTerm.getCoef() != 1) {
                Variable var = logTerm.getVar();
                double coef = logTerm.getCoef();
                int denomPower = ArithmeticUtils.numOfDigits(coef);
                int numPower = (int) (coef * Math.pow(10, denomPower));
                switch (var.getVarType()) {
                    case U_SUB_TERM:
                        /* Initializing variables */
                        Term innerTerm = var.getInnerTerm();

                        if (innerTerm.getTermType() == TermType.POLYNOMIAL) {
                            // as to not create a polynomial in a polynomial, to build off the old
                            // polynmial
                            PolynomialTerm innerPoly = (PolynomialTerm) innerTerm;
                            int oldNumPower = innerPoly.getNumeratorPower();
                            int oldDenomPower = innerPoly.getDenominatorPower();
                            insideLog = new PolynomialTerm(1, new USub(innerTerm), numPower * oldNumPower,
                                    denomPower * oldDenomPower);
                        } else {
                            insideLog = new PolynomialTerm(1, new USub(innerTerm), numPower, denomPower);
                        }
                        break;
                    default:
                        insideLog = new PolynomialTerm(1, var, numPower, denomPower);
                        break;
                }
            } else {
                Variable var = logTerm.getVar();
                insideLog = new PolynomialTerm(1, var);
                switch (var.getVarType()) {
                    case U_SUB_TERM:
                        insideLog = var.getInnerTerm();
                        break;
                    default:
                        insideLog = new PolynomialTerm(1, var);
                        break;

                }
            }

            /* Condensing */
            if (bases.contains(base)) {
                update(base, insideLog);
            } else {
                add(base, insideLog);
            }
        }

        /* Output */
        return (eqs.size() > 0) ? getTermArray() : null;

    }

    //
    // Get & Set Methods
    //
    private ArrayList<Term> getTermArray() {
        /* Initializing variables */
        ArrayList<Term> output = new ArrayList<Term>();

        /* Connecting the base with the equation */
        for (int i = 0; i < eqs.size(); i++) {
            double base = bases.get(i);
            output.add(new LogarithmicTerm(1, new USub(eqs.get(i)), base));
        }
        return output;
    }

    private int getIndexOf(double base) {
        for (int i = 0; i < bases.size(); i++) {
            if (bases.get(i) == base) {
                return i;
            }
        }
        return -1;
    }

    private void add(double base, Term term) {
        bases.add(base);
        eqs.add(new EQMultiplication(term));
    }

    private void update(double base, Term term) {
        eqs.get(getIndexOf(base)).add(term);
    }
}
