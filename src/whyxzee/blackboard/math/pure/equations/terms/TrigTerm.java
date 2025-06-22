package whyxzee.blackboard.math.pure.equations.terms;

import whyxzee.blackboard.math.pure.equations.MathEQ;
import whyxzee.blackboard.math.pure.equations.variables.Variable;
import whyxzee.blackboard.math.pure.numbers.ComplexNum;

/**
 * A package for trigonometric terms. This package is contructed as an
 * {@code a*(trig(x))^n}.
 */
public class TrigTerm extends PowerTerm {
    /* Variables */
    private TrigType type;
    private String trigString;

    public enum TrigType {
        SINE,
        COSINE,
        TANGENT,
        COSECANT,
        SECANT,
        COTANGENT,

        ARC_SINE,
        ARC_COSINE,
        ARC_TANGENT,
        ARC_COSECANT,
        ARC_SECANT,
        ARC_COTANGENT,
    }

    /**
     * {@code a*trig(var)}
     * 
     * @param coef
     * @param var
     * @param type
     */
    public TrigTerm(Object coef, Variable<?> var, TrigType type) {
        super(coef, var);
        this.type = type;
        this.trigString = getTrigString(type);
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
        this.type = type;
        this.trigString = getTrigString(type);
    }

    // #region String/Display
    private static final String getTrigString(TrigType type) {
        switch (type) {
            case SINE:
                return "sin";
            case COSINE:
                return "cos";
            case TANGENT:
                return "tan";
            case COSECANT:
                return "csc";
            case SECANT:
                return "sec";
            case COTANGENT:
                return "cot";

            case ARC_SINE:
                return "arcsin";
            case ARC_COSINE:
                return "arccos";
            case ARC_TANGENT:
                return "arctan";
            case ARC_COSECANT:
                return "arccsc";
            case ARC_SECANT:
                return "arcsec";
            case ARC_COTANGENT:
                return "arccot";
            default:
                return "what";
        }
    }

    @Override
    public final String termString() {
        return trigString + "(" + getVar() + ")";
    }
    // #endregion

    // #region Copying/Cloning
    @Override
    public final TrigTerm clone() {
        return new TrigTerm(getCoef().clone(), getVar().clone(), getPower().clone(), type);
    }
    // #endregion

    // #region Trig Get/Set
    public final TrigType getTrigType() {
        return type;
    }
    // #endregion

    // #region Coefficient
    @Override
    public final TrigTerm negate() {
        return new TrigTerm(getCoef().negate(), getVar().clone(), getPower().clone(), type);
    }

    // #endregion

    // #region Solve
    @Override
    public final PowerTerm solve(String variable, ComplexNum value) {
        if (getVar().equals(Variable.noVar)) {
            return new PowerTerm(getCoef());
        } else {
            PowerTerm solved = getVar().solve(variable, value);
            if (solved.isConstant()) {
                ComplexNum inVal = solved.getCoef();
                switch (type) {
                    case SINE:

                    case COSINE:
                    case TANGENT:
                    case COSECANT:
                    case SECANT:
                    case COTANGENT:

                    case ARC_SINE:
                    case ARC_COSINE:
                    case ARC_TANGENT:
                    case ARC_COSECANT:
                    case ARC_SECANT:
                    case ARC_COTANGENT:
                    default:
                }
                ComplexNum val = ComplexNum.multiply(getCoef(), ComplexNum.pow(inVal, getPower()));
                return new PowerTerm(val);
            }
        }

        throw new UnsupportedOperationException("lolz");
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
        return type == ((TrigTerm) addend).getTrigType();
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
        return type == ((TrigTerm) factor).getTrigType();
    }
    // #endregion

    // #region Inverse
    private TrigType inverseType() {
        switch (type) {
            case SINE:
                return TrigType.ARC_SINE;
            case COSINE:
                return TrigType.ARC_COSINE;
            case TANGENT:
                return TrigType.ARC_TANGENT;
            case COSECANT:
                return TrigType.ARC_COSECANT;
            case SECANT:
                return TrigType.ARC_SECANT;
            case COTANGENT:
                return TrigType.ARC_COTANGENT;

            case ARC_SINE:
                return TrigType.SINE;
            case ARC_COSINE:
                return TrigType.COSINE;
            case ARC_TANGENT:
                return TrigType.TANGENT;
            case ARC_COSECANT:
                return TrigType.COSECANT;
            case ARC_SECANT:
                return TrigType.SECANT;
            case ARC_COTANGENT:
                return TrigType.COTANGENT;
            default:
                return TrigType.SINE;
        }
    }

    @Override
    public final TrigTerm applyInverseTo(Object arg) {
        if (arg instanceof PowerTerm) {
            PowerTerm powTerm = (PowerTerm) arg;
            if (!getPower().equals(1)) {
                powTerm = applyInversePowTo(arg);
                setPower(1);
            }

            return new TrigTerm(1, new Variable<PowerTerm>(powTerm), inverseType());

        } else if (arg instanceof MathEQ) {

            if (!getPower().equals(1)) {

            }

            return new TrigTerm(1, new Variable<MathEQ>((MathEQ) arg), inverseType());

        }
        return null;
    }
    // #endregion

    // #region Comparison Bools
    @Override
    public final boolean equalsCriteria(PowerTerm arg) {
        TrigTerm other = (TrigTerm) arg;
        return type == other.getTrigType();
    }
    // #endregion
}
