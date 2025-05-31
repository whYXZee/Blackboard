package whyxzee.blackboard.terms.arithmetic.logarithmic;

import java.util.ArrayList;

import whyxzee.blackboard.equations.MathFunction;
import whyxzee.blackboard.equations.MathFunction.FunctionType;
import whyxzee.blackboard.terms.LogarithmicTerm;
import whyxzee.blackboard.terms.PowerTerm;
import whyxzee.blackboard.terms.Term;
import whyxzee.blackboard.terms.Term.TermType;
import whyxzee.blackboard.terms.variables.USub;
import whyxzee.blackboard.terms.variables.Variable;
import whyxzee.blackboard.utils.SpecialFunctions;

/**
 * A package which is used to expand logarithmic terms in a Sequential Equation.
 * 
 * <p>
 * The methods in this class have been checked on {@code 5/30/2025} and nothing
 * has changed since.
 */
public class ExpandLog {
    /* Variables */
    private ArrayList<Term> expandedTerms;

    public ExpandLog() {
        expandedTerms = new ArrayList<Term>();
    }

    public ArrayList<Term> performExpansion(ArrayList<Term> terms) {
        if (terms.size() == 0) {
            return terms;
        }

        /* Expansion Algorithm */
        for (int i = 0; i < terms.size(); i++) {
            /* Condensed */
            LogarithmicTerm logTerm = (LogarithmicTerm) terms.get(i);
            double coef = logTerm.getCoef();
            Variable var = logTerm.getVar();
            double base = logTerm.getBase();

            /* Expanded */
            double overallInnerCoef = 1;
            double coefFactor = 1;
            Variable varToAdd = var;

            switch (var.getVarType()) {
                case U_SUB_EQ:
                    MathFunction innerFunc = var.getInnerFunction();
                    if (innerFunc.isFunctionType(FunctionType.SEQUENCE)) {
                        add(coef * coefFactor, varToAdd, base);
                        break;
                    }

                    // is Multiplicative EQ after this
                    for (Term j : innerFunc.getTermArray()) {
                        if (j.isTermType(TermType.POWER)) {
                            PowerTerm powTerm = (PowerTerm) j;
                            coefFactor = powTerm.getPower();
                            varToAdd = j.getVar();
                        } else {
                            varToAdd = new USub(j);
                        }
                        overallInnerCoef *= j.getCoef();

                        add(coef * coefFactor, varToAdd, base);
                    }
                    break;

                case U_SUB_TERM:
                    Term innerTerm = var.getInnerTerm();
                    if (innerTerm.isTermType(TermType.POWER)) {
                        PowerTerm powTerm = (PowerTerm) innerTerm;
                        coef = powTerm.getPower();
                        varToAdd = innerTerm.getVar();
                    } else {
                        varToAdd = new USub(innerTerm);
                    }
                    overallInnerCoef *= innerTerm.getCoef();
                    add(coef * coefFactor, varToAdd, base);
                    break;

                default:
                    add(coef * coefFactor, varToAdd, base);
                    break;
            }

            expandedTerms.add(new PowerTerm(SpecialFunctions.log_b(base, overallInnerCoef)));
        }

        return expandedTerms;
    }

    //
    // Get & Set Methods
    //
    private final void add(double coef, Variable var, double base) {
        expandedTerms.add(new LogarithmicTerm(coef, var, base));
    }
}
