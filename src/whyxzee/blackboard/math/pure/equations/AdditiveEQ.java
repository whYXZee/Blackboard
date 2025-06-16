package whyxzee.blackboard.math.pure.equations;

import java.util.ArrayList;

import whyxzee.blackboard.math.applied.settheory.DefinedList;
import whyxzee.blackboard.math.pure.numbers.BNumber;
import whyxzee.blackboard.math.pure.terms.Term;
import whyxzee.blackboard.math.pure.terms.TermUtils;
import whyxzee.blackboard.math.pure.terms.Term.TermType;

public class AdditiveEQ extends MathEQ {
    ///
    /// Constructor Methods
    ///
    // #region
    public AdditiveEQ(ArrayList<Term> terms) {
        super(EQType.ADDITIVE, terms);
        setTerms(TermUtils.AddTerms.performAddition(terms));
    }

    public AdditiveEQ(Term... terms) {
        super(EQType.ADDITIVE, terms);
        setTerms(TermUtils.AddTerms.performAddition(getTerms()));
    }

    // #endregion

    @Override
    public final String toString() {
        ArrayList<Term> termArr = getTerms();
        if (termArr.size() == 0) {
            return "";
        }

        String output = termArr.get(0).toString();
        for (int i = 1; i < termArr.size(); i++) {
            Term iTerm = termArr.get(i);
            // if (iTerm.isNegative()) {
            // // TODO: isNegative
            // output += " - " + iTerm.toString();
            // } else {
            // }
            output += " + " + iTerm.toString();
        }
        return output;
    }

    ///
    /// Arithmetic Methods
    ///
    @Override
    public final BNumber solve(BNumber value) {
        BNumber output = new BNumber(0, 0);
        for (Term i : getTerms()) {
            BNumber addend = i.solve(value);
            output.add(addend);
        }

        return output;
    }

    @Override
    public final DefinedList solutions() {
        // TODO: complex/imaginary numbers?

        ArrayList<BNumber> numbers = new ArrayList<BNumber>();
        ArrayList<Term> plusMin = getTermArr(TermType.PLUS_MINUS);

        if (plusMin.size() != 0) {
            for (Term i : plusMin) {
                BNumber iNum = new BNumber(0, 0);
                for (Term j : getTerms()) {
                    if (!j.equals(i)) {
                        iNum.add(j.getCoef());
                    }
                }
                BNumber numOne = iNum.clone();
                numOne.add(i.getCoef());
                BNumber numTwo = iNum.clone();
                numTwo.add(i.getCoef().negate());
                numbers.add(numOne);
                numbers.add(numTwo);
            }
        } else {
            BNumber output = new BNumber(0, 0);
            for (Term i : getTerms()) {
                output.add(i.getCoef());
            }
            numbers.add(output);
        }

        return new DefinedList("", numbers);
    }

}
