package whyxzee.blackboard.equations;

import java.util.ArrayList;

import whyxzee.blackboard.terms.PolynomialTerm;
import whyxzee.blackboard.terms.Term;
import whyxzee.blackboard.terms.variables.USub;

/**
 * A package for an equation based on multiplication
 * 
 * <p>
 * The functionality of this class has not been checked.
 */
public class EQMultiplication extends MathFunction {

    /**
     * 
     * @param constant the coefficient of the equation.
     * @param terms
     */
    public EQMultiplication(Term... terms) {
        super(FunctionType.MULTIPLICATION, terms);
    }

    public EQMultiplication(ArrayList<Term> terms) {
        super(FunctionType.MULTIPLICATION, terms);
    }

    @Override
    public String toString() {
        String output = "";
        for (Term term : getTermArray()) {
            output += "(" + term.toString() + ")";
        }
        return output;
    }

    @Override
    public String printConsole() {
        String output = "";
        for (Term term : getTermArray()) {
            output += "(" + term.printConsole() + ")";
        }
        return output;
    }

    @Override
    public void simplify() {
        // TODO implement simplify in EQ Muliplication
        throw new UnsupportedOperationException("Unimplemented method 'simplify'");
    }

    //
    // Arithmetic Methods
    //

    @Override
    public double solve(double value) {
        /* Solving */
        double output = 1;
        for (Term i : getTermArray()) {
            output *= i.solve(value);
        }
        return output;
    }

    @Override
    public MathFunction derive() {
        /* Initializing variables */
        ArrayList<Term> eqSeqTerms = new ArrayList<Term>();
        Term[] termArray = getTermArray();

        /* Derivative process */
        for (int i = 0; i < termArray.length; i++) {
            // for the derived function
            EQMultiplication term = new EQMultiplication();
            term.addTerm(termArray[i].derive());
            for (int j = 0; j < termArray.length; j++) {
                // for the non-derived functions
                if (j != i) {
                    // whenever not the same term
                    term.addTerm(termArray[j]);
                }
            }

            /* Remove missing data */
            createTermArray();
            eqSeqTerms.add(new PolynomialTerm(1, new USub(term), 1));
        }

        return new EQSequence(eqSeqTerms);
    }
}
