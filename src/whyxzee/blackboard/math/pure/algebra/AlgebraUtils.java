package whyxzee.blackboard.math.pure.algebra;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.applied.settheory.DefinedList;
import whyxzee.blackboard.math.pure.algebra.solver.*;
import whyxzee.blackboard.math.pure.combinatorics.CombinatoricsUtils;
import whyxzee.blackboard.math.pure.equations.AdditiveEQ;
import whyxzee.blackboard.math.pure.equations.MathEQ;
import whyxzee.blackboard.math.pure.equations.MultiplyEQ;
import whyxzee.blackboard.math.pure.equations.TermArray;
import whyxzee.blackboard.math.pure.equations.terms.PowerTerm;
import whyxzee.blackboard.math.pure.equations.variables.Variable;
import whyxzee.blackboard.math.pure.numbers.Complex;
import whyxzee.blackboard.math.pure.numbers.NumberTheory;
import whyxzee.blackboard.math.utils.pure.NumberUtils;
import whyxzee.blackboard.utils.Loggy;

/**
 * A general-use package for Algebra operations.
 * 
 * <p>
 * The functionality of this class has been checked on <b>6/16/2025</b> and
 * nothing has changed since.
 */
public class AlgebraUtils {
    /* Variables */
    private static final Loggy loggy = new Loggy(Constants.Loggy.ALGEBRA_UTILS_LOGGY);

    // #region Foil
    private static final AdditiveEQ foil(AdditiveEQ one, AdditiveEQ two) {
        TermArray outputTerms = new TermArray();
        TermArray oneArr = one.getTerms();
        TermArray twoArr = two.getTerms();

        for (PowerTerm i : oneArr) {
            for (PowerTerm j : twoArr) {
                PowerTerm addendTerm = new MultiplyEQ(i, j).toTerm();
                // Term addendTerm = new PowerTerm(1, new USub(innerEQ));
                outputTerms.add(addendTerm);
            }
        }

        return new AdditiveEQ(outputTerms);
    }

    public static final AdditiveEQ foil(AdditiveEQ... terms) {
        if (terms == null || terms.length == 0) {
            return new AdditiveEQ();
        } else if (terms.length == 1) {
            return terms[0];
        }

        AdditiveEQ current = foil(terms[0], terms[1]);
        for (int i = 2; i < terms.length; i++) {
            current = foil(current, terms[i]);
        }
        return current;

    }
    // #endregion

    // #region Binomial Theorem
    /**
     * 
     * @param one
     * @param two
     * @param power
     * @return
     */
    public static final AdditiveEQ binomTheorem(PowerTerm one, PowerTerm two, int power) {
        // TODO: negative powers?
        loggy.logHeader("Binomial Theorem with (" + one + " + " + two + ")^(" + power + ")");

        ArrayList<PowerTerm> outputArr = new ArrayList<PowerTerm>();
        for (int k = 0; k <= power; k++) {
            /* Arithmetic */
            PowerTerm oneFactor = one.clone();
            oneFactor.toPower(power - k);
            PowerTerm twoFactor = two.clone();
            twoFactor.toPower(k);
            Complex coef = CombinatoricsUtils.combination(power, power - k);

            MultiplyEQ innerEQ = new MultiplyEQ(new PowerTerm(coef), oneFactor, twoFactor);
            outputArr.add(innerEQ.toTerm());

            loggy.log("k = " + k + ", termA: " + oneFactor + " termB: " + twoFactor + " coef: " + coef);
        }

        return new AdditiveEQ(outputArr);
    }

    /**
     * 
     * @param one
     * @param two
     * @param power
     * @return
     */
    public static final AdditiveEQ binomTheorem(PowerTerm one, PowerTerm two, Complex power) {
        // TODO: non-integer, complex, imaginary implementation
        if (power.isReal() && power.mod(1).equals(0)) {
            return binomTheorem(one, two, (int) power.getA().getValue());
        }

        throw new UnsupportedOperationException(
                "non-integer, complex, and imaginary powers are unimplemented for Binomial Theorem");
    }
    // #endregion

    // #region Quadratics
    /**
     * The following have been implemented:
     * <ul>
     * <li>TODO: complex/imaginary numbers
     * <li>TODO: (test) Uncountables
     * <li>TODO: (test) Irrational numbers
     * <li>TODO: (test) DNE
     * <ul>
     * 
     * @param varToSolve
     * @param leftSide
     * @param rightSide
     * @return
     */
    public static final SolutionData completeSquare(String varToSolve, TermArray leftSide, Complex rightSide) {
        /* Initializing variables */
        Complex a = leftSide.get(0).getCoef(); // first term is highest power
        PowerTerm term = leftSide.get(1);
        Complex b = term.getCoef();
        Complex c = leftSide.get(2).getCoef();
        Complex square;
        Complex rightAddend;

        if (a.equals(1)) {
            square = b.clone();
            square.multiplyScalar(0.5);
            rightAddend = NumberUtils.pow(square.clone(), 2);

        } else {
            Complex bPrime = NumberUtils.divide(b.clone(), a);
            square = bPrime.clone();
            square.multiplyScalar(0.5);
            rightAddend = NumberUtils.pow(square.clone(), 2);
            rightAddend = NumberUtils.multiply(rightAddend, a);
        }

        Complex rSide = NumberUtils.add(rightSide, c.negate(), rightAddend);
        term.setCoef(1);
        AdditiveEQ binom = new AdditiveEQ(term, new PowerTerm(square));

        return AlgebraSolver.performOp(varToSolve,
                new PowerTerm(a, new Variable<MathEQ>(binom), 2).toTermArray(), rSide.toTerm().toTermArray());
    }

    /**
     * TODO: complex/imaginary numbers?
     * 
     * @param terms
     * @return
     */
    public static final boolean isQuadratic(TermArray terms) {
        loggy.logHeader("isQuadratic(" + terms + ")");

        // quadratics have a maximum of 3 terms
        if (terms.size() > 3) {
            loggy.log("Quadratic size too large");
            return false;
        }

        // TODO: what if the array has less than 3 terms?
        // quadratics have at least 2 power terms
        Complex[] powOfTerms = new Complex[3];
        for (int i = 0; i < 3; i++) {
            try {
                powOfTerms[i] = ((PowerTerm) terms.get(i)).getPower();
            } catch (java.lang.ClassCastException e) {
                // could be something like a trig term, but the first one would be a power with
                // u-sub
                powOfTerms[i] = new Complex(1, 0);
            }
        }
        if (!powOfTerms[2].equals(0)) {
            // constant must have a power of 0
            return false;
        }

        // TODO: what if it is 1 + 2x^(-1) + x^(-2)?

        // the power of term 1 must be two times the power of the second
        if (!NumberUtils.divide(powOfTerms[0], powOfTerms[1]).equals(2)) {
            return false;
        }

        return true;
    }

    // #endregion

    // #region Rational Root
    /**
     * A general-use class for the Rational Root Theorem.
     * 
     * <p>
     * The functionality of this class has not been checked and the following need
     * to be checked:
     * <ul>
     * <li>complex/imaginary functionalty?
     */
    public static class RationalRoot {
        private static final Loggy rLoggy = new Loggy(Constants.Loggy.RATIONAL_ROOT_LOGGY);
        /* Variables */
        private static ArrayList<Complex> uniqueRoots = new ArrayList<Complex>();

        public static final DefinedList performOp(Complex q, Complex p) {
            uniqueRoots = new ArrayList<Complex>();
            ArrayList<Complex> qFactors = NumberTheory.Factors.factorsOf(q);
            ArrayList<Complex> pFactors = NumberTheory.Factors.factorsOf(p);

            rLoggy.logHeader("Rational Root Theorem with q = " + q + " and p = " + p + "\nqFactors: "
                    + qFactors + " p factors: " + pFactors + "");

            for (Complex denom : qFactors) {
                for (Complex num : pFactors) {
                    Complex factor = NumberUtils.divide(num, denom);
                    if (!uniqueRoots.contains(factor)) {
                        uniqueRoots.add(factor);
                    }
                    if (!uniqueRoots.contains(factor.negate())) {
                        uniqueRoots.add(factor.negate());
                    }
                }
            }

            rLoggy.logVal("combined factors", uniqueRoots);

            return new DefinedList("", uniqueRoots);
        }
    }
    // #endregion

    // #region Polynomials
    public static final boolean isPolynomial(TermArray terms) {
        loggy.logHeader("isPolynomial(" + terms + ")---");

        Complex[] powOfTerms = new Complex[terms.size()];
        int numOfOnePows = 0;
        for (int i = 0; i < terms.size(); i++) {
            try {
                powOfTerms[i] = ((PowerTerm) terms.get(i)).getPower();
            } catch (java.lang.ClassCastException e) {
                // could be something like a trig term, but the first one would be a power with
                // u-sub
                powOfTerms[i] = new Complex(1, 0);
            }

            /* Tracking the number of terms with power of one */
            if (powOfTerms[i].equals(1)) {
                numOfOnePows++;
            }
            if (numOfOnePows > 1) {
                // there can only be one term with the power of one
                loggy.log("More than one term with the power of one.");
                return false;
            }
        }

        // all powers must be integers.
        for (Complex i : powOfTerms) {
            if (!NumberTheory.isInteger(i)) {
                loggy.log("One of the powers is not an integer.");
                return false;
            }
        }
        return true;
    }

    // #endregion

    // #region Factoring
    public static final boolean needsFactoring(TermArray terms) {
        // TODO: needsFactoring()
        return true;
    }
    // #endregion
}
