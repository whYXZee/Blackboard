package whyxzee.blackboard.terms;

import java.util.ArrayList;

import whyxzee.blackboard.equations.EQMultiplication;
import whyxzee.blackboard.terms.variables.USub;
import whyxzee.blackboard.terms.variables.Variable;
import whyxzee.blackboard.utils.ArithmeticUtils;

/**
 * The package for a polynomial term. The term is a*x^n.
 * 
 * <p>
 * The package is contructed as an y=x^n equation.
 * 
 * <p>
 * The functionality of this class was checked on {@code 5/16/2025}, and the
 * following has changed since then:
 * <ul>
 * <li>multiply()
 * <li>solve()
 */
public class PolynomialTerm extends Term {
    //
    // General use: static
    //
    public static final PolynomialTerm ZERO_TERM = new PolynomialTerm(0);

    //
    // Variables
    //

    //
    // Object-related Methods
    //

    /**
     * The constructor class for a polynomial term
     * 
     * @param num the coefficient
     * @param var the variable
     */
    public PolynomialTerm(double num, Variable var) {
        super(num, var, TermType.POLYNOMIAL);
    }

    /**
     * The constructor class for a constant.
     * 
     * @param num the constant
     */
    public PolynomialTerm(double num) {
        super(num, Variable.noVar, TermType.POLYNOMIAL);
    }

    /**
     * Prints the polynomial term. If the number is 0, then nothing will be printed.
     */
    @Override
    public String toString() {
        if (getNum() == 0) {
            return "";
        } else if (getVar().getNumeratorPower() == 0) {
            return Double.toString(getNum());
        } else {
            if (getNum() == 1) {
                return getVar().toString();
            } else {
                return getNum() + getVar().toString();
            }
        }
    }

    @Override
    public String printConsole() {
        if (getNum() == 0) {
            return "0";
        } else if (getVar().getNumeratorPower() == 0) {
            return Double.toString(getNum());
        } else if (getNum() == 1) {
            return getVar().printConsole();
        } else {
            return getNum() + getVar().printConsole();
        }
    }

    //
    // Static Arithmetic
    //

    /**
     * Adds n polynomial terms together. The first addened is used for the variable,
     * so all addends with similar variable and power are added together.
     * 
     * <p>
     * This method can also be used for subtraction.
     * 
     * @param addends The terms being added into the first term in the ArrayList.
     *                The variables need to be equal.
     * @return
     */
    public static PolynomialTerm add(ArrayList<PolynomialTerm> addends) {
        int numOfAddends = addends.size();

        if (numOfAddends == 1) {
            return addends.get(0);
        } else {
            Variable variable = addends.get(0).getVar();
            double number = 0;

            for (PolynomialTerm addend : addends) {
                number += addend.getNum();
            }

            return new PolynomialTerm(number, variable);
        }
    }

    /**
     * Multiplies n polynomial terms together. The variable power does not matter,
     * while the variable itself does. The variables must be the same. Used for
     * non-special variables (no u-sub,
     * factorials, multivariate).
     * 
     * <p>
     * This method can also be used for division.
     * 
     * @param varLetter The letter of the variable
     * @param factors   The terms being multiplied together.
     * @return
     */
    public static PolynomialTerm multiply(String varLetter, ArrayList<PolynomialTerm> factors) {
        int numOfFactors = factors.size();

        if (numOfFactors == 1) {
            return factors.get(0);
        } else {
            // "Blank" variables to be iterated upon
            double number = 1;
            Variable variable = new Variable(varLetter, 0) {

            };

            int numPower = variable.getNumeratorPower();
            int denomPower = variable.getDenominatorPower();

            /* If the variable is not u-sub / multivariate */
            for (PolynomialTerm factor : factors) {
                numPower = variable.getNumeratorPower();
                denomPower = variable.getDenominatorPower();

                int factorNumPower = factor.getVar().getNumeratorPower();
                int factorDenomPower = factor.getVar().getDenominatorPower();

                number *= factor.getNum();

                if (denomPower != factorDenomPower) {
                    // implement later (and check functionality later)
                } else {
                    // if denominators are the same
                    variable.setPower(numPower + factorNumPower, denomPower);
                }
            }
            return new PolynomialTerm(number, variable);
        }
    }

    //
    // Object Arithmetics
    // As in arithmetics applied to the instance/term itself. Ordered from simple to
    // complex.

    /**
     * Inputs a value into the polynomial, as if it was a function.
     * 
     * @param value the value of the variable
     * @return
     */
    public double solve(double value) {
        Variable variable = getVar();
        return getNum() * Math.pow(variable.solve(value),
                (double) variable.getNumeratorPower() / variable.getDenominatorPower());
    }

    public Term negate() {
        return new PolynomialTerm(-1 * getNum(), getVar());
    }

    /**
     * Applies the derivative power rule to a mono-variate polynomial term.
     * 
     * @param degree how many times the derivative should be taken.
     * @return
     */
    @Override
    public Term derive() {
        double number = getNum();
        Variable variable = getVar().clone();
        int numPower = variable.getNumeratorPower();
        int denomPower = variable.getDenominatorPower();

        /* Number */
        if (numPower == 0) {
            // Derivative of a constant is 0
            return PolynomialTerm.ZERO_TERM;
        }

        /* Function */
        if (variable.needsChainRule()) {
            // chain rule
            if (numPower == 1 && denomPower == 1) {
                return variable.derive();
            }
            EQMultiplication eq = new EQMultiplication(
                    // outer function (x^n)
                    new PolynomialTerm((double) numPower / denomPower,
                            variable.setPower(numPower - denomPower, denomPower)),

                    // inner function (x)
                    variable.derive());
            return new PolynomialTerm(1, new USub(1, eq));

        } else {
            // no chain rule
            number *= (double) numPower / denomPower;
            variable.setPower(numPower - denomPower, denomPower);
            return new PolynomialTerm(number, variable);
        }
    }

    @Override
    public double limInfSolve() {
        /* Without respect to the variable */

        /* Number */
        double number = getNum();
        if (number == 0) {
            return 0;
        }
        boolean isNumberNegative = number < 0;

        /* Function */
        double power = getVar().getPower();
        if (power > 0) {
            // if the power is positive (in the numerator)
            return isNumberNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        } else if (power < 0) {
            // if the power is negative (in the denominator)
            return isNumberNegative ? -0 : 0;
        } else {
            // if the power is 0
            return number;
        }
    }

    @Override
    public double limNegInfSolve() {
        /* Without respect to the variable */

        /* Number */
        double number = getNum();
        if (number == 0) {
            return 0;
        }
        boolean isNumberNegative = number < 0;

        /* Function */
        double power = getVar().getPower();
        if (power > 0) {
            // if the power is positive (in the numerator)
            if (ArithmeticUtils.isEven(number)) {
                // even power
                return isNumberNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
            } else {
                // odd power
                return isNumberNegative ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
            }
        } else if (power < 0) {
            // if the power is negative (in the denominator)
            if (ArithmeticUtils.isEven(number)) {
                // even power
                return isNumberNegative ? -0 : 0;
            } else {
                // odd power
                return isNumberNegative ? 0 : -0;
            }
        } else {
            // if the power is 0
            return number;
        }
    }

}
