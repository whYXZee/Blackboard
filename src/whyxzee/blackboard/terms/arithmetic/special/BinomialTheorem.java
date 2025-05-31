package whyxzee.blackboard.terms.arithmetic.special;

import java.util.ArrayList;

import whyxzee.blackboard.equations.MathFunction;
import whyxzee.blackboard.equations.MultiplicativeEQ;
import whyxzee.blackboard.equations.MathFunction.FunctionType;
import whyxzee.blackboard.terms.PowerTerm;
import whyxzee.blackboard.terms.Term;
import whyxzee.blackboard.terms.Term.TermType;
import whyxzee.blackboard.terms.variables.*;
import whyxzee.blackboard.terms.variables.Variable.VarType;
import whyxzee.blackboard.utils.ArithmeticUtils;
import whyxzee.blackboard.utils.SpecialFunctions;

/**
 * A package for expanding a binomial term into a Term Array suitable for a
 * Sequential EQ.
 * 
 * <p>
 * The functionality of this class have not been checked.
 */
public class BinomialTheorem {
    /* Variables */
    private ArrayList<Term> finalTerms;

    public BinomialTheorem() {
        finalTerms = new ArrayList<Term>();
    }

    /**
     * 
     * @param terms a TermArray of terms in the (x+y)^n format
     * @return
     */
    public final ArrayList<Term> performTheorem(ArrayList<Term> terms) {
        if (terms.size() == 0) {
            return terms;
        }

        if (isFinalTermsEmpty()) {
            finalTerms.clear();
        }

        for (int i = 0; i < terms.size(); i++) {
            Term termIndex = terms.get(i);
            if (!isTermBinomial(termIndex)) {
                finalTerms.add(termIndex);
                break;
            }

            PowerTerm powTerm = (PowerTerm) termIndex;
            int power = (int) powTerm.getPower();

            ArrayList<Term> termArray = termIndex.getVar().getInnerFunction().getTermArray();
            Variable varOne = termArray.get(0).getVar();
            Variable varTwo = termArray.get(1).getVar();
            for (int r = 0; r < power; i++) {
                add(SpecialFunctions.combination(power, r),
                        new PowerTerm(1, varOne, power - r),
                        new PowerTerm(1, varTwo, r));
            }

        }

        return finalTerms;
    }

    //
    // Get & Set Methods
    //
    private void add(double coef, PowerTerm a, PowerTerm b) {
        MultiplicativeEQ eq = new MultiplicativeEQ(a, b);
        finalTerms.add(new PowerTerm(coef, new USub(eq)));
    }

    //
    // Boolean Methods
    //
    private boolean isFinalTermsEmpty() {
        return finalTerms.size() == 0;
    }

    private boolean isTermBinomial(Term term) {
        if (!term.isTermType(TermType.POWER)) {
            return false;
        }

        PowerTerm powTerm = (PowerTerm) term;
        if (!ArithmeticUtils.isInteger(powTerm.getPower())) {
            return false;
        }

        Variable var = term.getVar();
        if (!var.isVarType(VarType.U_SUB_EQ)) {
            return false;
        }

        MathFunction innerFunc = var.getInnerFunction();
        if (!innerFunc.isFunctionType(FunctionType.SEQUENCE) || innerFunc.termArraySize() != 2) {
            // not sequence nor term size of two
            return false;
        }
        return true;
    }
}
