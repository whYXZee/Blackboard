package whyxzee.blackboard.math.pure.equations.terms;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.math.pure.equations.variables.Variable;
import whyxzee.blackboard.math.pure.numbers.Complex;
import whyxzee.blackboard.math.utils.pure.NumberUtils;

/**
 * A package for plus or minus terms. This package is constructed as an
 * {@code +- a*(x)^n} term.
 * 
 * <p>
 * The functionality of this class has not been checked.
 */
public class PlusMinusTerm extends PowerTerm {
    // #region Constructors
    /**
     * Constructor for a plus-or-minus term with a real number.
     * 
     * @param coef
     */
    public PlusMinusTerm(double coef) {
        super(coef, Variable.noVar);
    }

    /**
     * Constructor for a plus-or-minus term with a complex number.
     * 
     * @param coef
     */
    public PlusMinusTerm(Complex coef) {
        super(coef, Variable.noVar);
    }

    /**
     * Constructor for a plus-or-minus term with a real number and variable.
     * 
     * @param coef
     */
    public PlusMinusTerm(double coef, Variable<?> var) {
        super(coef, var);
    }

    /**
     * Constructor for a plus-or-minus term with a complex number and variable.
     * 
     * @param coef
     */
    public PlusMinusTerm(Complex coef, Variable<?> var) {
        super(coef, var);
    }
    // #endregion

    // #region String / Display
    @Override
    public final String toString() {
        String output = "";
        output += Constants.Unicode.PLUS_MINUS;

        /* Coefficient */
        if (getCoef().isZero()) {
            return "0";
        }
        output += coefString();
        output += getVar();

        return output;
    }

    @Override
    public final String negativeString() {
        return toString();
    }
    // #endregion

    // #region Copying / Cloning
    @Override
    public final PowerTerm clone() {
        return new PlusMinusTerm(getCoef().clone(), getVar().clone());
    }
    // #endregion

    // #region Coefficient
    @Override
    public PowerTerm negate() {
        return this.clone();
    }
    // #endregion

    // #region Solve
    @Override
    public final PowerTerm solve(String variable, PowerTerm value) {
        if (getVar().equals(Variable.noVar)) {
            return this;
        } else {
            PowerTerm solved = getVar().solve(variable, value);
            if (solved.isConstant()) {
                Complex val = NumberUtils.multiply(getCoef(), NumberUtils.pow(solved.getCoef(), getPower()));
                return new PlusMinusTerm(val);
            } else {
                return new PlusMinusTerm(getCoef(), new Variable<PowerTerm>(solved));
            }
        }
    }
    // #endregion

    // #region Addition
    @Override
    public final boolean isAddend(PowerTerm addend) {
        return false;
    }

    @Override
    public final void add(PowerTerm addend) {
    }
    // #endregion

    // #region Multiplication
    @Override
    public final boolean isFactor(PowerTerm factor) {
        if (!(factor instanceof PlusMinusTerm)) {
            return false;
        }
        return true;
    }

    @Override
    public final void multiply(PowerTerm factor) {
        setCoef(NumberUtils.multiply(getCoef(), factor.getCoef()));

        // TODO: multiply variables
    }
    // #endregion
}
