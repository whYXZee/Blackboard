package whyxzee.blackboard.math.pure.equations.terms;

public abstract class TermData {
    /* Identification */
    private TermType type;

    public enum TermType {
        POWER,
        TRIG
    }

    public TermData(TermType type) {
        this.type = type;
    }

    // #region TermType
    public final TermType getTermType() {
        return type;
    }
    // #endregion

    // #region Copying/Cloning
    public abstract TermData clone();
    // #endregion

    // #region String/Display
    public abstract String termString(String varString);
    // #endregion

    // #region Addition
    /**
     * Checks if <b>b</b> is an potential addend of <b>a</b>
     * 
     * @param a
     * @param b
     * @return
     */
    public abstract boolean isAddend(PowerTerm a, PowerTerm b);

    public abstract PowerTerm add(PowerTerm a, PowerTerm b);
    // #endregion

    // #region Multiplication
    /**
     * Checks if <b>b</b> is a clean multiply for <b>a</b>.
     * 
     * @param a
     * @param b
     * @return
     */
    public abstract boolean isFactor(PowerTerm a, PowerTerm b);

    public abstract PowerTerm multiply(PowerTerm a, PowerTerm b);
    // #endregion

    // #region Inverse
    /**
     * Retruns the inverse TermData of <b>this</b>.
     * 
     * @return
     */
    public abstract TermData inverse();

    public abstract PowerTerm applyInverseTo(PowerTerm a, PowerTerm o);
    // #endregion

    // #region Comparison Bools
    public abstract boolean equals(Object o);

    public abstract boolean equals(PowerTerm a, PowerTerm o);
    // #endregion

}
