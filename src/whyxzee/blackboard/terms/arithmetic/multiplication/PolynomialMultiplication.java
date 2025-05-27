package whyxzee.blackboard.terms.arithmetic.multiplication;

import java.util.ArrayList;

import whyxzee.blackboard.terms.PolynomialTerm;
import whyxzee.blackboard.terms.Term;
import whyxzee.blackboard.terms.variables.Variable;

public class PolynomialMultiplication {
    /* Variables */
    private ArrayList<Variable> variables;
    private ArrayList<PolynomialTerm> polyTerms;

    public PolynomialMultiplication() {
        variables = new ArrayList<Variable>();
        polyTerms = new ArrayList<PolynomialTerm>();
    }

    public ArrayList<Term> performFunction(ArrayList<Term> terms) {
        /* Simplifying Algorithm */
        for (int i = 0; i < terms.size(); i++) {
            /* Initialing variables */
            PolynomialTerm term = (PolynomialTerm) terms.get(i);
            double coef = term.getCoef();
            Variable var = term.getVar();
            int numPower = term.getNumeratorPower();
            int denomPower = term.getDenominatorPower();

            if (contains(var)) {
                // var in polyData
                PolynomialTerm poly2 = get(var);

                update(var, poly2.getCoef() * coef,
                        (numPower * poly2.getDenominatorPower()) + (denomPower * poly2.getNumeratorPower()),
                        denomPower * poly2.getDenominatorPower());

            } else {
                // var not in variables
                add(var, term);
            }
        }

        if (polyTerms.size() > 0) {
            return new ArrayList<Term>(polyTerms);
        }
        return null;
    }

    //
    // Get & Set Methods
    //
    /**
     * Gets the index of a term based if it is alike to the present term.
     * 
     * @param term
     * @return
     */
    private int getIndexOf(Variable var) {
        for (int i = 0; i < variables.size(); i++) {
            if (variables.get(i).equals(var)) {
                return i;
            }
        }
        return -1;
    }

    private PolynomialTerm get(int index) {
        return polyTerms.get(index);
    }

    private PolynomialTerm get(Variable var) {
        return get(getIndexOf(var));
    }

    private void add(Variable var, PolynomialTerm term) {
        polyTerms.add(term);
    }

    private void update(Variable var, double coefficient, int numPower, int denomPower) {
        polyTerms.set(getIndexOf(var), new PolynomialTerm(coefficient, var, numPower, denomPower));
    }

    //
    // Boolean Methods
    //
    private boolean contains(Variable var) {
        for (Variable i : variables) {
            if (i.equals(var)) {
                return true;
            }
        }
        return false;
    }
}
