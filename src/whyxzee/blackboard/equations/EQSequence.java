package whyxzee.blackboard.equations;

import whyxzee.blackboard.terms.Term;

public class EQSequence extends MathFunction {

    public EQSequence(Term... terms) {
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
    public MathFunction derive(int degree) {
        Term[] outputTerms = {};
        int index = 0;
        for (Term term : getTermArray()) {
            outputTerms[index] = term.derive(degree);
            index++;
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
                    output += "+ " + term;
                }
            }

        }

        return output;
    }

}
