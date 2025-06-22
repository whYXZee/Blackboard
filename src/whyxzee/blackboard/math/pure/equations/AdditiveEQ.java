package whyxzee.blackboard.math.pure.equations;

import java.util.ArrayList;

import whyxzee.blackboard.math.pure.equations.terms.PowerTerm;
import whyxzee.blackboard.math.pure.equations.variables.Variable;
import whyxzee.blackboard.math.pure.numbers.ComplexNum;

public class AdditiveEQ extends MathEQ {
    // #region Constructors
    public AdditiveEQ(ArrayList<PowerTerm> terms) {
        super(EQType.ADDITIVE, terms);
        getTerms().add();
    }

    public AdditiveEQ(PowerTerm... terms) {
        super(EQType.ADDITIVE, terms);
        getTerms().add();
    }

    public AdditiveEQ(TermArray terms) {
        super(EQType.ADDITIVE, terms);
        getTerms().add();
    }
    // #endregion

    // #region String/Display
    @Override
    public final String toString() {
        TermArray termArr = getTerms();
        if (termArr.isEmpty()) {
            return "";
        }

        String output = termArr.get(0).toString();
        for (int i = 1; i < termArr.size(); i++) {
            PowerTerm iTerm = termArr.get(i);
            // if (iTerm.isNegative()) {
            // // TODO: isNegative
            // output += " - " + iTerm.toString();
            // } else {
            // }
            output += " + " + iTerm.toString();
        }
        return output;
    }
    // #endregion

    // #region Copying/Cloning
    @Override
    public MathEQ clone() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clone'");
    }
    // #endregion

    // #region Conversion Methods
    @Override
    public PowerTerm toTerm() {
        if (getTerms().size() == 1) {
            // AdditiveEQ not needed if there is only one term.
            return getTerms().get(0);
        }
        return new PowerTerm(1, new Variable<MathEQ>(this));
    }
    // #endregion

    // #region Solve
    @Override
    public final PowerTerm solve(String variable, ComplexNum value) {
        TermArray solvedTerms = new TermArray();
        for (PowerTerm i : getTerms().getArr()) {
            solvedTerms.add(i.solve(variable, value));
        }

        solvedTerms.add();
        return new AdditiveEQ(solvedTerms).toTerm();
    }
    // #endregion

    // @Override
    // @Deprecated
    // public final DefinedList solutions() {
    // // TODO: complex/imaginary numbers?

    // ArrayList<ComplexNum> numbers = new ArrayList<ComplexNum>();
    // ArrayList<PowerTerm> plusMin = getTermArr(TermType.PLUS_MINUS);

    // if (plusMin.size() != 0) {
    // for (Term i : plusMin) {
    // ComplexNum iNum = new ComplexNum(0, 0);
    // for (Term j : getTerms()) {
    // if (!j.equals(i)) {
    // iNum = ComplexNum.add(iNum, j.getCoef());
    // }
    // }
    // ComplexNum numOne = ComplexNum.add(iNum, i.getCoef());
    // ComplexNum numTwo = ComplexNum.add(iNum, i.getCoef().negate());
    // numbers.add(numOne);
    // numbers.add(numTwo);
    // }
    // } else {
    // ComplexNum output = new ComplexNum(0, 0);
    // for (Term i : getTerms()) {
    // output = ComplexNum.add(output, i.getCoef());
    // }
    // numbers.add(output);
    // }

    // return new DefinedList("", numbers);
    // }
}
