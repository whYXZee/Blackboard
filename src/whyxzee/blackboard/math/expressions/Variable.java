package whyxzee.blackboard.math.expressions;

/**
 * <p>
 * The functionality of this class has not been checked.
 */
public class Variable<T> {
    public static final Variable<String> noVar = new Variable<String>("");

    private final T inner;

    // #region Constructors
    public Variable(T inner) {
        this.inner = inner;
    }
    // #endregion

    // #region Copying/Cloning
    public Variable<?> clone() {
        if (inner instanceof String) {
            return new Variable<String>((String) inner);
        }
        return new Variable<String>("");
    }
    // #endregion

    // #region Inner
    /**
     * <em>Calling getInner() will return the reference to the inner value of
     * <b>this</b>.</em>
     * 
     * @return
     */
    public T getInner() {
        return inner;
    }

    public Class<?> getInnerClass() {
        return inner.getClass();
    }
    // #endregion

    // #region Comparison Bools
    @Override
    public boolean equals(Object arg0) {
        if (arg0.getClass() == getInnerClass()) {
            return inner.equals(arg0);
        } else if (arg0 instanceof Variable) {
            return equals(((Variable<?>) arg0).getInner());
        }
        return false;
    }
    // #endregion
}
