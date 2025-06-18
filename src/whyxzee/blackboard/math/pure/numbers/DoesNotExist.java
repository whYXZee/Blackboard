package whyxzee.blackboard.math.pure.numbers;

public class DoesNotExist extends BNumber {

    public DoesNotExist() {
        super(Double.NaN, Double.NaN);
    }

    @Override
    public final String toString() {
        return "DNE";
    }

    ///
    /// Boolean Methods
    ///
    @Override
    public final boolean isDNE() {
        return true;
    }
}
