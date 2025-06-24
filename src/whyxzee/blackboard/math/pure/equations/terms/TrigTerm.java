package whyxzee.blackboard.math.pure.equations.terms;

import whyxzee.blackboard.math.pure.equations.MathEQ;
import whyxzee.blackboard.math.pure.equations.variables.Variable;
import whyxzee.blackboard.math.pure.numbers.Complex;
import whyxzee.blackboard.math.utils.pure.NumberUtils;
import whyxzee.blackboard.math.utils.pure.TrigUtils;
import whyxzee.blackboard.math.utils.pure.TrigUtils.TrigType;

/**
 * A package for trigonometric terms. This package is contructed as an
 * {@code a*(trig(x))^n}.
 */
public class TrigTerm extends PowerTerm {
    /* Variables */
    private TrigType trigType;

    /**
     * {@code a*trig(var)}
     * 
     * @param coef
     * @param var
     * @param type
     */
    public TrigTerm(Object coef, Variable<?> var, TrigType type) {
        super(coef, var);
        this.trigType = type;
    }

    /**
     * {@code a*(trig(var))^n}
     * 
     * @param coef
     * @param var
     * @param power
     * @param type
     */
    public TrigTerm(Object coef, Variable<?> var, Object power, TrigType type) {
        super(coef, var, power);
        this.trigType = type;
    }

    // #region String/Display
    @Override
    public final String termString() {
        return trigType.getTrigString() + "(" + getVar() + ")";
    }
    // #endregion

    // #region Copying/Cloning
    @Override
    public final TrigTerm clone() {
        return new TrigTerm(getCoef().clone(), getVar().clone(), getPower().clone(), trigType);
    }
    // #endregion

    // #region Trig Get/Set
    public final TrigType getTrigType() {
        return trigType;
    }
    // #endregion

    // #region Coefficient
    @Override
    public final TrigTerm negate() {
        return new TrigTerm(getCoef().negate(), getVar().clone(), getPower().clone(), trigType);
    }

    // #endregion

    // #region Solve
    @Override
    public final PowerTerm solve(String variable, PowerTerm value) {
        if (getVar().equals(Variable.noVar)) {
            return new PowerTerm(getCoef());
        } else {
            PowerTerm solved = getVar().solve(variable, value);
            if (solved.isConstant()) {
                Complex inVal = solved.getCoef();
                inVal = trigType.performOp(inVal);
                Complex val = NumberUtils.multiply(getCoef(), NumberUtils.pow(inVal, getPower()));
                return new PowerTerm(val);
            } else {
                return new TrigTerm(getCoef(), new Variable<PowerTerm>(solved), trigType);
            }
        }
    }

    // #endregion

    // #region Addition
    @Override
    public final boolean isAddend(PowerTerm addend) {
        if (!(addend instanceof TrigTerm)) {
            return false;
        }

        /* Similar var + power */
        if (!similarTo(addend)) {
            return false;
        }

        /* Similar Trig Type */
        return trigType == ((TrigTerm) addend).getTrigType();
    }
    // #endregion

    // #region Multiplication
    @Override
    public final boolean isFactor(PowerTerm factor) {
        if (!(factor instanceof TrigTerm)) {
            return false;
        }

        /* Similar var + power */
        if (!getVar().equals(factor.getVar())) {
            return false;
        }

        /* Similar Trig Type */
        return trigType == ((TrigTerm) factor).getTrigType();
    }
    // #endregion

    // #region Inverse
    @Override
    public final TrigTerm applyInverseTo(Object arg) {
        if (arg instanceof PowerTerm) {
            PowerTerm powTerm = (PowerTerm) arg;
            if (!getPower().equals(1)) {
                powTerm = applyInversePowTo(arg);
                setPower(1);
            }

            return new TrigTerm(1, new Variable<PowerTerm>(powTerm), TrigUtils.inverseOf(trigType));

        } else if (arg instanceof MathEQ) {
            PowerTerm powTerm = ((MathEQ) arg).toTerm();
            if (!getPower().equals(1)) {
                powTerm = applyInversePowTo(arg);
                setPower(1);
            }

            return new TrigTerm(1, new Variable<PowerTerm>(powTerm), TrigUtils.inverseOf(trigType));
        }
        return null;
    }
    // #endregion

    // #region Comparison Bools
    @Override
    public final boolean equalsCriteria(PowerTerm arg) {
        TrigTerm other = (TrigTerm) arg;
        return trigType == other.getTrigType();
    }
    // #endregion
}
