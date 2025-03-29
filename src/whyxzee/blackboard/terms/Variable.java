package whyxzee.blackboard.terms;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.UnicodeUtils;

/**
 * The API for a polynomial variable. The variable is essentially an x^n.
 * 
 * <p>
 * The methods in this class have been checked on {@code 2/16/2025}, and nothing
 * has
 * changed since then.
 */
public abstract class Variable {
    //
    // General-Use
    //
    private char var;
    private int numPower; // numerator
    private int denomPower; // denominator
    private String powerUnicode;

    /**
     * The constructor class for a variable with a integer power.
     * 
     * @param var
     * @param power
     */
    public Variable(char var, int power) {
        this.var = var;
        numPower = power;
        denomPower = 1;
        powerUnicode = UnicodeUtils.intToSuperscript(power);
    }

    /**
     * The constructor class for a variable with a fractional power.
     * 
     * @param var
     * @param numPower
     * @param denomPower
     */
    public Variable(char var, int numPower, int denomPower) {
        this.var = var;
        this.numPower = numPower;
        this.denomPower = denomPower;
        powerUnicode = UnicodeUtils.intToSuperscript(numPower) + Constants.Unicode.SUPERSCRIPT_SLASH
                + UnicodeUtils.intToSuperscript(denomPower);
    }

    /**
     * Solves the polynomial.
     * 
     * @param value
     * @return
     */
    public double solve(double value) {
        return Math.pow(value, (double) numPower / denomPower);
    }

    //
    // Get & Set Methods
    //
    public char getVar() {
        return var;
    }

    public int getNumeratorPower() {
        return numPower;
    }

    public int getDenominatorPower() {
        return denomPower;
    }

    public void setPower(int power) {
        numPower = power;
        denomPower = 1;
    }

    public void setPower(int numPower, int denomPower) {
        this.numPower = numPower;
        this.denomPower = denomPower;
    }

    public String getPowerUnicode() {
        return powerUnicode;
    }

    //
    // Boolean Methods
    //
    public boolean hasFractionPower() {
        return denomPower != 1;
    }

    public boolean equals(Variable other) {
        boolean sameVar = var == other.getVar();
        boolean samePower = getPowerUnicode().equals(other.getPowerUnicode()); // blanket conditional, so there doesn't
                                                                               // need to be a conditional for int and
                                                                               // one for fractional
        return sameVar && samePower;
    }

    @Override
    public String toString() {
        if (numPower == 0) {
            return "1";
        } else if (numPower == 1) {
            return "" + var;
        } else {
            return var + powerUnicode;
        }
    }
}
