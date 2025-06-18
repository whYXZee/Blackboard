package whyxzee.blackboard.math.pure.terms;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.pure.numbers.BNumber;
import whyxzee.blackboard.math.pure.terms.variables.Variable;

public class PowerTerm extends Term {
    private static final boolean telemetryOn = Constants.TelemetryConstants.POW_TERM_TELEMETRY;

    /* Variables */
    private BNumber power;

    ///
    /// Constructor Classes
    ///
    // #region Constructors
    /**
     * Constructor class for a real number term.
     * 
     * @param coef
     */
    public PowerTerm(double coef) {
        super(coef, Variable.noVar, TermType.POWER);
        this.power = new BNumber(0, 0);
    }

    /**
     * Constructor class for a number term.
     * 
     * @param coef
     */
    public PowerTerm(BNumber coef) {
        super(coef, Variable.noVar, TermType.POWER);
        this.power = new BNumber(0, 0);
    }

    /**
     * Constructor class for a real number term with a variable to the power of one.
     * 
     * @param coef
     * @param var
     */
    public PowerTerm(double coef, Variable var) {
        super(coef, var, TermType.POWER);
        this.power = new BNumber(1, 0);
    }

    /**
     * Constructor class for a number term with a variable to the power of one.
     * 
     * @param coef
     * @param var
     */
    public PowerTerm(BNumber coef, Variable var) {
        super(coef, var, TermType.POWER);
        this.power = new BNumber(1, 0);
    }

    /**
     * Constructor class for a real number with a variable to the power of a real
     * number.
     * 
     * @param coef
     * @param var
     * @param power
     */
    public PowerTerm(double coef, Variable var, double power) {
        super(coef, var, TermType.POWER);
        this.power = new BNumber(power, 0);
    }

    /**
     * constructor class for a complex number with a variable to the power of a real
     * number.
     * 
     * @param coef  complex number as a <b>BNumber</b>
     * @param var
     * @param power real number as a <b>double</b>.
     */
    public PowerTerm(BNumber coef, Variable var, double power) {
        super(coef, var, TermType.POWER);
        this.power = new BNumber(power, 0);
    }

    public PowerTerm(double coef, Variable var, BNumber power) {
        super(coef, var, TermType.POWER);
        this.power = power;
    }

    public PowerTerm(BNumber coef, Variable var, BNumber power) {
        super(coef, var, TermType.POWER);
        this.power = power;
    }
    // #endregion

    @Override
    public final String toString() {
        String output = "";

        /* Coefficient */
        BNumber coef = getCoef();
        if (coef.isZero()) {
            return "0";
        } else if (coef.isComplex()) {
            output += "(" + coef + ")";
        } else if (!isConstant() && coef.equals(1)) {
            output += "";
        } else if (!isConstant() && coef.equals(-1)) {
            output += "-";
        } else {
            output += coef;
        }

        /* Variable + power */
        if (power.equals(new BNumber(1, 0))) {
            output += getVar();
        } else if (!power.equals(new BNumber(0, 0))) {
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

    @Override
    public final Term clone() {
        return new PowerTerm(getCoef().clone(), getVar(), power.clone());
    }

    ///
    /// Get & Set Methods
    ///
    public final BNumber getPower() {
        return power;
    }

    /**
     * Sets the power to a real number.
     * 
     * @param power
     */
    public final void setPower(double power) {
        this.power = new BNumber(power, 0);
    }

    /**
     * Sets the power to a complex number.
     * 
     * @param power
     */
    public final void setPower(BNumber power) {
        this.power = power;
    }

    ///
    /// Arithmetic Methods
    ///
    @Override
    public final Term negate() {
        return new PowerTerm(getCoef().negate(), getVar(), power.clone());
    }

    @Override
    public BNumber solve(BNumber value) {
        BNumber output = getCoef().clone();
        /* Telemetry */
        if (telemetryOn) {
            System.out.println("----solving " + this + " with value " + value + "----");
            System.out.println("coef: " + output);
        }

        if (getVar().equals(Variable.noVar)) {
            return output;
        }

        BNumber factor = BNumber.pow(getVar().solve(value), power);
        output = BNumber.multiply(output, factor);

        /* Telemetry */
        if (telemetryOn) {
            System.out.println(getCoef() + " * " + factor + " = " + output);
        }
        return output;
    }

    ///
    /// Boolean Methods
    ///
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
}
