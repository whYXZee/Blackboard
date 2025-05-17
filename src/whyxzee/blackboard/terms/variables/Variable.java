package whyxzee.blackboard.terms.variables;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.terms.PolynomialTerm;
import whyxzee.blackboard.terms.Term;
import whyxzee.blackboard.utils.UnicodeUtils;

/**
 * The package for a polynomial variable. The variable class is modeled after
 * the variable x^n.
 * 
 * <p>
 * The functionalityof this class was checked on {@code 5/10/2025}, and nothing
 * has changed since. However, the following remain unimplemented:
 * <ul>
 * <li>simplifyPower() unimplemented
 */
public class Variable {
    /* General Use : static */
    public static Variable noVar = new Variable("", 0);

    /* General Use */
    private String var;
    private int numPower; // numerator. if power is negative, this will be negative
    private int denomPower; // denominator
    private String powerUnicode;

    /* Variable Identification */
    private boolean shouldChainRule;

    /**
     * The constructor class for a variable with a integer power.
     * 
     * @param var
     * @param power
     */
    public Variable(String var, int power) {
        this.var = var;
        numPower = power;
        denomPower = 1;
        setUnicode();

        shouldChainRule = false;
    }

    /**
     * The constructor class for a variable with a fractional power.
     * 
     * @param var
     * @param numPower
     * @param denomPower
     */
    public Variable(String var, int numPower, int denomPower) {
        this.var = var;
        this.numPower = numPower;
        this.denomPower = denomPower;
        setUnicode();

        shouldChainRule = false;
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

    /**
     * Simplifies the power to the smallest fraction.
     */
    public void simplifyPower() {
        throw new UnsupportedOperationException("simplifyPower() method is not supported at this moment");
        // TODO: Unimplemented method
    }

    /**
     * 
     * @return the derivative of the variable, if it has an "inner function" or
     *         "inner term"
     */
    public Term derive() {
        if (numPower == 0) {
            // constant
            return PolynomialTerm.ZERO_TERM;
        } else {
            // not a constant
            return new PolynomialTerm((double) numPower / denomPower, this.setPower(numPower - denomPower, denomPower));
        }
    }

    //
    // Get & Set Methods
    //
    public final String getVar() {
        return var;
    }

    public final int getNumeratorPower() {
        return numPower;
    }

    public final int getDenominatorPower() {
        return denomPower;
    }

    public final double getPower() {
        return (double) numPower / denomPower;
    }

    public final void setPower(int power) {
        numPower = power;
        denomPower = 1;
        setUnicode();
    }

    public final Variable setPower(int numPower, int denomPower) {
        this.numPower = numPower;
        this.denomPower = denomPower;
        setUnicode();

        return this;
    }

    public final String getPowerUnicode() {
        return powerUnicode;
    }

    private final void setUnicode() {
        if (denomPower == 1) {
            powerUnicode = UnicodeUtils.intToSuperscript(numPower);
        } else {
            powerUnicode = UnicodeUtils.intToSuperscript(numPower) + Constants.Unicode.SUPERSCRIPT_SLASH
                    + UnicodeUtils.intToSuperscript(denomPower);
        }
    }

    public final void setShouldChainRule(boolean shouldChainRule) {
        this.shouldChainRule = shouldChainRule;
    }

    public final boolean getShouldChainRule() {
        return shouldChainRule;
    }

    //
    // Arithmetic
    //
    /**
     * Applies an exponent to the variable.
     * (x^n)^power
     */
    public final Variable exponentiate(int power) {
        numPower *= power;
        setUnicode();
        return this;
    }

    public final Variable exponentiate(int numPower, int denomPower) {
        this.numPower *= numPower;
        this.denomPower *= denomPower;
        setUnicode();
        return this;
    }

    //
    // Boolean Methods
    //
    public final boolean hasFractionPower() {
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
        } else if (numPower == 1 && denomPower == 1) {
            return var;
        } else {
            return var + powerUnicode;
        }
    }

    @Override
    public Variable clone() {
        return new Variable(getVar(), getNumeratorPower(), getDenominatorPower());
    }

    public String printConsole() {
        if (numPower == 0) {
            return "1";
        } else if (numPower == 1) {
            return var;
        } else if (denomPower == 1) {
            return var + "^(" + numPower + ")";
        } else {
            return var + "^(" + numPower + "/" + denomPower + ")";
        }
    }
}
