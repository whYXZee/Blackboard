package whyxzee.blackboard.math.pure.numbers;

public class DoesNotExist extends BNumber {

    public DoesNotExist() {
        super(Double.NaN, Double.NaN);
    }

    @Override
    public final String toString() {
        return "DNE";
    }

    // #region Number Type Bools

    /* idk if any of this is needed */
    // @Override
    // public final boolean isComplex() {
    // return false;
    // }

    // @Override
    // public final boolean isImaginary() {
    // return false;
    // }

    // @Override
    // public final boolean isReal() {
    // return false;
    // }

    // @Override
    // public final boolean isRational(int sigFigs) {
    // return false;
    // }

    // @Override
    // public final boolean isUncountable() {
    // return false;
    // }

    @Override
    public final boolean isDNE() {
        return true;
    }
    // #endregion
}
