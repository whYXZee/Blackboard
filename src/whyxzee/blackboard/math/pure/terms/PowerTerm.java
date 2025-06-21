package whyxzee.blackboard.math.pure.terms;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.pure.equations.MathEQ;
import whyxzee.blackboard.math.pure.numbers.ComplexNum;
import whyxzee.blackboard.math.pure.terms.variables.Variable;
import whyxzee.blackboard.utils.Loggy;

/**
 * A package for PowerTerms. This package is constructed as an {@code a*x^n}
 * term. The following have been implemented:
 * <ul>
 * <li>Complex powers and coefficients
 * <li>uncountable power and coefficient
 * <li>DNE power and coefficient
 * </ul>
 * 
 * <p>
 * The functionality of this class has not been checked.
 */
public class PowerTerm extends Term {
    /* Variables */
    private static final Loggy loggy = new Loggy(
            Constants.Loggy.POW_TERM_LOGGY || Constants.Loggy.TERM_LOGGY);
    private ComplexNum power;

    // #region Constructors
    /**
     * Constructor class for a real number term.
     * 
     * @param coef
     */
    public PowerTerm(double coef) {
        super(coef, Variable.noVar, TermType.POWER);
        this.power = new ComplexNum(0, 0);
    }

    /**
     * Constructor class for a number term.
     * 
     * @param coef
     */
    public PowerTerm(ComplexNum coef) {
        super(coef, Variable.noVar, TermType.POWER);
        this.power = new ComplexNum(0, 0);
    }

    /**
     * Constructor class for a real number term with a variable to the power of one.
     * 
     * @param coef
     * @param var
     */
    public PowerTerm(double coef, Variable<?> var) {
        super(coef, var, TermType.POWER);
        this.power = new ComplexNum(1, 0);
    }

    /**
     * Constructor class for a number term with a variable to the power of one.
     * 
     * @param coef
     * @param var
     */
    public PowerTerm(ComplexNum coef, Variable<?> var) {
        super(coef, var, TermType.POWER);
        this.power = new ComplexNum(1, 0);
    }

    /**
     * Constructor class for a real number with a variable to the power of a real
     * number.
     * 
     * @param coef
     * @param var
     * @param power
     */
    public PowerTerm(double coef, Variable<?> var, double power) {
        super(coef, var, TermType.POWER);
        this.power = new ComplexNum(power, 0);
    }

    /**
     * constructor class for a complex number with a variable to the power of a real
     * number.
     * 
     * @param coef  complex number as a <b>BNumber</b>
     * @param var
     * @param power real number as a <b>double</b>.
     */
    public PowerTerm(ComplexNum coef, Variable var, double power) {
        super(coef, var, TermType.POWER);
        this.power = new ComplexNum(power, 0);
    }

    public PowerTerm(double coef, Variable var, ComplexNum power) {
        super(coef, var, TermType.POWER);
        this.power = power;
    }

    public PowerTerm(ComplexNum coef, Variable var, ComplexNum power) {
        super(coef, var, TermType.POWER);
        this.power = power;
    }
    // #endregion

    // #region String / Display
    @Override
    public final String toString() {
        String output = "";

        /* Coefficient */
        if (getCoef().isZero()) {
            return "0";
        } else if (getCoef().isDNE() || getPower().isDNE()) {
            return "DNE";
        }
        output += coefString();

        /* Variable + power */
        if (power.equals(1)) {
            output += getVar();
        } else if (!power.isZero()) {
            // power not one nor zero

            if (getVar().isUSub()) {
                output += "(" + getVar() + ")";
            } else {
                output += getVar();
            }
            output += "^(" + power + ")";
        }

        return output;
    }
    // #endregion

    // #region Copying / Cloning
    @Override
    public final Term clone() {
        return new PowerTerm(getCoef().clone(), getVar(), power.clone());
    }
    // #endregion

    // #region PowTerm Get/Set
    public final ComplexNum getPower() {
        return power;
    }

    /**
     * Sets the power to a real number.
     * 
     * @param power
     */
    public final void setPower(double power) {
        this.power = new ComplexNum(power, 0);
    }

    /**
     * Sets the power to a complex number.
     * 
     * @param power
     */
    public final void setPower(ComplexNum power) {
        this.power = power;
    }
    // #endregion

    // #region Arithmetic w/ Coef
    @Override
    public final Term negate() {
        return new PowerTerm(getCoef().negate(), getVar(), power.clone());
    }
    // #endregion

    @Override
    public ComplexNum solve(ComplexNum value) {
        ComplexNum output = getCoef().clone();

        loggy.logHeader("Solving " + this + " with value " + value);
        loggy.logVal("coef", output);

        if (getVar().equals(Variable.noVar)) {
            return output;
        }

        ComplexNum factor = ComplexNum.pow(getVar().solve(value), power);
        output = ComplexNum.multiply(output, factor);

        /* Telemetry */
        loggy.log(getCoef() + " * " + factor + " = " + output);

        return output;
    }

    // #region Term Bools
    /**
     * A PowerTerm is constant if the power is zero.
     * 
     * @return {@code power == 0}
     */
    public final boolean isConstant() {
        return power.equals(0);
    }

    @Override
    public boolean similarTo(Term term) {
        if (!term.isTermType(TermType.POWER)) {
            return false;
        }

        PowerTerm powTerm = (PowerTerm) term;
        return getVar().equals(powTerm.getVar()) &&
                power.equals(powTerm.getPower());
    }

    public final boolean contains(Object var1) {
        if (var1 == null) {
            return false;
        }

        if (var1 instanceof ComplexNum) {
            ComplexNum num = (ComplexNum) var1;
            return num.equals(getCoef()) || num.equals(getPower());

        } else if (var1 instanceof Variable) {
            Variable var = (Variable) var1;
            return false;

        } else if (var1 instanceof Term) {
            if (equals(var1)) {
                return true;
            }
            return getVar().contains(var1);

        } else if (var1 instanceof MathEQ) {
            return getVar().contains(var1);
        }

        return false;
    }
    // #endregion
}
