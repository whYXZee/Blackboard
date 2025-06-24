package whyxzee.blackboard.math.pure.equations.terms;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.pure.equations.MathEQ;
import whyxzee.blackboard.math.pure.equations.TermArray;
import whyxzee.blackboard.math.pure.equations.variables.Variable;
import whyxzee.blackboard.math.pure.numbers.Complex;
import whyxzee.blackboard.math.utils.pure.NumberUtils;
import whyxzee.blackboard.utils.Loggy;
import whyxzee.blackboard.utils.ObjectUtils;

/**
 * A package for power terms. This package is constructed as an {@code a*(x)^n}
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
public class PowerTerm {
    /* Variables */
    private static final Loggy loggy = new Loggy(Constants.Loggy.POW_TERM_LOGGY);
    private Complex coef;
    private Variable<?> var;
    private Complex power;

    // #region Constructors
    /**
     * Constructor class for a real number term.
     * 
     * @param coef
     */
    public PowerTerm(Object coef) {
        this.coef = ObjectUtils.objToComplex(coef);
        this.var = Variable.noVar;
        this.power = new Complex(0, 0);
        simplify();
    }

    /**
     * Constructor class for a complex number term with a variable to the power of
     * one.
     * 
     * @param coef a double, int, Value, or ComplexNum
     * @param var
     */
    public PowerTerm(Object coef, Variable<?> var) {
        this.coef = ObjectUtils.objToComplex(coef);
        this.var = var;
        this.power = new Complex(1, 0);
        simplify();
    }

    /**
     * Constructor class for a complex number with a variable to the power of
     * complex number.
     * 
     * @param coef  a double, int, Value, or ComplexNum
     * @param var
     * @param power a double, int Value, or ComplexNum
     */
    public PowerTerm(Object coef, Variable<?> var, Object power) {
        this.coef = ObjectUtils.objToComplex(coef);
        this.var = var;
        this.power = ObjectUtils.objToComplex(power);
        simplify();
    }
    // #endregion

    // #region String / Display
    /**
     * Outputs a String value dependent on the coef and var.
     * 
     * @return
     */
    public final String coefString() {
        boolean hasVar = !var.equals(Variable.noVar);

        if (coef.isComplex()) {
            return "(" + coef + ")";
        } else if (hasVar && coef.equals(1)) {
            return "";
        } else if (hasVar && coef.equals(-1)) {
            return "-";
        } else if (hasVar && coef.equals(new Complex(0, -1))) {
            return "-i";
        } else {
            return coef.toString();
        }
    }

    @Override
    public String toString() {
        String output = "";

        /* Coefficient */
        if (coef.isZero()) {
            return "0";
        } else if (coef.isDNE() || power.isDNE()) {
            return "DNE";
        }
        output += coefString();

        /* Variable + power */
        if (power.equals(1)) {
            output += termString();
        } else if (!power.isZero()) {
            // power not one nor zero

            if (var.isUSub() || this.getClass() != PowerTerm.class) {
                output += "(" + termString() + ")";
            } else {
                output += termString();
            }
            output += "^(" + power + ")";
        }

        return output;
    }

    /**
     * Checks if the negative version of the Term String is needed.
     * 
     * @return
     */
    public final boolean needsNegString() {
        return coef.isANegative() && coef.isBZero() // real negative
                || coef.isBNegative() && coef.isAZero(); // imaginary negative

    }

    /**
     * Outputs a String value dependent on the coef and var. However, the terms are
     * negated for negativeString().
     * 
     * @return
     */
    public final String negCoefString() {
        boolean hasVar = !var.equals(Variable.noVar);

        if (coef.isComplex()) {
            return "(" + coef + ")";
        } else if (hasVar && coef.equals(1) || coef.equals(-1)) {
            return "";
        } else if (hasVar && coef.equals(new Complex(0, -1))) {
            return "i";
        } else {
            return coef.negate().toString();
        }
    }

    /**
     * Returns a String that is used for MathEQ.
     * 
     * @return
     */
    public String negativeString() {
        String output = "";

        /* Coefficient */
        if (coef.isZero()) {
            return "0";
        } else if (coef.isDNE() || power.isDNE()) {
            return "DNE";
        }
        output += negCoefString();

        /* Variable + power */
        if (power.equals(1)) {
            output += termString();
        } else if (!power.isZero()) {
            // power not one nor zero

            if (var.isUSub() || this.getClass() != PowerTerm.class) {
                output += "(" + termString() + ")";
            } else {
                output += termString();
            }
            output += "^(" + power + ")";
        }

        return output;
    }

    /**
     * The String for term-specific content.
     * 
     * @return
     */
    public String termString() {
        return var.toString();
    }
    // #endregion

    // #region Copying / Cloning
    public PowerTerm clone() {
        return new PowerTerm(coef.clone(), var.clone(), power.clone());
    }
    // #endregion

    // #region Conversion Methods
    public final TermArray toTermArray() {
        return new TermArray(this);
    }

    // #endregion

    // #region Variable
    public final Variable<?> getVar() {
        return var;
    }

    public final void setVar(Variable<?> var) {
        this.var = var;
    }

    public final boolean containsVar(String var) {
        return this.var.containsVar(var);
    }
    // #endregion

    // #region Coefficient
    /**
     * Distributes the variable amongst the real and imaginary components of the
     * coefficient.
     * 
     * <p>
     * <em>Performs a deep copy of the term</em>.
     * 
     * @return
     */
    public final TermArray distributeTerm() {
        if (!coef.isComplex()) {
            return clone().toTermArray();
        }

        PowerTerm real = clone();
        real.setCoef(new Complex(coef.getA(), 0));
        PowerTerm imaginary = clone();
        imaginary.setCoef(new Complex(0, coef.getB()));
        return new TermArray(real, imaginary);
    }

    public final Complex getCoef() {
        return coef;
    }

    public void setCoef(Object coef) {
        this.coef = ObjectUtils.objToComplex(coef);
    }

    public final void addToCoef(Complex addend) {
        coef = NumberUtils.add(coef, addend);
    }

    public final void multiplyCoefBy(Object factor) {
        coef = NumberUtils.multiply(coef, ObjectUtils.objToComplex(factor));
    }

    public final void divideCoefBy(Object dividend) {
        coef = NumberUtils.divide(coef, ObjectUtils.objToComplex(dividend));
    }

    public final void coefToPow(Object power) {
        coef = NumberUtils.pow(coef, ObjectUtils.objToComplex(power));
    }

    /**
     * Performs a deep copy of the PowerTerm with the coefficient * -1.
     */
    public PowerTerm negate() {
        return new PowerTerm(coef.negate(), var.clone(), power.clone());
    }
    // #endregion

    // #region Power
    public final Complex getPower() {
        return power;
    }

    /**
     * Sets the power to a number.
     * 
     * @param power a double, int, Value, or ComplexNum
     */
    public void setPower(Object power) {
        this.power = ObjectUtils.objToComplex(power);
    }

    public final void addToPower(Object addend) {
        power = NumberUtils.add(power, ObjectUtils.objToComplex(addend));
    }

    /**
     * Sets <b>this</b> to the power of <b>arg</b>. Normally, this will just
     * multiply the power of a PowerTerm by the <b>arg</b> and set the coefficient
     * to {@code coef^(arg)}. However, this method can be overriden to produce new
     * effects.
     * 
     * @param arg a double, int, Value, or ComplexNum
     */
    public void toPower(Object arg) {
        Complex power = ObjectUtils.objToComplex(arg);

        /* Power */
        this.power = NumberUtils.multiply(this.power, power);

        /* Coefficient */
        coef = NumberUtils.pow(getCoef(), power);
    }

    /**
     * Applies the inverse power of <b>this</b> onto <b>arg</b>.
     * 
     * <p>
     * <em>No deep copies are performed, so arg most likely changes after
     * this method.</b>
     * 
     * @param arg
     * @return
     */
    public final PowerTerm applyInversePowTo(Object arg) {
        loggy.logHeader("Applying inverse power of " + power + " onto " + arg);
        Complex inversePow = NumberUtils.divide(new Complex(1, 0), power);
        loggy.logVal("inverse power", inversePow);

        if (arg instanceof Variable) {
            PowerTerm powTerm = new PowerTerm(1, (Variable<?>) arg, inversePow);
            if (power.mod(2).equals(0)) {
                return new PlusMinusTerm(1, new Variable<PowerTerm>(powTerm));
            } else {
                return powTerm;
            }

        } else if (arg instanceof PowerTerm) {
            PowerTerm other = (PowerTerm) arg;
            other.toPower(inversePow);

            if (power.mod(2).equals(0)) {
                Complex coef = other.getCoef().clone();
                other.setCoef(1);
                return new PlusMinusTerm(coef, new Variable<PowerTerm>(other));
            } else {
                return other;
            }

        } else if (arg instanceof MathEQ) {
            PowerTerm powTerm = ((MathEQ) arg).toTerm();
            powTerm.toPower(inversePow);
            if (power.mod(2).equals(0)) {
                return new PlusMinusTerm(1, new Variable<PowerTerm>(powTerm));
            } else {
                return powTerm;
            }
        }
        return null;
    }

    /**
     * A PowerTerm is constant if the power is zero.
     * 
     * @return {@code power == 0}
     */
    public final boolean isConstant() {
        return power.equals(0);
    }
    // #endregion

    // #region Solve
    public PowerTerm solve(String variable, PowerTerm value) {
        if (var.equals(Variable.noVar)) {
            return this;
        } else {
            PowerTerm solved = var.solve(variable, value);
            if (solved.isConstant()) {
                Complex val = NumberUtils.multiply(coef, NumberUtils.pow(solved.getCoef(), power));
                return new PowerTerm(val);
            } else {
                solved.toPower(power);
                solved.multiplyCoefBy(coef);
                return solved;
            }
        }
    }
    // #endregion

    // #region Addition
    /**
     * Checks if the <b>addend</b> is able to be added to <b>this</b>.
     * 
     * @param addend
     * @return
     */
    public boolean isAddend(PowerTerm addend) {
        if (addend.getClass() != PowerTerm.class) {
            return false;
        }

        if (!var.equals(addend.getVar()) || !power.equals(addend.getPower())) {
            return false;
        }

        return true;
    }

    /**
     * Adds the <b>addend</b> into <b>this</b>.
     * 
     * @param addend
     */
    public void add(PowerTerm addend) {
        addToCoef(addend.getCoef());
    }
    // #endregion

    // #region Multiplication
    /**
     * Checks if the <b>factor</b> is able to be multiplied into <b>this</b>.
     * 
     * @param factor
     * @return
     */
    public boolean isFactor(PowerTerm factor) {
        if (factor.getClass() != PowerTerm.class) {
            return false;
        }

        return var.equals(factor.getVar());
    }

    /**
     * Multiplies the <b>factor</b> into <b>this</b>.
     * 
     * @param factor
     */
    public void multiply(PowerTerm factor) {
        multiplyCoefBy(factor.getCoef());
        addToPower(factor.getPower());
    }
    // #endregion

    // #region Inverse
    /**
     * Applies the inverse of <b>this</b> onto <b>this</b> and <b>arg</b>.
     * 
     * @param arg
     * @return
     */
    public PowerTerm applyInverseTo(Object arg) {
        /* Applying inverse to arg */
        PowerTerm output = applyInversePowTo(arg);

        /* Applying inverse to this */
        setPower(1);

        return output;
    }
    // #endregion

    // #region Simplify
    /**
     * Simplifies the PowerTerm to remove redundant U-Sub.
     */
    public void simplify() {
        if (var.getInnerClass() != PowerTerm.class) {
            return;
        }

        PowerTerm innerTerm = (PowerTerm) var.getInner();
        Complex newPow = NumberUtils.multiply(this.power, innerTerm.getPower());
        Complex newCoef = NumberUtils.multiply(this.coef, NumberUtils.pow(innerTerm.getCoef(), this.power));

        /* Setting new values */
        setCoef(newCoef);
        setPower(newPow);

        /* Variable Simplification */
        // TODO: clean up variables in case its MathEQ with one term. is this needed?
        setVar(innerTerm.getVar()); // infers the generic from var.getInner()
    }
    // #endregion

    // #region Comparison Bools
    public final boolean similarTo(PowerTerm other) {
        if (!var.equals(other.getVar()) || !power.equals(other.getPower())) {
            return false;
        }
        return true;
    }

    /**
     * Override equals method for various methods that utilize the equals(Object)
     * method.
     * 
     * @param arg any type of object
     * @return
     */
    @Override
    public final boolean equals(Object arg) {
        if (arg instanceof PowerTerm) {
            PowerTerm other = (PowerTerm) arg;
            if (getClass() != other.getClass()) {
                return false;
            }

            return var.equals(other.getVar())
                    && power.equals(other.getPower())
                    && coef.equals(other.getCoef())
                    && equalsCriteria(other);
        }
        return false;
    }

    /**
     * Add criteria for the
     * {@link whyxzee.blackboard.math.pure.equations.terms.PowerTerm#equals(Object)}
     * for
     * term-specific content.
     * 
     * @param arg
     * @return
     */
    public boolean equalsCriteria(PowerTerm arg) {
        return true;
    }
    // #endregion
}
