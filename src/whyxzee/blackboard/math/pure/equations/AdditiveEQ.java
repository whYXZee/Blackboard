package whyxzee.blackboard.math.pure.equations;

import java.util.ArrayList;

import whyxzee.blackboard.math.applied.settheory.DefinedList;
import whyxzee.blackboard.math.pure.numbers.ComplexNum;
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
    public final ComplexNum solve(ComplexNum value) {
        ComplexNum output = new ComplexNum(0, 0);
        for (Term i : getTerms()) {
            ComplexNum addend = i.solve(value);
            output = ComplexNum.add(output, addend);
        }

        return output;
    }

    @Override
    public final DefinedList solutions() {
        // TODO: complex/imaginary numbers?

        ArrayList<ComplexNum> numbers = new ArrayList<ComplexNum>();
        ArrayList<Term> plusMin = getTermArr(TermType.PLUS_MINUS);

        if (plusMin.size() != 0) {
            for (Term i : plusMin) {
                ComplexNum iNum = new ComplexNum(0, 0);
                for (Term j : getTerms()) {
                    if (!j.equals(i)) {
                        iNum = ComplexNum.add(iNum, j.getCoef());
                    }
                }
                ComplexNum numOne = ComplexNum.add(iNum, i.getCoef());
                ComplexNum numTwo = ComplexNum.add(iNum, i.getCoef().negate());
                numbers.add(numOne);
                numbers.add(numTwo);
            }
        } else {
            ComplexNum output = new ComplexNum(0, 0);
            for (Term i : getTerms()) {
                output = ComplexNum.add(output, i.getCoef());
            }
            numbers.add(output);
        }

        return new DefinedList("", numbers);
    }

}
