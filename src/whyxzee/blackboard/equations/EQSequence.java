package whyxzee.blackboard.equations;

import java.util.ArrayList;

import whyxzee.blackboard.terms.PolynomialTerm;
import whyxzee.blackboard.terms.Term;

/**
 * A package for equations that are a sequence, meaning that they are chained
 * with addition and subtraction.
 * 
 * <p>
 * The functionality of this class has been checked on {@code 5/16/2025}, and
 * nothing has changed since.
 */
public class EQSequence extends MathFunction {

    public EQSequence(Term... terms) {
        super(terms);
    }

    public EQSequence(ArrayList<Term> terms) {
        super(terms);
    }

    @Override
    public void simplify() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'simplify'");
    }

    @Override
    public double solve(double value) {
        double output = 0;

        for (Term term : getTermArray()) {
            output += term.solve(value);
        }

        return output;
    }

    @Override
    public MathFunction derive() {
        ArrayList<Term> outputTerms = new ArrayList<Term>();
        for (Term term : getTermArray()) {
            Term derived = term.derive();
            if (!derived.equals(PolynomialTerm.ZERO_TERM)) {
                // if the derivative is not 0 nor DNE
                outputTerms.add(derived);
            }
        }

        return new EQSequence(outputTerms);
    }

    @Override
    public String toString() {
        String output = "";
        Term[] termArray = getTermArray();

        for (int i = 0; i < termArray.length; i++) {
            Term term = termArray[i];

            if (i == 0) {
                output += term;
            } else {
                if (term.isNegative()) {
                    output += " - " + term.negate();
                } else {
                    output += " + " + term;
                }
            }

        }

        return output;
    }

}
