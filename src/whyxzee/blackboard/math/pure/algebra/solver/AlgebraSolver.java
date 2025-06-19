package whyxzee.blackboard.math.pure.algebra.solver;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.pure.equations.AdditiveEQ;
import whyxzee.blackboard.math.pure.equations.EquationUtils;
import whyxzee.blackboard.math.pure.equations.MathEQ;
import whyxzee.blackboard.math.pure.equations.MathEQ.EQType;
import whyxzee.blackboard.math.pure.numbers.BNumber;
import whyxzee.blackboard.math.pure.terms.PlusMinusTerm;
import whyxzee.blackboard.math.pure.terms.PowerTerm;
import whyxzee.blackboard.math.pure.terms.Term;
import whyxzee.blackboard.math.pure.terms.TermUtils;
import whyxzee.blackboard.math.pure.terms.Term.TermType;
import whyxzee.blackboard.math.pure.terms.variables.USub;
import whyxzee.blackboard.math.pure.terms.variables.Variable;
import whyxzee.blackboard.utils.Loggy;

/**
 * A general-use package for an algebraic solver. The following have been
 * implemented:
 * <ul>
 * <li>TODO: linear solution
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
    private static final Loggy loggy = new Loggy(Constants.LoggyConstants.ALGEBRA_SOLVER_LOGGY);
    private static int maxIterations = 4;

    private static ArrayList<Term> lSide; // will contain the info for the var
    private static ArrayList<Term> rSide;
    private static Variable varToSolve; // for now, assume normal var
    private static boolean opPerformed = false;

    /**
     * TODO: what if the leftSide and rightSide inputs are switched?
     * 
     * @param var
     * @param leftSide  the ArrayList<Term> of an AdditiveEQ.
     * @param rightSide the ArrayList<Term> of an AdditiveEQ.
     * @return
     */
    public static final SolutionData performOp(Variable var, ArrayList<Term> leftSide, ArrayList<Term> rightSide) {
        /* Variables */
        lSide = EquationUtils.deepCopyTerms(leftSide);
        rSide = EquationUtils.deepCopyTerms(rightSide);
        varToSolve = var;

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

        if (isMultivariate(rightSide)) {
            return new SolutionData(EquationUtils.simplifyAdditive(new AdditiveEQ(rSide)));
        }
        return new SolutionData(var, extraneousSolutions(leftSide, rightSide));
    }

    ///
    /// Abstracting the Problem
    ///
    // #region Inverse, Multiply, Add
    private static final void inverse() {
        if (lSide.size() != 1) {
            return;
        }

        Term lTerm = lSide.get(0);
        if (!lTerm.containsVar(varToSolve) || !lTerm.getCoef().equals(1)) {
            return;
        }

        opPerformed = true;
        ArrayList<Term> newLeft = new ArrayList<Term>();
        ArrayList<Term> newRight = new ArrayList<Term>();
        switch (lTerm.getTermType()) {
            case POWER:
                PowerTerm powTerm = (PowerTerm) lTerm;
                BNumber lPower = powTerm.getPower();
                if (lPower.equals(-1)) {
                    loggy.logDetail("power of -1");

                    /* Arithmetic */
                    // TODO: inverse later
                    break;
                }

                boolean rSideNeedsUSub = true;
                USub rSideVar = new USub(new AdditiveEQ(rSide));
                BNumber constantVal = rSide.get(0).getCoef();
                if (rSide.size() == 1) {
                    rSideNeedsUSub = false;
                }
                BNumber newPower = lPower.reciprocal();

                // TODO: roots of complex number

                if (lPower.mod(2).equals(0)) {
                    loggy.logDetail("even power");

                    if (rSideNeedsUSub) {
                        USub powUSub = new USub(new PowerTerm(1, rSideVar, newPower));
                        newRight.add(new PlusMinusTerm(1, powUSub));
                    } else {
                        newRight.add(new PlusMinusTerm(BNumber.pow(constantVal, newPower)));
                    }
                } else {
                    loggy.logDetail("non even power");

                    if (rSideNeedsUSub) {
                        newRight.add(new PowerTerm(1, rSideVar, newPower));
                    } else {
                        newRight.add(new PowerTerm(BNumber.pow(constantVal, newPower)));
                    }
                }

                /* Left Side */
                powTerm.setPower(1);
                switch (lTerm.getVar().getVarType()) {
                    case U_SUB_EQ:
                        MathEQ inner = lTerm.getVar().getInnerFunction();
                        if (inner.isType(EQType.ADDITIVE)) {
                            newLeft = inner.getTerms();
                        }
                        break;
                    case U_SUB_TERM:
                        break;
                    default:
                        newLeft.add(powTerm);
                        break;

                }
                break;
            default:
                break;
        }

        lSide = newLeft;
        rSide = newRight;

    }

    private static final void multiply() {
        if (lSide.size() != 1) {
            return;
        }

        Term lTerm = lSide.get(0);
        if (!lTerm.containsVar(varToSolve) || lTerm.getCoef().equals(1)) {
            return;
        }

        opPerformed = true;
        if (rSide.size() == 1) {
            Term rTerm = rSide.get(0);
            rTerm.divideCoefBy(lTerm.getCoef());

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

        } else {

        }

        lTerm.setCoef(1);

        loggy.logDetail("multiplication");
        logSides();

    }

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
                if (inner.isType(EQType.ADDITIVE)) {
                    lSide = inner.getTerms();
                } else {
                    USub uSub = new USub(lTerm);
                    lSide = new PowerTerm(1, uSub).toTermArray();
                }
                break;
            case U_SUB_TERM:
                Term innerTerm = lTerm.getVar().getInnerTerm();
                lSide = innerTerm.toTermArray();
                break;
            default:
                break;
        }

    }
    // #endregion

    // #region Solutions
    private static final ArrayList<BNumber> extraneousSolutions(ArrayList<Term> left, ArrayList<Term> right) {
        ArrayList<BNumber> potentialSols = new AdditiveEQ(rSide).solutions().getNumbers();
        loggy.logHeader("---- Extraneous Solutions ----");
        loggy.logVal("potential solutions", potentialSols);

        /* Original EQs */
        AdditiveEQ lEQ = new AdditiveEQ(left);
        AdditiveEQ rEQ = new AdditiveEQ(right);

        ArrayList<BNumber> actualSols = new ArrayList<BNumber>();
        for (BNumber i : potentialSols) {
            BNumber lSol = lEQ.solve(i);
            BNumber rSol = rEQ.solve(i);

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

    ///
    /// Boolean Methods
    ///
    // #region Boolean Methods
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
    // #endregion

}
