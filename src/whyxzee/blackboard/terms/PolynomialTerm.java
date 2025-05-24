package whyxzee.blackboard.terms;

import java.util.ArrayList;
import java.util.HashMap;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.equations.EQMultiplication;
import whyxzee.blackboard.terms.variables.USub;
import whyxzee.blackboard.terms.variables.Variable;
import whyxzee.blackboard.utils.ArithmeticUtils;
import whyxzee.blackboard.utils.UnicodeUtils;

/**
 * The package for a polynomial term. The term is a*x^n.
 * 
 * <p>
 * The package is contructed as an y=x^n equation.
 * 
 * <p>
 * The functionality of this class was checked on {@code 5/20/2025} and nothing
 * has changed since then.
 */
public class PolynomialTerm extends Term {
    //
    // General use: static
    //
    public static final PolynomialTerm ZERO_TERM = new PolynomialTerm(0);

    //
    // Variables
    //
    private int numPower;
    private int denomPower;
    private double power;
    private String powerUnicode;

    //
    // Object-related Methods
    //

    /**
     * The constructor class for a polynomial term
     * 
     * @param coefficient the coefficient
     * @param var         the variable
     */
    public PolynomialTerm(double coefficient, Variable var, int power) {
        /* Term Abstract */
        super(coefficient, var, TermType.POLYNOMIAL);

        /* Function */
        numPower = power;
        denomPower = 1;
        this.power = (double) numPower / denomPower;
        setUnicode();
    }

    public PolynomialTerm(double coefficient, Variable var) {
        /* Term Abstract */
        super(coefficient, var, TermType.POLYNOMIAL);

        /* Function */
        numPower = 1;
        denomPower = 1;
        this.power = (double) numPower / denomPower;
        setUnicode();
    }

    /**
     * The constructor class for a polynomial term
     * 
     * @param coefficient the coefficient
     * @param var         the variable
     */
    public PolynomialTerm(double coefficient, Variable var, int numPower, int denomPower) {
        /* Term Abstract */
        super(coefficient, var, TermType.POLYNOMIAL);

        /* Function */
        this.numPower = numPower;
        this.denomPower = denomPower;
        this.power = (double) numPower / denomPower;
        setUnicode();
    }

    /**
     * The constructor class for a constant.
     * 
     * @param num the constant
     */
    public PolynomialTerm(double num) {
        /* Term Abstract */
        super(num, Variable.noVar, TermType.POLYNOMIAL);

        /* Function */
        numPower = 0;
        denomPower = 1;
        this.power = (double) numPower / denomPower;
        setUnicode();
    }

    /**
     * Prints the polynomial term. If the number is 0, then nothing will be printed.
     */
    @Override
    public String toString() {
        /* Initializing variables */
        Variable variable = getVar();

        /* Number */
        double number = getCoefficient();
        if (number == 0) {
            return "0";
        }

        /* Power */
        boolean isPowerOne = power == 1;
        if (numPower == 0) {
            return Double.toString(number);
        } else {
            if (number == 1) {
                // coefficient of 1
                if (variable.isUSub()) {
                    // USub variable
                    return isPowerOne ? variable.toString() : "(" + variable.toString() + ")" + powerUnicode;
                } else {
                    // non-USub variable
                    return variable.toString() + (isPowerOne ? "" : powerUnicode);
                }
            } else {
                // coefficient that is not 1 nor 0
                if (variable.isUSub()) {
                    // USub variable
                    return number + "(" + variable.toString() + ")" + (isPowerOne ? "" : powerUnicode);
                } else {
                    // non-USub variable
                    return number + variable.toString() + (isPowerOne ? "" : powerUnicode);
                }
            }
        }
    }

    @Override
    public String printConsole() {
        /* Initializing variables */
        Variable variable = getVar();

        /* Number */
        double number = getCoefficient();
        if (number == 0) {
            return "0";
        }

        /* Power */
        boolean isPowerOne = power == 1;
        if (numPower == 0) {
            return Double.toString(number);
        } else {
            if (number == 1) {
                // coefficient of 1
                if (variable.isUSub()) {
                    // USub variable
                    return isPowerOne ? variable.printConsole()
                            : "(" + variable.toString() + ")^(" + numPower + "/" + denomPower + ")";
                } else {
                    // non-USub variable
                    return variable.printConsole() + (isPowerOne ? "" : "^(" + numPower + "/" + denomPower + ")");
                }
            } else {
                // coefficient that is not 1 nor 0
                if (variable.isUSub()) {
                    // USub variable
                    return number + "(" + variable.toString() + ")"
                            + (isPowerOne ? "" : "^(" + numPower + "/" + denomPower + ")");
                } else {
                    // non-USub variable
                    return number + variable.toString() + (isPowerOne ? "" : "^(" + numPower + "/" + denomPower + ")");
                }
            }
        }
    }

    public static Term simplifyEQMulti(ArrayList<Term> terms) {
        /* Initializing terms */
        HashMap<Variable, PolynomialTerm> polyTerms = new HashMap<Variable, PolynomialTerm>();

        /* Simplifying Algorithm */
        for (int i = 0; i < terms.size(); i++) {
            /* Initialing variables */
            PolynomialTerm term = (PolynomialTerm) terms.get(i);
            double coef = term.getCoefficient();
            Variable var = term.getVar();
            int numPower = term.getNumeratorPower();
            int denomPower = term.getDenominatorPower();

            if (ArithmeticUtils.inHashMap(polyTerms, var)) {
                // var in variables
                PolynomialTerm poly2 = polyTerms.get(var);

                polyTerms.replace(var, new PolynomialTerm(
                        poly2.getCoefficient() * coef,
                        var,
                        (numPower * poly2.getDenominatorPower()) + (denomPower * poly2.getNumeratorPower()),
                        denomPower * poly2.getDenominatorPower()));

            } else {
                // var not in variables
                polyTerms.put(var, new PolynomialTerm(coef, var, numPower, denomPower));
            }

        }

        System.out.println("size: " + polyTerms.size());
        if (polyTerms.size() > 1) {
            EQMultiplication eq = new EQMultiplication(new ArrayList<Term>(polyTerms.values()));
            return new PolynomialTerm(1, new USub(eq), 1);

        } else if (polyTerms.size() == 1) {
            return polyTerms.get(polyTerms.keySet().toArray()[0]);
        } else {
            return null;
        }
    }

    //
    // Get & Set Methods
    //
    public final int getNumeratorPower() {
        return numPower;
    }

    public final int getDenominatorPower() {
        return denomPower;
    }

    private void setUnicode() {
        if (denomPower == 1) {
            // denominator not needed
            powerUnicode = UnicodeUtils.intToSuperscript(numPower);
        } else {
            powerUnicode = UnicodeUtils.intToSuperscript(numPower) + Constants.Unicode.SUPERSCRIPT_SLASH
                    + UnicodeUtils.intToSuperscript(denomPower);
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
        return getCoefficient() * Math.pow(getVar().solve(value), power);
    }

    public Term negate() {
        return new PolynomialTerm(-1 * getCoefficient(), getVar(), numPower, denomPower);
    }

    /**
     * Applies the derivative power rule to a mono-variate polynomial term.
     * 
     * @param degree how many times the derivative should be taken.
     * @return
     */
    @Override
    public Term derive() {
        /* Initializing variables */
        double number = getCoefficient();
        Variable variable = getVar().clone();

        /* Number */
        if (numPower == 0) {
            // Derivative of a constant is 0
            return PolynomialTerm.ZERO_TERM;
        }

        /* Function */
        if (variable.isUSub()) {
            // chain rule
            if (numPower == 1 && denomPower == 1) {
                System.out.println("deriving with power of 1");
                return variable.derive();
            } else {
                /* Derivative */
                EQMultiplication eq = new EQMultiplication(
                        // outer function (x^n)
                        new PolynomialTerm((double) numPower / denomPower, variable, numPower - denomPower, denomPower),

                        // inner function (x)
                        variable.derive());
                return new PolynomialTerm(number, new USub(eq), 1);
            }

        } else {
            // no chain rule
            number *= (double) numPower / denomPower;
            return new PolynomialTerm(number, variable, numPower - denomPower, denomPower);
        }
    }

    @Override
    public double limInfSolve() {
        /* Without respect to the variable */

        /* Number */
        double number = getCoefficient();
        if (number == 0) {
            return 0;
        }
        boolean isNumberNegative = number < 0;

        /* Function */
        double power = (double) numPower / denomPower;
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
        double number = getCoefficient();
        if (number == 0) {
            return 0;
        }
        boolean isNumberNegative = number < 0;

        /* Function */
        double power = (double) numPower / denomPower;
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
