package whyxzee.blackboard.terms.arithmetic.logarithmic;

import java.util.ArrayList;

import whyxzee.blackboard.equations.MultiplicativeEQ;
import whyxzee.blackboard.terms.LogarithmicTerm;
import whyxzee.blackboard.terms.PowerTerm;
import whyxzee.blackboard.terms.Term;
import whyxzee.blackboard.terms.variables.USub;
import whyxzee.blackboard.terms.variables.Variable;

/**
 * A package which is used to condense logarithmic terms.
 * 
 * <p>
 * The methods in this class have been checked on {@code 5/30/2025}, and nothing
 * has changed since.
 */
public class CondenseLog {
    /* Variables */
    private ArrayList<Double> bases;
    private ArrayList<MultiplicativeEQ> eqs;

    public CondenseLog() {
        bases = new ArrayList<Double>();
        eqs = new ArrayList<MultiplicativeEQ>();
    }

    /**
     * 
     * @param terms a termArray of LogarithmicTerms
     * @return
     */
    public ArrayList<Term> performFunction(ArrayList<Term> terms) {
        if (terms.size() == 0) {
            return terms;
        }

        for (int i = 0; i < terms.size(); i++) {
            /* Turning terms into -> log_b(x^n) form */
            LogarithmicTerm logTerm = (LogarithmicTerm) terms.get(i);
            logTerm.condense();
            double base = logTerm.getBase();
            Variable var = logTerm.getVar();

            /* Variable inside the Logarithmic */
            Term insideLog;
            switch (var.getVarType()) {
                case U_SUB_TERM:
                    insideLog = var.getInnerTerm();
                    break;
                default:
                    insideLog = new PowerTerm(1, var);
                    break;
            }

            /* Condensing */
            if (bases.contains(base)) {
                update(base, insideLog);
            } else {
                add(base, insideLog);
            }
        }

        /* Output */
        return getTermArray();

    }

    //
    // Get & Set Methods
    //
    /**
     * 
     * @return a term array with logarithmic terms.
     */
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

    /**
     * 
     * @param base
     * @return the index of the base in the bases ArrayList.
     */
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
        eqs.add(new MultiplicativeEQ(term));
    }

    private void update(double base, Term term) {
        MultiplicativeEQ eq = eqs.get(getIndexOf(base));
        eq.add(term);
        eq.organizeTerms();
    }
}
