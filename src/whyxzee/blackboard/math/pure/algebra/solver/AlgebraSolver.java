package whyxzee.blackboard.math.pure.algebra.solver;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.pure.equations.AdditiveEQ;
import whyxzee.blackboard.math.pure.equations.MathEQ;
import whyxzee.blackboard.math.pure.equations.MathEQ.EQType;
import whyxzee.blackboard.math.pure.equations.TermArray;
import whyxzee.blackboard.math.pure.equations.terms.PlusMinusTerm;
import whyxzee.blackboard.math.pure.equations.terms.PowerTerm;
import whyxzee.blackboard.math.pure.equations.terms.TermUtils;
import whyxzee.blackboard.math.pure.equations.variables.Variable;
import whyxzee.blackboard.math.pure.numbers.ComplexNum;
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

    private static TermArray lSide; // will contain the info for the var
    private static TermArray rSide;
    private static String varToSolve;
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
    public static final SolutionData performOp(String var, TermArray leftSide,
            TermArray rightSide) {
        /* Variables */
        lSide = leftSide.clone();
        rSide = rightSide.clone();
        varToSolve = var;

        /* Swap Sides if Needed */
        if (!sideContainsVar(leftSide) && sideContainsVar(rightSide)) {
            TermArray temp = lSide;
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

        PowerTerm lTerm = lSide.get(0);
        if (!lTerm.containsVar(varToSolve) || !lTerm.getCoef().equals(1)) {
            return;
        }

        opPerformed = true;
        // ArrayList<Term> newLeft = new ArrayList<Term>();
        ArrayList<PowerTerm> newRight = new ArrayList<PowerTerm>();
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

        PowerTerm lTerm = lSide.get(0);
        if (!lTerm.containsVar(varToSolve) || lTerm.getCoef().equals(1)) {
            return;
        }

        /* Right Side */
        opPerformed = true;
        if (rSide.size() == 1) {
            PowerTerm rTerm = rSide.get(0);

            // TODO: what if the divisor is another var?

            /* Division of Coef */
            rTerm.divideCoefBy(lTerm.getCoef());

        } else {
            for (PowerTerm r : rSide) {
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

        TermArray newLeft = new TermArray();
        TermArray newRight = new TermArray();

        for (PowerTerm i : rSide.getArr()) {
            if (i.containsVar(varToSolve)) {
                newLeft.add(i.negate());
            } else {
                newRight.add(i);
            }
        }

        for (PowerTerm i : lSide.getArr()) {
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

        newLeft.add();
        newRight.add();

        lSide = newLeft;
        rSide = newRight;

        loggy.logDetail("addition");
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
    private static final void setUSubToLSide(PowerTerm lTerm) {
        Object inner = lTerm.getVar().getInner();

        if (inner instanceof MathEQ) {
            MathEQ eq = (MathEQ) inner;
            if (eq.isType(EQType.ADDITIVE)) {
                lSide = eq.getTerms();
            } else {
                // TODO: foil instead?
                lSide = eq.toTerm().toTermArray();
            }
        } else if (inner instanceof PowerTerm) {
            lSide = ((PowerTerm) inner).toTermArray();
        } else if (inner instanceof String) {
            lSide = lTerm.toTermArray();
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
    private static final ArrayList<ComplexNum> getSolutions(TermArray rightSide) {
        /* Term Types */
        TermArray pmTerms = rightSide.getTermsUnion(PlusMinusTerm.class);
        TermArray otherTerms = rightSide.getTermsExcluding(PlusMinusTerm.class);

        ComplexNum total = new ComplexNum(0, 0);
        for (PowerTerm i : otherTerms.getArr()) {
            total = ComplexNum.add(total, i.getCoef());
        }

        if (pmTerms.isEmpty()) {
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

    private static final ArrayList<ComplexNum> extraneousSolutions(ArrayList<PowerTerm> left,
            ArrayList<PowerTerm> right) {
        ArrayList<ComplexNum> potentialSols = getSolutions(rSide);
        loggy.logHeader("---- Extraneous Solutions ----");
        loggy.logVal("potential solutions", potentialSols);

        /* Original EQs */
        AdditiveEQ lEQ = new AdditiveEQ(left);
        AdditiveEQ rEQ = new AdditiveEQ(right);

        ArrayList<ComplexNum> actualSols = new ArrayList<ComplexNum>();
        for (ComplexNum i : potentialSols) {
            PowerTerm lSol = lEQ.solve(varToSolve, i);
            PowerTerm rSol = rEQ.solve(varToSolve, i);

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
        PowerTerm term = lSide.get(0);

        // TODO: what if the var is usub?

        /* If actual var */
        if (term.getClass() != PowerTerm.class || !term.getPower().equals(1) || !term.getCoef().equals(1)) {
            return false;
        }

        return true;
    }

    private static final boolean isMultivariate(TermArray rightSide) {
        for (PowerTerm i : rightSide.getArr()) {
            if (!i.containsVar(Variable.noVar)) {
                return true;
            }
        }
        return false;
    }

    private static final boolean sideContainsVar(TermArray terms) {
        for (PowerTerm i : terms.getArr()) {
            if (i.containsVar(varToSolve)) {
                return true;
            }
        }
        return false;
    }
    // #endregion

}
