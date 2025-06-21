package whyxzee.blackboard.math.pure.algebra.solver;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.pure.equations.AdditiveEQ;
import whyxzee.blackboard.math.pure.equations.EquationUtils;
import whyxzee.blackboard.math.pure.equations.MathEQ;
import whyxzee.blackboard.math.pure.equations.MathEQ.EQType;
import whyxzee.blackboard.math.pure.numbers.ComplexNum;
import whyxzee.blackboard.math.pure.terms.PlusMinusTerm;
import whyxzee.blackboard.math.pure.terms.PowerTerm;
import whyxzee.blackboard.math.pure.terms.Term;
import whyxzee.blackboard.math.pure.terms.TermUtils;
import whyxzee.blackboard.math.pure.terms.Term.TermType;
import whyxzee.blackboard.math.pure.terms.variables.Variable;
import whyxzee.blackboard.utils.Loggy;

/**
 * A general-use package for an algebraic solver. The following have been
 * implemented:
 * <ul>
 * <li>linear solution
 * <li>TODO: polynomial solutions
 * <li>TODO: multivariate
 * <li>TODO: infinite or no solutions
 * </ul>
 * 
 * <p>
 * The functionality of this class has not been tested.
 */
public class AlgebraSolver {
    /* Variables */
    private static final Loggy loggy = new Loggy(Constants.Loggy.ALGEBRA_SOLVER_LOGGY);
    private static int maxIterations = 10;

    private static ArrayList<Term> lSide; // will contain the info for the var
    private static ArrayList<Term> rSide;
    private static Variable varToSolve; // for now, assume normal var
    private static boolean opPerformed = false;
    private static boolean areSidesSwapped = false;

    /**
     * TODO: what if the varToSolve is an additive EQ, and is equal to the left
     * side? (turn ArrayList<Term> -> PowerTerm to not confuse the program)
     * 
     * @param var       the variable that should be found
     * @param leftSide  the ArrayList<Term> of an AdditiveEQ.
     * @param rightSide the ArrayList<Term> of an AdditiveEQ.
     * @return
     */
    public static final SolutionData performOp(Variable var, ArrayList<Term> leftSide, ArrayList<Term> rightSide) {
        /* Variables */
        lSide = EquationUtils.deepCopyTerms(leftSide);
        rSide = EquationUtils.deepCopyTerms(rightSide);
        varToSolve = var;

        /* Swap Sides if Needed */
        if (!sideContainsVar(leftSide) && sideContainsVar(rightSide)) {
            ArrayList<Term> temp = lSide;
            lSide = rSide;
            rSide = temp;
            areSidesSwapped = true;
        }

        /* Arithmetic */
        int i = 1;
        while (!isVarIsolated()) {
            loggy.log("---- Iteration " + i + " ----");
            // System.out.println("old eq: " + leftSide + " = " + rightSide);
            logSides();

            /* Arithmetic */
            opPerformed = false;
            inverse();
            multiply();
            add();

            /* Iterations */
            i++;
            if (i > maxIterations) {
                break;
            }
        }

        if (!areSidesSwapped && isMultivariate(rightSide)) {
            return new SolutionData(var, EquationUtils.simplifyAdditive(new AdditiveEQ(rSide)));
        } else if (areSidesSwapped && isMultivariate(leftSide)) {
            return new SolutionData(var, EquationUtils.simplifyAdditive(new AdditiveEQ(lSide)));
        }
        return new SolutionData(var, extraneousSolutions(leftSide, rightSide));
    }

    // #region inverse()
    private static final void inverse() {
        if (lSide.size() != 1) {
            return;
        }

        Term lTerm = lSide.get(0);
        if (!lTerm.containsVar(varToSolve) || !lTerm.getCoef().equals(1)) {
            return;
        }

        opPerformed = true;
        // ArrayList<Term> newLeft = new ArrayList<Term>();
        ArrayList<Term> newRight = new ArrayList<Term>();
        switch (lTerm.getTermType()) {
            case POWER:
                PowerTerm powTerm = (PowerTerm) lTerm;
                ComplexNum lPower = powTerm.getPower();
                if (lPower.equals(-1)) {
                    loggy.logDetail("power of -1");

                    /* Arithmetic */
                    // TODO: inverse later
                    break;
                }

                boolean rSideNeedsUSub = true;
                USub rSideVar = new USub(new AdditiveEQ(rSide));
                ComplexNum constantVal = rSide.get(0).getCoef();
                if (rSide.size() == 1) {
                    rSideNeedsUSub = false;
                }
                ComplexNum newPower = lPower.reciprocal();

                // TODO: roots of complex number

                if (lPower.mod(2).equals(0)) {
                    loggy.logDetail("even power");

                    if (rSideNeedsUSub) {
                        USub powUSub = new USub(new PowerTerm(1, rSideVar, newPower));
                        newRight.add(new PlusMinusTerm(1, powUSub));
                    } else {
                        newRight.add(new PlusMinusTerm(ComplexNum.pow(constantVal, newPower)));
                    }
                } else {
                    loggy.logDetail("non even power");

                    if (rSideNeedsUSub) {
                        newRight.add(new PowerTerm(1, rSideVar, newPower));
                    } else {
                        newRight.add(new PowerTerm(ComplexNum.pow(constantVal, newPower)));
                    }
                }

                /* Left Side */
                powTerm.setPower(1);
                setUSubToLSide(powTerm);
                break;
            default:
                break;
        }

        // lSide = newLeft;
        rSide = newRight;
        logSides();
    }
    // #endregion

    // #region multiply()
    /**
     * When <b>lSide</b> only has one term, multiplies whatever is in the
     * denominator to the other side. Implemented:
     * <ul>
     * <li>Division of coefficient
     * <li>TODO: Division of another var
     * </ul>
     */
    private static final void multiply() {
        if (lSide.size() != 1) {
            return;
        }

        Term lTerm = lSide.get(0);
        if (!lTerm.containsVar(varToSolve) || lTerm.getCoef().equals(1)) {
            return;
        }

        /* Right Side */
        opPerformed = true;
        if (rSide.size() == 1) {
            Term rTerm = rSide.get(0);

            // TODO: what if the divisor is another var?

            /* Division of Coef */
            rTerm.divideCoefBy(lTerm.getCoef());

        } else {
            for (Term r : rSide) {
                // TODO: what if the divisor is another var?

                /* Division of Coef */
                r.divideCoefBy(lTerm.getCoef());
            }

        }

        /* Left Side */
        switch (lTerm.getTermType()) {
            case POWER:
                PowerTerm powTerm = (PowerTerm) lTerm;
                if (powTerm.getPower().equals(1)) {
                    setUSubToLSide(lTerm);
                }
                break;
            default:
                break;
        }
        lTerm.setCoef(1);

        loggy.logDetail("multiplication");
        logSides();

    }
    // #endregion

    // #region add()
    /**
     * 
     */
    private static final void add() {
        /* Check if addition is needed */
        if (lSide.size() == 1) {
            if (lSide.get(0).containsVar(varToSolve)) {
                // incase lSide is a constant or some other term
                return;
            }
        }

        ArrayList<Term> newLeft = new ArrayList<Term>();
        ArrayList<Term> newRight = new ArrayList<Term>();

        for (Term i : rSide) {
            if (i.containsVar(varToSolve)) {
                newLeft.add(i.negate());
            } else {
                newRight.add(i);
            }
        }

        for (Term i : lSide) {
            if (i.containsVar(varToSolve)) {
                newLeft.add(i);
            } else {
                newRight.add(i.negate());
            }
        }

        // if nothing changed, then do not continue
        if (newLeft.equals(lSide) && newRight.equals(rSide)) {
            return;
        }
        opPerformed = true;

        lSide = TermUtils.AddTerms.performAddition(newLeft);
        rSide = TermUtils.AddTerms.performAddition(newRight);

        loggy.logDetail("addition");
        // loggy.logDetail("newLeft: " + newLeft + " newRight: " + newRight);
        logSides();
    }

    // #endregion

    // #region U-Sub
    /**
     * If <b>lTerm</b> contains a u-subbed variable, then it sets the left side to
     * match that.
     * 
     * @param lTerm
     */
    private static final void setUSubToLSide(Term lTerm) {
        switch (lTerm.getVar().getVarType()) {
            case U_SUB_EQ:
                MathEQ inner = lTerm.getVar().getInnerFunction();
                // if (varToSolve.isVarType(VarType.U_SUB_EQ)) {

                // } else {
                if (inner.isType(EQType.ADDITIVE)) {
                    lSide = inner.getTerms();
                } else {
                    // TODO: foil instead?
                    USub uSub = new USub(lTerm);
                    lSide = new PowerTerm(1, uSub).toTermArray();
                }
                // }
                break;
            case U_SUB_TERM:
                Term innerTerm = lTerm.getVar().getInnerTerm();
                lSide = innerTerm.toTermArray();
                break;
            default:
                lSide = lTerm.toTermArray();
                break;
        }

    }
    // #endregion

    // #region Solutions
    /**
     * If the right side contains all constant terms, then get all solutions from
     * there.
     * 
     * @param rightSide
     * @return
     */
    private static final ArrayList<ComplexNum> getSolutions(ArrayList<Term> rightSide) {
        /* Term Types */
        ArrayList<Term> pmTerms = EquationUtils.getTermTypeFromArray(rightSide, TermType.PLUS_MINUS);
        ArrayList<Term> otherTerms = EquationUtils.getTermsExcludingType(rightSide, TermType.PLUS_MINUS);

        ComplexNum total = new ComplexNum(0, 0);
        for (Term i : otherTerms) {
            total = ComplexNum.add(total, i.getCoef());
        }

        if (pmTerms.size() == 0) {
            return total.toArrayList();
        }

        /* Plus Minus specifics */
        ArrayList<ComplexNum> output = new ArrayList<ComplexNum>();
        ComplexNum staticCoef = pmTerms.get(0).getCoef(); // the coef which will be unchanged
        if (pmTerms.size() == 1) {
            output.add(ComplexNum.add(total, staticCoef));
            output.add(ComplexNum.add(total, staticCoef.negate()));

        } else {
            // for (int i = 0; i < )

        }

        return output;

    }

    private static final ArrayList<ComplexNum> extraneousSolutions(ArrayList<Term> left, ArrayList<Term> right) {
        ArrayList<ComplexNum> potentialSols = new AdditiveEQ(rSide).solutions().getNumbers();
        loggy.logHeader("---- Extraneous Solutions ----");
        loggy.logVal("potential solutions", potentialSols);

        /* Original EQs */
        AdditiveEQ lEQ = new AdditiveEQ(left);
        AdditiveEQ rEQ = new AdditiveEQ(right);

        ArrayList<ComplexNum> actualSols = new ArrayList<ComplexNum>();
        for (ComplexNum i : potentialSols) {
            ComplexNum lSol = lEQ.solve(i);
            ComplexNum rSol = rEQ.solve(i);

            if (lSol.equals(rSol)) {
                actualSols.add(i);
            }

            loggy.logDetail("solution " + i + "\n" + lSol + " = " + rSol);
        }

        return actualSols;
    }
    // #endregion

    // #region Telemetry Methods
    /**
     * Prints both sides of the working equation.
     * {@code System.out.println("new eq: " + lSide + " = " + rSide)}
     */
    private static final void logSides() {
        loggy.log("new eq: " + lSide + " = " + rSide);
    }

    // #endregion

    // #region Variable Bools
    private static final boolean isVarIsolated() {
        if (lSide.size() != 1) {
            return false;
        }
        Term term = lSide.get(0);

        // TODO: what if the var is usub?

        /* If actual var */
        if (!term.isTermType(TermType.POWER)) {
            return false;
        }

        PowerTerm powTerm = (PowerTerm) term;
        if (!powTerm.getPower().equals(1) || !powTerm.getCoef().equals(1)) {
            return false;
        }
        return true;
    }

    private static final boolean isMultivariate(ArrayList<Term> rightSide) {
        for (Term i : rightSide) {
            if (!i.containsVar(Variable.noVar)) {
                return true;
            }
        }
        return false;
    }

    private static final boolean sideContainsVar(ArrayList<Term> terms) {
        for (Term i : terms) {
            if (i.containsVar(varToSolve)) {
                return true;
            }
        }
        return false;
    }
    // #endregion

}
