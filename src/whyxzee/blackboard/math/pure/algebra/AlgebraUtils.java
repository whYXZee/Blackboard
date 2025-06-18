package whyxzee.blackboard.math.pure.algebra;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.applied.settheory.DefinedList;
import whyxzee.blackboard.math.pure.algebra.equations.SolveFor;
import whyxzee.blackboard.math.pure.combinatorics.CombinatoricsUtils;
import whyxzee.blackboard.math.pure.equations.AdditiveEQ;
import whyxzee.blackboard.math.pure.equations.EquationUtils;
import whyxzee.blackboard.math.pure.equations.MultiplyEQ;
import whyxzee.blackboard.math.pure.numbers.BNumber;
import whyxzee.blackboard.math.pure.numbers.NumberUtils;
import whyxzee.blackboard.math.pure.terms.PowerTerm;
import whyxzee.blackboard.math.pure.terms.Term;
import whyxzee.blackboard.math.pure.terms.variables.USub;

/**
 * A general-use package for Algebra operations.
 * 
 * <p>
 * The functionality of this class has been checked on <b>6/16/2025</b> and
 * nothing has changed since.
 */
public class AlgebraUtils {
    /* Variables */
    private static final boolean telemetryOn = Constants.TelemetryConstants.ALGEBRA_UTILS_TELEMETRY;

    ///
    /// Operations
    ///
    // #region Foil
    public static final AdditiveEQ foil(AdditiveEQ one, AdditiveEQ two) {
        ArrayList<Term> outputTerms = new ArrayList<Term>();
        ArrayList<Term> oneArr = one.getTerms();
        ArrayList<Term> twoArr = two.getTerms();

        for (Term i : oneArr) {
            for (Term j : twoArr) {
                MultiplyEQ innerEQ = new MultiplyEQ(i, j);
                Term addendTerm = EquationUtils.simplifyMultiply(innerEQ);
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
    public static final AdditiveEQ binomTheorem(Term one, Term two, int power) {
        // TODO: negative powers?
        /* Telemetry */
        if (telemetryOn)
            System.out.println("----Binomial Theorem with (" + one + " + " + two + ")^(" + power + ")----");

        ArrayList<Term> outputArr = new ArrayList<Term>();
        for (int k = 0; k <= power; k++) {
            /* Arithmetic */
            Term oneFactor = one.clone().toPower(power - k);
            Term twoFactor = two.clone().toPower(k);
            BNumber coef = CombinatoricsUtils.combination(power, power - k);

            MultiplyEQ innerEQ = new MultiplyEQ(new PowerTerm(coef), oneFactor, twoFactor);
            outputArr.add(EquationUtils.simplifyMultiply(innerEQ));

            /* Telemetry */
            if (telemetryOn) {
                System.out.println("k = " + k + ", termA: " + oneFactor + " termB: " + twoFactor + " coef: " + coef);
            }
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
    public static final AdditiveEQ binomTheorem(Term one, Term two, BNumber power) {
        // TODO: non-integer, complex, imaginary implementation
        if (power.isReal() && power.mod(1).equals(0)) {
            return binomTheorem(one, two, (int) power.getA());
        }

        throw new UnsupportedOperationException(
                "non-integer, complex, and imaginary powers are unimplemented for Binomial Theorem");
    }
    // #endregion

    /**
     * 
     * 
     * @param varToSolve
     * @param leftSide
     * @param rightSide
     * @return
     */
    public static final DefinedList completeSquare(String varToSolve, ArrayList<Term> leftSide, BNumber rightSide) {
        // TODO: complex/imaginary numbers?
        /* Initializing variables */
        BNumber a = leftSide.get(0).getCoef(); // first term is highest power
        Term term = leftSide.get(1);
        BNumber b = term.getCoef();
        BNumber c = leftSide.get(2).getCoef();
        BNumber square;
        BNumber rightAddend;

        if (a.equals(1)) {
            square = b.clone();
            square.multiplyScalar(0.5);
            rightAddend = square.clone();
            rightAddend.power(2);
        } else {
            BNumber bPrime = b.clone();
            bPrime.divide(a);
            square = bPrime.clone();
            square.multiplyScalar(0.5);
            rightAddend = square.clone();
            rightAddend.power(2);
            rightAddend.multiply(a);
        }

        rightSide.add(c.negate(), rightAddend);
        term.setCoef(1);
        AdditiveEQ binom = new AdditiveEQ(term, new PowerTerm(square));
        AdditiveEQ lSide = new AdditiveEQ(new PowerTerm(a, new USub(binom), 2));

        return SolveFor.performOp(varToSolve, lSide, new AdditiveEQ(rightSide.toTerm().toTermArray()));
    }

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
        private static final boolean telemetryOn = Constants.TelemetryConstants.RATIONAL_ROOT_TELEMETRY;
        /* Variables */
        private static ArrayList<BNumber> uniqueRoots = new ArrayList<BNumber>();

        public static final DefinedList performOp(BNumber q, BNumber p) {
            uniqueRoots = new ArrayList<BNumber>();
            ArrayList<BNumber> qFactors = NumberUtils.Factors.factorsOf(q);
            ArrayList<BNumber> pFactors = NumberUtils.Factors.factorsOf(p);

            /* Telemetry */
            if (telemetryOn) {
                System.out.println("----Rational Root Theorem with q = " + q + " and p = " + p + "\nqFactors: "
                        + qFactors + " p factors: " + pFactors + "----");
            }

            for (BNumber denom : qFactors) {
                for (BNumber num : pFactors) {
                    BNumber factor = num.clone();
                    factor.divide(denom);
                    if (!contains(factor)) {
                        uniqueRoots.add(factor);
                    }
                    if (!contains(factor.negate())) {
                        uniqueRoots.add(factor.negate());
                    }
                }
            }

            if (telemetryOn)
                System.out.println("combined factors: " + uniqueRoots);

            return new DefinedList("", uniqueRoots);
        }

        ///
        /// Boolean Methods
        ///
        private static final boolean contains(BNumber root) {
            for (BNumber i : uniqueRoots) {
                if (i.equals(root)) {
                    return true;
                }
            }
            return false;
        }

    }

    ///
    /// Boolean Methods
    ///
    public static final boolean isQuadratic(ArrayList<Term> terms) {
        // TODO: complex/imaginary numbers?
        /* Telemetry */
        if (telemetryOn)
            System.out.println("----isQuadratic(" + terms + ")---");

        // quadratics have a maximum of 3 terms
        if (terms.size() > 3) {
            /* Telemetry */
            if (telemetryOn)
                System.out.println("Quadratic size too large");

            /* Arithmetic */
            return false;
        }

        // TODO: what if the array has less than 3 terms?
        // quadratics have at least 2 power terms
        BNumber[] powOfTerms = new BNumber[3];
        for (int i = 0; i < 3; i++) {
            try {
                powOfTerms[i] = ((PowerTerm) terms.get(i)).getPower();
            } catch (java.lang.ClassCastException e) {
                // could be something like a trig term, but the first one would be a power with
                // u-sub
                powOfTerms[i] = new BNumber(1, 0);
            }
        }
        if (!powOfTerms[2].equals(0)) {
            // constant must have a power of 0
            return false;
        }

        // TODO: what if it is 1 + 2x^(-1) + x^(-2)?

        // the power of term 1 must be two times the power of the second
        if (!BNumber.divide(powOfTerms[0], powOfTerms[1]).equals(2)) {
            return false;
        }

        return true;
    }

    public static final boolean isPolynomial(ArrayList<Term> terms) {
        /* Telemetry */
        if (telemetryOn)
            System.out.println("----isQuadratic(" + terms + ")---");

        BNumber[] powOfTerms = new BNumber[terms.size()];
        int numOfOnePows = 0;
        for (int i = 0; i < terms.size(); i++) {
            try {
                powOfTerms[i] = ((PowerTerm) terms.get(i)).getPower();
            } catch (java.lang.ClassCastException e) {
                // could be something like a trig term, but the first one would be a power with
                // u-sub
                powOfTerms[i] = new BNumber(1, 0);
            }

            /* Tracking the number of terms with power of one */
            if (powOfTerms[i].equals(1)) {
                numOfOnePows++;
            }
            if (numOfOnePows > 1) {
                // there can only be one term with the power of one
                /* Telemetry */
                if (telemetryOn)
                    System.out.println("More than one term with the power of one.");

                /* Algorithm */
                return false;
            }
        }

        // all powers must be integers.
        for (BNumber i : powOfTerms) {
            if (!NumberUtils.isInteger(i)) {
                /* Telemetry */
                if (telemetryOn)
                    System.out.println("One of the powers is not an integer.");

                /* Algorithm */
                return false;
            }
        }
        return true;
    }

    public static final boolean needsFactoring(ArrayList<Term> terms) {
        // TODO: needsFactoring()
        return true;
    }

}
