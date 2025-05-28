package whyxzee.blackboard.terms;

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
 * The functionality of this class was checked on {@code 5/24/2025} and the
 * following has changed since:
 * <ul>
 * <li>equals()
 */
public class PowerTerm extends Term {
    /* General-use Static */
    public static final PowerTerm ZERO_TERM = new PowerTerm(0);

    /* Variables */
    private int numPower;
    private int denomPower;
    private double power;
    private String powerUnicode;

    /**
     * The constructor class for a polynomial term.
     * 
     * @param coef  the coefficient
     * @param var   the variable
     * @param power the power
     */
    public PowerTerm(double coef, Variable var, int power) {
        /* Term Abstract */
        super(coef, var, TermType.POWER);

        /* Function */
        numPower = power;
        denomPower = 1;
        this.power = (double) numPower / denomPower;
        setUnicode();
    }

    /**
     * The constructor class for a PowerTerm with a power of 1.
     * 
     * @param coefficient
     * @param var
     */
    public PowerTerm(double coefficient, Variable var) {
        /* Term Abstract */
        super(coefficient, var, TermType.POWER);

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
    public PowerTerm(double coefficient, Variable var, int numPower, int denomPower) {
        /* Term Abstract */
        super(coefficient, var, TermType.POWER);

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
    public PowerTerm(double num) {
        /* Term Abstract */
        super(num, Variable.noVar, TermType.POWER);

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
        double number = getCoef();
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
        double number = getCoef();
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

    //
    // Get & Set Methods
    //
    public final int getNumeratorPower() {
        return numPower;
    }

    public final void setNumeratorPower(int numPower) {
        this.numPower = numPower;
        power = (double) numPower / denomPower;
        setUnicode();
    }

    public final int getDenominatorPower() {
        return denomPower;
    }

    public final void setDenominatorPower(int denomPower) {
        this.denomPower = denomPower;
        power = (double) numPower / denomPower;

        setUnicode();
    }

    public final double getPower() {
        return (double) numPower / denomPower;
    }

    public final void setPower(int numPower, int denomPower) {
        this.numPower = numPower;
        this.denomPower = denomPower;
        power = (double) numPower / denomPower;
        setUnicode();
    }

    private final void setUnicode() {
        if (denomPower == 1) {
            // denominator not needed
            powerUnicode = UnicodeUtils.intToSuperscript(numPower);
        } else {
            powerUnicode = UnicodeUtils.intToSuperscript(numPower) + Constants.Unicode.SUPERSCRIPT_SLASH
                    + UnicodeUtils.intToSuperscript(denomPower);
        }
    }

    //
    // Arithmetic Methods
    //

    /**
     * Inputs a value into the polynomial, as if it was a function.
     * 
     * @param value the value of the variable
     * @return
     */
    public final double solve(double value) {
        return getCoef() * Math.pow(getVar().solve(value), power);
    }

    public final Term negate() {
        return new PowerTerm(-1 * getCoef(), getVar(), numPower, denomPower);
    }

    /**
     * Applies the derivative power rule to a mono-variate polynomial term.
     * 
     * @param degree how many times the derivative should be taken.
     * @return
     */
    @Override
    public final Term derive() {
        /* Initializing variables */
        double number = getCoef();
        Variable variable = getVar().clone();

        /* Number */
        if (numPower == 0) {
            // Derivative of a constant is 0
            return PowerTerm.ZERO_TERM;
        }

        /* Function */
        if (variable.isUSub()) {
            // chain rule
            if (numPower == 1 && denomPower == 1) {
                return variable.derive();
            } else {
                /* Derivative */
                EQMultiplication eq = new EQMultiplication(
                        // outer function (x^n)
                        new PowerTerm((double) numPower / denomPower, variable, numPower - denomPower, denomPower),

                        // inner function (x)
                        variable.derive());
                return new PowerTerm(number, new USub(eq), 1);
            }

        } else {
            // no chain rule
            number *= (double) numPower / denomPower;
            return new PowerTerm(number, variable, numPower - denomPower, denomPower);
        }
    }

    @Override
    public final double limInfSolve() {
        /* Without respect to the variable */

        /* Number */
        double number = getCoef();
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
    public final double limNegInfSolve() {
        /* Without respect to the variable */

        /* Number */
        double number = getCoef();
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

    //
    // Boolean Methods
    //
    @Override
    public final boolean similarTo(Term term) {
        switch (term.getTermType()) {
            case POWER:
                PowerTerm polyTerm = (PowerTerm) term;
                return (polyTerm.getDenominatorPower() == this.denomPower)
                        && (polyTerm.getNumeratorPower() == this.numPower)
                        && (polyTerm.getVar().equals(this.getVar()));
            default:
                return false;
        }
    }

    @Override
    public final boolean equals(Term other) {
        if ((other.getTermType() == TermType.POWER) && (getCoef() == other.getCoef())) {
            PowerTerm polyTerm = (PowerTerm) other;
            return (power == polyTerm.getPower()) && (getVar().equals(polyTerm.getVar()));
        }
        return false;
    }
}
