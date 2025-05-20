package whyxzee.blackboard.terms;

import java.util.ArrayList;

import whyxzee.blackboard.equations.EQMultiplication;
import whyxzee.blackboard.terms.variables.USub;
import whyxzee.blackboard.terms.variables.Variable;

/**
 * The package for an exponential term, where the term is constructed as
 * num*base^variable.
 * 
 * <p>
 * The package is constructed as an y=b^x equation.
 * 
 * <p>
 * The methods in this class have not been checked.
 */
public class ExponentialTerm extends Term {
    /* Variables */
    private double base;

    /**
     * 
     * @param num  the constant in front of the exponent
     * @param var  the variable in the exponent
     * @param base the base of the exponent
     */
    public ExponentialTerm(double num, Variable var, double base) {
        super(num, var, TermType.EXPONENTIAL);
        this.base = base;
    }

    @Override
    public String toString() {
        return Double.toString(getNum()) + base + getVar();
    }

    @Override
    public String printConsole() {
        return getNum() + base + "^(" + getVar().printConsole() + ")";
    }

    //
    // Get and Set Methods
    //
    public double getBase() {
        return base;
    }

    //
    // Static Arithmetic
    //

    /**
     * Multiplies n exponential terms together. The variable power does not matter,
     * while the variable itself does. Used for non-special variables (no u-sub,
     * factorials, multivariate).
     * 
     * <p>
     * This method can also be used for division.
     * 
     * @param varLetter The letter of the variable to use for the output.
     * @param factors   The terms being multiplied together.
     * @return
     */
    public static ExponentialTerm multiply(String varLetter, ArrayList<ExponentialTerm> factors) {
        int numOfFactors = factors.size();

        if (numOfFactors == 1) {
            // if only one factor
            return factors.get(0);
        } else {
            // if n factors

            /* Initializing variables */
            double number = 1;
            Variable variable = new Variable(varLetter, 0);
            int numPower = variable.getNumeratorPower();
            int denomPower = variable.getDenominatorPower();

            for (ExponentialTerm factor : factors) {
                /* Factor variable */
                int factorNumPower = factor.getVar().getNumeratorPower();
                int factorDenomPower = factor.getVar().getDenominatorPower();

                /* Update the values */
                number *= factor.getNum();
                if (denomPower != factorDenomPower) {
                    // implement later (and check functionality later)
                } else {
                    // if denominators are the same
                    variable.setPower(numPower + factorNumPower, denomPower);
                }
                numPower = variable.getNumeratorPower();
                denomPower = variable.getDenominatorPower();
            }

            return new ExponentialTerm(number, variable, factors.get(0).getBase());
        }
    }

    //
    // Arithmetic Methods
    //
    @Override
    public double solve(double value) {
        return getNum() * Math.pow(base, getVar().solve(value));
    }

    @Override
    public Term negate() {
        return new ExponentialTerm(-1 * getNum(), getVar(), base);
    }

    @Override
    public Term derive() {
        /* Initiating variables */
        double number = getNum();
        Variable variable = getVar().clone();

        /* The coefficient */
        if (base != Math.E) {
            number *= Math.log(base); // should I multiply to constant or to an eq?
        }

        /* The variable */
        if (variable.needsChainRule()) {
            // chain rule
            EQMultiplication eq = new EQMultiplication(
                    // outer function (b^x)
                    new ExponentialTerm(1, variable, base),

                    // inner function (x)
                    variable.derive());

            return new PolynomialTerm(number, new USub(1, eq));
        } else {
            // no chain rule
            return new ExponentialTerm(number, variable, base);
        }
    }

    @Override
    public double limInfSolve() {
        /* Without respect to the variable */
        /* Initializing variables */
        double number = getNum();

        /* Algorithm */
        if (number == 0) {
            return 0;
        }

        if (base < 1) {
            // -1 < b < 1
            // converging
            return 0;
        } else if (base == 1) {
            // b = 1
            return number;
        } else if (base >= -1) {
            // negative base less than than or equal to -1
            // alternating
            return Double.NaN;
        } else {
            return Double.POSITIVE_INFINITY;
        }
    }

    public double limNegInfSolve() {
        /* Initializing variables */
        double number = getNum();

        /* Algorithm */
        if (number == 0) {
            return 0;
        }

        if (base < 1) {
            // -1 < b < 1
            return Double.POSITIVE_INFINITY;
        } else if (base == 1) {
            // b = 1
            return number;
        } else if (base > -1) {
            // negative base less than to -1
            // converging alternating
            return 0;
        } else {
            // negative base greater than or equal to -1
            return Double.NaN;
        }
    }
}
