package whyxzee.blackboard.math.pure.equations;

import java.util.ArrayList;

import whyxzee.blackboard.math.pure.equations.terms.PowerTerm;
import whyxzee.blackboard.math.pure.equations.variables.Variable;
import whyxzee.blackboard.math.pure.numbers.ComplexNum;

public class MultiplyEQ extends MathEQ {
    // #region Constructors
    public MultiplyEQ(ArrayList<PowerTerm> terms) {
        super(EQType.MULTIPLY, terms);
        getTerms().multiply();
    }

    public MultiplyEQ(PowerTerm... terms) {
        super(EQType.MULTIPLY, terms);
        getTerms().multiply();
    }

    public MultiplyEQ(TermArray terms) {
        super(EQType.MULTIPLY, terms);
        getTerms().multiply();
    }
    // #endregion

    // #region String/Display
    @Override
    public final String toString() {
        TermArray termArr = getTerms();
        if (termArr.isEmpty()) {
            return "";
        } else if (termArr.size() == 1) {
            return termArr.get(0).toString();
        }

        String output = "";
        for (int i = 0; i < termArr.size(); i++) {
            PowerTerm iTerm = termArr.get(i);
            output += "(" + iTerm + ")";
        }
        return output;
    }
    // #endregion

    // #region Copying/Cloning
    @Override
    public MathEQ clone() {
        return new MultiplyEQ(getTerms().clone());
    }
    // #endregion

    // #region Conversion Methods
    public final PowerTerm toTerm() {
        TermArray terms = getTerms().clone();
        if (terms.size() == 1) {
            // MultiplyEQ not needed if there is only one term.
            return terms.get(0);
        }

        // brings out the coefficient
        ComplexNum coef = terms.getConstantAndRemove().getCoef();
        return new PowerTerm(coef, new Variable<MathEQ>(new MultiplyEQ(terms)));
    }
    // #endregion

    // #region Solve
    @Override
    public final PowerTerm solve(String variable, ComplexNum value) {
        TermArray solvedTerms = new TermArray();
        for (PowerTerm i : getTerms().getArr()) {
            solvedTerms.add(i.solve(variable, value));
        }

        solvedTerms.multiply();
        return new MultiplyEQ(solvedTerms).toTerm();
    }
    // #endregion
}
