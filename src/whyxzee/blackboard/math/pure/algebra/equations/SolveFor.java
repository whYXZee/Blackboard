package whyxzee.blackboard.math.pure.algebra.equations;

import java.util.ArrayList;

import whyxzee.blackboard.math.pure.equations.EquationUtils;
import whyxzee.blackboard.math.pure.equations.MathEQ;
import whyxzee.blackboard.math.pure.algebra.AlgebraUtils;
import whyxzee.blackboard.math.pure.equations.AdditiveEQ;
import whyxzee.blackboard.math.pure.equations.MathEQ.EQType;
import whyxzee.blackboard.math.pure.numbers.BNumber;
import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.applied.settheory.DefinedList;
import whyxzee.blackboard.math.pure.terms.PlusMinusTerm;
import whyxzee.blackboard.math.pure.terms.PowerTerm;
import whyxzee.blackboard.math.pure.terms.Term;
import whyxzee.blackboard.math.pure.terms.TermUtils;
import whyxzee.blackboard.math.pure.terms.Term.TermType;
import whyxzee.blackboard.math.pure.terms.variables.USub;

/**
 * Sets two Sequential Functions equal to each other and then solves for a given
 * variable.
 * 
 * <p>
 * The functionality of this class has been checked on <b>6/14/2025</b>, and
 * the following has changed since:
 * <ul>
 * <li>inverse() - except Power
 * <li>complex number implementation for inverse
 * <li>imaginary number implementation for inverse
 */
public class SolveFor {
    /* Variables */
    private static final boolean telemetryOn = Constants.TelemetryConstants.SOLVE_FOR_TELEMETRY;
    private static ArrayList<Term> leftSide = new ArrayList<Term>(); // will have the variable
    private static ArrayList<Term> rightSide = new ArrayList<Term>();
    private static String varToFind = "";
    /**
     * If nothing is performed, then mark true to check for special operations (such
     * as completing the square, rational root theorem, etc.)
     */
    private static boolean somethingPerformed;

    public static final DefinedList performOp(String var, AdditiveEQ left, AdditiveEQ right) {
        /* Var declarations */
        varToFind = var;
        DefinedList allSols = null;
        leftSide = EquationUtils.deepCopyTerms(left.getTerms());
        rightSide = EquationUtils.deepCopyTerms(right.getTerms());

        int i = 1;
        while (!isVarIsolated()) {
            if (telemetryOn) {
                System.out.println("----iteration: " + i + "----");
                // System.out.println("original eq: " + left + " = " + right);
                System.out.println("new eq: " + leftSide + " = " + rightSide);
            }

            /* Arithmetic */
            somethingPerformed = false;
            inverse();
            multiplication();
            addition();

            if (somethingPerformed == false) {
                allSols = powerOps();
                if (allSols != null) {
                    break;
                }
            }

            if (i > 6) {
                break;
            }
            i++;
        }

        if (allSols == null) {
            allSols = getSolutions();
        }
        return extraneousSolutions(allSols, left, right);
    }

    ///
    /// Abstracting the Problem
    ///
    private static final void inverse() {
        // TODO: implement usubbed terms and functions
        if (leftSide.size() != 1) {
            return;
        }

        Term lTerm = leftSide.get(0);
        if (!lTerm.getCoef().equals(1)) {
            return;
        }

        /* Telemetry */
        if (telemetryOn)
            System.out.println("inverse");

        /* Algorithm */
        somethingPerformed = true;
        ArrayList<Term> newRight = new ArrayList<Term>();
        ArrayList<Term> newLeft = new ArrayList<Term>();
        switch (lTerm.getTermType()) {
            case POWER:
                PowerTerm powTerm = (PowerTerm) lTerm;
                BNumber lPower = powTerm.getPower();
                if (lPower.equals(-1)) {
                    /* Telemetry */
                    if (telemetryOn)
                        System.out.println("- power of -1");

                    /* Arithmetic */
                    // TODO: inverse later
                    break;
                }

                boolean rSideNeedsUSub = true;
                USub rSide = new USub(new AdditiveEQ(rightSide));
                BNumber constantVal = rightSide.get(0).getCoef();
                if (rightSide.size() == 1) {
                    rSideNeedsUSub = false;
                }
                BNumber newPower = lPower.reciprocal();
                // TODO: roots of complex number

                if (lPower.mod(2).equals(0)) {
                    /* Telemetry */
                    if (telemetryOn)
                        System.out.println("- even power");

                    /* Arithmetic */
                    if (rSideNeedsUSub) {
                        USub powUSub = new USub(new PowerTerm(1, rSide, newPower));
                        newRight.add(new PlusMinusTerm(1, powUSub));
                    } else {
                        newRight.add(new PlusMinusTerm(BNumber.pow(constantVal, newPower)));
                    }
                } else {
                    /* Telemetry */
                    if (telemetryOn)
                        System.out.println("- non even power");

                    /* Arithmetic */
                    if (rSideNeedsUSub) {
                        newRight.add(new PowerTerm(1, rSide, newPower));
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

        leftSide = newLeft;
        rightSide = newRight;
    }

    private static final void multiplication() {
        if (leftSide.size() != 1) {
            return;
        }

        Term lTerm = leftSide.get(0);
        if (lTerm.getCoef().equals(1)) {
            return;
        }

        /* Algorithm */
        if (lTerm.getVar().containsVar(varToFind)) {
            somethingPerformed = true;
            if (!lTerm.getCoef().equals(new BNumber(1, 0))) {
                BNumber coef = lTerm.getCoef();
                lTerm.setCoef(1);
                rightSide.get(0).divideCoefBy(coef);
            }

            switch (lTerm.getTermType()) {
                case POWER:
                    PowerTerm powTerm = (PowerTerm) lTerm;
                    if (powTerm.getPower().equals(1)) {
                        setUSubToLSide(lTerm);
                    }
                    break;
                case PLUS_MINUS:
                default:
                    break;
            }

            /* Telemetry */
            if (telemetryOn)
                System.out.println("multiplication\nnew eq: " + leftSide + " = " + rightSide);
        }

    }

    private static final void addition() {
        if (leftSide.size() == 1) {
            return;
        }

        /* Algorithm */
        ArrayList<Term> newLeft = new ArrayList<Term>();
        ArrayList<Term> newRight = new ArrayList<Term>();
        for (Term i : rightSide) {
            if (i.getVar().containsVar(varToFind)) {
                newLeft.add(i.negate());
            } else {
                newRight.add(i);
            }
        }

        for (Term i : leftSide) {
            if (i.getVar().containsVar(varToFind)) {
                // is the var
                newLeft.add(i);
            } else {
                newRight.add(i.negate());
            }
        }

        // if nothing changed, then do not continue
        if (newLeft.equals(leftSide) && newRight.equals(newRight)) {
            return;
        }
        somethingPerformed = true;

        rightSide = TermUtils.AddTerms.performAddition(newRight);
        leftSide = TermUtils.AddTerms.performAddition(newLeft);

        /* Telemetry */
        if (telemetryOn)
            System.out.println("addition\nnew eq: " + leftSide + " = " + rightSide);
    }

    /**
     * If the system is a power equation, then it performs special operations on the
     * equation in the following order:
     * <ol>
     * <li>isQuadratic() -> Completing the Square
     * <li>Rational Root Theorem
     */
    private static final DefinedList powerOps() {
        ArrayList<Term> terms = EquationUtils.deepCopyTerms(leftSide);
        for (Term i : rightSide) {
            terms.add(i.negate());
        }

        if (AlgebraUtils.isQuadratic(terms)) {
            return AlgebraUtils.completeSquare(varToFind, terms, new BNumber(0, 0));

        } else if (AlgebraUtils.isPolynomial(terms)) {
            // if (AlgebraUtils.needsFactoring(terms)) {

            // }
            DefinedList list = AlgebraUtils.RationalRoot.performOp(terms.get(0).getCoef(),
                    terms.get(terms.size() - 1).getCoef());
            list.setSetName(varToFind);
            return list;
        }
        return null;
    }

    private static final void setUSubToLSide(Term lTerm) {
        switch (lTerm.getVar().getVarType()) {
            case U_SUB_EQ:
                MathEQ inner = lTerm.getVar().getInnerFunction();
                if (inner.isType(EQType.ADDITIVE)) {
                    leftSide = inner.getTerms();
                }
                break;
            case U_SUB_TERM:
                break;
            default:
                break;
        }
    }

    ///
    /// Get & Set Methods
    ///
    private static final DefinedList getSolutions() {
        DefinedList list = new AdditiveEQ(rightSide).solutions();
        list.setSetName(varToFind);

        /* Telemetry */
        if (telemetryOn)
            System.out.println("All Solutions: " + list);

        return list;
    }

    private static final DefinedList extraneousSolutions(DefinedList list, AdditiveEQ left, AdditiveEQ right) {
        if (telemetryOn)
            System.out.println("----Extraneous Solutions----");

        ArrayList<BNumber> numbers = new ArrayList<BNumber>();
        for (BNumber i : list.getNumbers()) {
            /* Arithmetic */
            BNumber lNum = left.solve(i);
            BNumber rNum = right.solve(i);
            if (lNum.equals(rNum)) {
                numbers.add(i);
            }

            /* Telemetry */
            if (telemetryOn) {
                System.out.println("Number: " + i);
                System.out.println("- " + lNum + " = " + rNum);
            }
        }

        return new DefinedList(varToFind, numbers);
    }

    ///
    /// Boolean Methods
    ///
    /**
     * Checks if the left side of the equation is <b>variable</b> to the power of
     * one.
     * 
     * @return
     */
    private static final boolean isVarIsolated() {
        if (leftSide.size() != 1) {
            return false;
        }

        Term term = leftSide.get(0);
        if (!term.isTermType(TermType.POWER)) {
            return false;
        }

        PowerTerm powTerm = (PowerTerm) term;
        if (!powTerm.getPower().equals(1) || !powTerm.getCoef().equals(1)) {
            return false;
        }
        return true;
    }

    // private static final boolean completeSquareCriteria() {

    // }
}
