package whyxzee.blackboard.math.pure.equations;

import java.util.ArrayList;

import whyxzee.blackboard.math.applied.settheory.DefinedList;
import whyxzee.blackboard.math.pure.numbers.BNumber;
import whyxzee.blackboard.math.pure.terms.Term;

public class MultiplyEQ extends MathEQ {
    public MultiplyEQ(ArrayList<Term> terms) {
        super(EQType.MULTIPLY, terms);
    }

    public MultiplyEQ(Term... terms) {
        super(EQType.MULTIPLY, terms);
    }

    @Override
    public final String toString() {
        ArrayList<Term> termArr = getTerms();
        if (termArr.size() == 0) {
            return "";
        } else if (termArr.size() == 1) {
            return termArr.get(0).toString();
        }

        String output = "";
        for (int i = 0; i < termArr.size(); i++) {
            Term iTerm = termArr.get(i);
            output += "(" + iTerm + ")";
        }
        return output;
    }

    ///
    /// Arithmetic Methods
    ///
    @Override
    public BNumber solve(BNumber value) {
        BNumber output = new BNumber(1, 0); // needs to be 1 or (1 + i)?
        for (Term i : getTerms()) {
            output = BNumber.multiply(output, i.solve(value));
        }

        return output;
    }

    @Override
    public DefinedList solutions() {
        ArrayList<BNumber> sols = new ArrayList<BNumber>();

        throw new UnsupportedOperationException();
        // return new DefinedList("", sols);
    }

}
