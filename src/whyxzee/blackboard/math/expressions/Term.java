package whyxzee.blackboard.math.expressions;

import whyxzee.blackboard.math.number.BNum;

public class Term {
    // #region Variables
    private BNum coef;
    private Variable<?> var;
    private BNum power;
    private TermData data;
    // #endregion

    // #region Constructors
    /**
     * 
     * @param coef
     * @param var
     * @param power
     * @param data
     */
    public Term(BNum coef, Variable<?> var, BNum power, TermData data) {
        this is a test lol
        this.coef = coef;
        this.var = var;
        this.power = power;
        this.data = data;
    }
    // #endregion

    @Override
    public String toString() {
        if (power.isZero() || var.equals("")) {
            return coef.toString();
        } else if (power.equals(1)) {
            return coef.asCoef() + var.toString();
        }
        return coef.asCoef() + var.toString() + "^(" + power.toString() + ")";

    }

    // #region Copying/Cloning
    public Term clone() {
        return new Term(coef.clone(), var.clone(), power.clone(), data.clone());
    }

    // #endregion

    // #region Coef
    /**
     * <em>Calling getCoef() will return the reference of the coefficient of
     * <b>this</b>.</em>
     * 
     * @return the coefficient of this term
     */
    public final BNum getCoef() {
        return coef;
    }
    // #endregion

    // #region Variable
    /**
     * <em>Calling cloneVar() will return a deep copy of the variable of
     * <b>this</b>.</em>
     * 
     * @return
     */
    public final Variable<?> cloneVar() {
        return var.clone();
    }

    /**
     * <em>Calling getVar() will return the reference to the variable of
     * <b>this</b>.</em>
     * 
     * @return the variable of this term
     */
    public final Variable<?> getVar() {
        return var;
    }
    // #endregion

    // #region Power
    /**
     * <em>Calling getPower() will return the reference of the power of
     * <b>this</b>.</em>
     * 
     * @return the power of this term
     */
    public final BNum getPower() {
        return power;
    }

    /**
     * Updates the power of <b>this</b> to <b>power</b>.
     * 
     * @param power the new power of <b>this</b> term.
     */
    public final void setPower(BNum power) {
        this.power = power;
    }
    // #endregion

    // #region Term Data
    /**
     * @param data
     */
    public final void setData(TermData data) {
        this.data = data;
    }

    /**
     * @return the class of <b>data</b>.
     */
    public final Class<?> getDataClass() {
        return data.getClass();
    }
    // #endregion

    // #region Addition
    /**
     * Checks if the <b>addend</b> can be added to <b>this</b>. The conditions are
     * based on the <b>data</b> of <b>this</b>.
     * 
     * @param addend
     * @return {@code true} if <b>addend</b> can be added onto
     */
    public boolean isAddend(Term addend) {
        return data.isAddend(this, addend);
    }

    /**
     * Adds the <b>addend</b> onto <b>this</b>.
     * 
     * <p>
     * <em>Calling add() will not change the data of <b>this</b> nor the data of
     * <b>addend</b>.
     * 
     * @param addend
     * @return
     */
    public Term add(Term addend) {
        return data.add(this, addend);
    }
    // #endregion

    // #region Multiplication
    /**
     * Checks if the <b>factor</b> can be multiplied to <b>this</b>.
     * 
     * @param factor
     * @return
     */
    public boolean isFactor(Term factor) {
        return data.isFactor(this, factor);
    }

    /**
     * Multiplies the <b>factor</b> onto <b>this</b>.
     * 
     * <p>
     * <em>Calling multiply() will not change the data of <b>this</b> nor the data
     * of <b>factor</b>.</em>
     * 
     * @param factor
     * @return
     */
    public Term multiply(Term factor) {
        return data.multiply(this, factor);
    }
    // #endregion

    // #region Inverse
    /**
     * Applies the inverse power of <b>this</b> onto <b>o</b>.
     * 
     * <p>
     * <em>Calling applyInvPowTo() will not change the data of <b>this</b> nor the
     * data of <b>o</b>.</em>
     * 
     * @param o the term which will have the inverse power applied.
     * @return a deep copy of <b>o</b> with the inverse power of <b>this</b> applied
     *         to it.
     */
    public Term applyInvPowTo(Term o) {
        Term oCopy = o.clone();
        BNum pow = oCopy.getPower();
        pow = pow.divide(this.getPower());
        oCopy.setPower(pow);
        return oCopy;
    }

    /**
     * Applies the inverse of <b>this</b> onto <b>o</b>. Calls
     * {@link whyxzee.blackboard.math.expressions.Term#applyInvPowTo(Term)} as well.
     * 
     * <p>
     * <em>Calling applyInvTo() will not change the data of <b>this</b> nor the data
     * of <b>o</b>.</em>
     * 
     * @param o the term which will have the inverse power applied.
     * @return
     */
    public Term applyInvTo(Term o) {
        Term oCopy = power.equals(1) ? o.clone() : applyInvPowTo(o);

        return data.applyInverseTo(oCopy);
    }
    // #endregion
}
