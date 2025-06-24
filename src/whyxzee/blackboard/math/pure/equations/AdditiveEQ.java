package whyxzee.blackboard.math.pure.equations;

import java.util.ArrayList;

import whyxzee.blackboard.math.pure.equations.terms.PowerTerm;
import whyxzee.blackboard.math.pure.equations.variables.Variable;

public class AdditiveEQ extends MathEQ {
    // #region Constructors
    public AdditiveEQ(ArrayList<PowerTerm> terms) {
        super(EQType.ADDITIVE, terms);
        getTerms().addition();
    }

    public AdditiveEQ(PowerTerm... terms) {
        super(EQType.ADDITIVE, terms);
        getTerms().addition();
    }

    public AdditiveEQ(TermArray terms) {
        super(EQType.ADDITIVE, terms);
        getTerms().addition();
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
            if (iTerm.needsNegString()) {
                // TODO: isNegative
                output += " - " + iTerm.negativeString();
            } else {
                output += " + " + iTerm.toString();
            }
        }
        return output;
    }
    // #endregion

    // #region Copying/Cloning
    @Override
    public MathEQ clone() {
        return new AdditiveEQ(getTerms().clone());
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
    public final PowerTerm solve(String variable, PowerTerm value) {
        TermArray solvedTerms = new TermArray();
        for (PowerTerm i : getTerms().getArr()) {
            solvedTerms.add(i.solve(variable, value));
        }

        solvedTerms.addition(); // not needed cuz it already adds when creating the eq
        return new AdditiveEQ(solvedTerms).toTerm();
    }
    // #endregion
}
