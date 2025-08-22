package whyxzee.blackboard.math.expressions;

/**
 * <p>
 * The functionality of this class has not been checked.
 */
public interface TermData {
    /**
     * @return a deep copy of <b>this</b>.
     */
    public TermData clone();

    /**
     * Returns the String that is used to display this term, with all of the
     * TermData. This method is utilized when printing a term.
     * 
     * @param varString is the String of the term's variable.
     * @return
     */
    public String termString(String varString);

    // #region Addition
    /**
     * Checks if <b>o</b> is an potential addend of <b>dis</b>. The conditions are
     * based on the implementations of this interface.
     * 
     * @param dis
     * @param o
     * @return {@code }
     */
    public boolean isAddend(Term dis, Term o);

    /**
     * Adds two like terms together.
     * 
     * <p>
     * <em>Calling add() does not change the data of <b>dis</b> nor the data of
     * <b>o</b>.
     * 
     * @param dis is the term with <b>this</b> TermData
     * @param o
     * @return the sum of the two terms.
     */
    public Term add(Term dis, Term o);
    // #endregion

    // #region Multiplication
    /**
     * Checks if <b>o</b> can be multiplied into <b>dis</b>. The conditions are
     * based on the implementations of this interface.
     * 
     * @param dis is the term with <b>this</b> TermData.
     * @param o
     * @return
     */
    public boolean isFactor(Term dis, Term o);

    /**
     * Multiplies two similar terms together.
     * 
     * <p>
     * <em>Calling multiply() does not change the data of <b>dis</b> nor the data of
     * <b>o</b>.
     * 
     * @param dis
     * @param o
     * @return the product of the two terms.
     */
    public Term multiply(Term dis, Term o);
    // #endregion

    // #region Inverse
    /**
     * Retruns the TermData of what would be the inverse of <b>this</b>.
     * 
     * @return
     */
    public TermData inverse();

    /**
     * Applies the inverse of <b>this</b> data onto <b>o</b>.
     * 
     * <p>
     * <em>Calling applyInverseTo() does not change the data of
     * <b>applyInverseTo</b> nor the data of <b>this</b>.</em>
     * 
     * @param o
     * @return
     */
    public Term applyInverseTo(Term o);
    // #endregion

    // #region Comparison Bools
    public boolean equals(Object o);

    /**
     * Checks if two terms are equal to each other. The conditions are
     * based on the implementations of this interface.
     * 
     * @param a is the term with <b>this</b> TermData.
     * @param o is the term to compare
     * @return
     */
    public boolean equals(Term a, Term o);
    // #endregion

}
