package whyxzee.blackboard.equations;

import java.util.ArrayList;

import whyxzee.blackboard.terms.PowerTerm;
import whyxzee.blackboard.terms.Term;
import whyxzee.blackboard.terms.Term.TermType;
import whyxzee.blackboard.terms.variables.USub;

/**
 * A package for an equation based on multiplication.
 * 
 * <p>
 * The functionality of this class has not been checked.
 */
public class MultiplicativeEQ extends MathFunction {
    /**
     * 
     * @param terms
     */
    public MultiplicativeEQ(Term... terms) {
        super(FunctionType.MULTIPLICATION, terms);
    }

    public MultiplicativeEQ(ArrayList<Term> terms) {
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
        for (TermType i : TermType.values()) {
            performMultiplicationOn(i);
        }

    }

    @Override
    public void merge(MathFunction function) {

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
    public final MathFunction negate() {
        add(new PowerTerm(-1));
        organizeTerms();
        return this;
    }

    @Override
    public MathFunction derive() {
        /* Initializing variables */
        ArrayList<Term> eqSeqTerms = new ArrayList<Term>();
        ArrayList<Term> termArray = getTermArray();

        /* Derivative process */
        for (int i = 0; i < termArray.size(); i++) {
            // for the derived function
            MultiplicativeEQ term = new MultiplicativeEQ();
            term.add(termArray.get(i).derive());
            for (int j = 0; j < termArray.size(); j++) {
                // for the non-derived functions
                if (j != i) {
                    // whenever not the same term
                    term.add(termArray.get(j));
                }
            }

            /* Remove missing data */
            eqSeqTerms.add(new PowerTerm(1, new USub(term), 1));
        }

        return new SequentialEQ(eqSeqTerms);
    }
}
