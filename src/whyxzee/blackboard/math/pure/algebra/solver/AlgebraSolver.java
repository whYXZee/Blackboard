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
import whyxzee.blackboard.math.pure.numbers.Complex;
import whyxzee.blackboard.math.utils.pure.NumberUtils;
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
     * 
     * @param var       the variable that should be found
     * @param leftSide  the TermArray of an AdditiveEQ.
     * @param rightSide the TermArray of an AdditiveEQ.
     * @return
     */
    public static final SolutionData performOp(String var, TermArray leftSide, TermArray rightSide) {
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

        TermArray otherTerms = rSide.getTermsExcluding(PlusMinusTerm.class); // all terms except plus-minus
        otherTerms.addition(); // ngl idk if this is needed

        if (!otherTerms.areAllConstants()) {
            if (!areSidesSwapped && isMultivariate(rightSide)) {
                return new SolutionData(var, new AdditiveEQ(rSide).toTerm());
            } else {
                // (areSidesSwapped && isMultivariate(leftSide))
                return new SolutionData(var, new AdditiveEQ(lSide).toTerm());
            }
        } else {
            return new SolutionData(var, extraneousSolutions(leftSide, rightSide));
        }
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

        /* Right Side */
        rSide = lTerm.applyInverseTo(new AdditiveEQ(rSide)).toTermArray();

        /* Left Side */
        setUSubToLSide(lTerm);
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
        lTerm.setCoef(1);
        setUSubToLSide(lTerm);

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

        for (PowerTerm i : rSide) {
            if (i.containsVar(varToSolve)) {
                newLeft.add(i.negate());
            } else {
                newRight.add(i);
            }
        }

        for (PowerTerm i : lSide) {
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

        newLeft.addition();
        newRight.addition();

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
    private static final ArrayList<Complex> getSolutions() {
        // by now, we know that all terms are constant.
        loggy.logHeader("Get Solutions");

        /* Term Types */
        TermArray otherTerms = rSide.getTermsExcluding(PlusMinusTerm.class);
        TermArray pmTerms = rSide.getTermsUnion(PlusMinusTerm.class);
        loggy.logVal("otherTerms", otherTerms);
        loggy.logVal("pmTerms", pmTerms);
        otherTerms.addition();
        Complex constant = otherTerms.getConstant().getCoef();

        if (pmTerms.isEmpty()) {
            return new ArrayList<Complex>() {
                {
                    add(constant);
                }
            };
        }

        /* Plus Minus specifics */
        ArrayList<Complex> allCombos = TermUtils.addConstantPlusMinusTerms(pmTerms);
        for (Complex i : allCombos) {
            i.copy(NumberUtils.add(i, constant));
        }

        return allCombos;
    }

    private static final ArrayList<Complex> extraneousSolutions(TermArray left,
            TermArray right) {
        ArrayList<Complex> potentialSols = getSolutions();
        loggy.logHeader("Extraneous Solutions");
        loggy.logVal("potential solutions", potentialSols);

        /* Original EQs */
        AdditiveEQ lEQ = new AdditiveEQ(left);
        AdditiveEQ rEQ = new AdditiveEQ(right);

        ArrayList<Complex> actualSols = new ArrayList<Complex>();
        for (Complex i : potentialSols) {
            PowerTerm lSol = lEQ.solve(varToSolve, new PowerTerm(i));
            PowerTerm rSol = rEQ.solve(varToSolve, new PowerTerm(i));

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
        for (PowerTerm i : rightSide) {
            if (!i.containsVar("")) {
                return true;
            }
        }
        return false;
    }

    private static final boolean sideContainsVar(TermArray terms) {
        for (PowerTerm i : terms) {
            if (i.containsVar(varToSolve)) {
                return true;
            }
        }
        return false;
    }
    // #endregion

}
