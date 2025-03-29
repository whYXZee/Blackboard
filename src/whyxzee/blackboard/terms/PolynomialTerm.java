package whyxzee.blackboard.terms;

import java.util.ArrayList;

/**
 * The API for a polynomial term. The term is a*x^n.
 * 
 * <p>
 * The methods in this class have been checked on {@code 2/16/2025}, and nothing
 * has changed since then.
 */
public class PolynomialTerm extends Term {
    //
    // Variables
    //

    //
    // Object-related Methods
    //

    public PolynomialTerm(double num, Variable var) {
        super(num, var, TermType.POLYNOMIAL);
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
            return getNum() + getVar().toString();
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
     *                The variables should all be equal.
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
     * while the variable itself does.
     * 
     * <p>
     * This method can also be used for division.
     * 
     * @param varLetter The letter of the variable
     * @param factors   The terms being multiplied together.
     * @return
     */
    public static PolynomialTerm multiply(char varLetter, ArrayList<PolynomialTerm> factors) {
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

            for (PolynomialTerm factor : factors) {
                numPower = variable.getNumeratorPower();
                denomPower = variable.getDenominatorPower();

                int factorNumPower = factor.getVar().getNumeratorPower();
                int factorDenomPower = factor.getVar().getDenominatorPower();

                if (variable.getVar() == factor.getVar().getVar()) {
                    number *= factor.getNum();

                    if (denomPower != factorDenomPower) {
                        // implement later (and check functionality later)
                    } else {
                        variable.setPower(numPower + factorNumPower, denomPower);
                    }
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
        return getNum() * Math.pow(value, (double) variable.getNumeratorPower() / variable.getDenominatorPower());
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
    public PolynomialTerm derive(int degree) {
        double number = getNum();
        Variable variable = getVar();
        int denomPower = variable.getDenominatorPower();

        // Derivative of a constant is 0
        if (variable.getNumeratorPower() == 0) {
            return new PolynomialTerm(0, variable);
        }

        for (int i = 0; i < degree; i++) {
            number *= (double) variable.getNumeratorPower() / denomPower;
            variable.setPower(variable.getNumeratorPower() - denomPower, denomPower);
            System.out.println(variable.getNumeratorPower());
        }

        return new PolynomialTerm(number, getVar());
    }

    // /**
    // * Applies the integration power rule to a mono-variate polynomial term.
    // *
    // * @return
    // */
    // public PolynomialTerm integrate() {

    // }
}
