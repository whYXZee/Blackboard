package whyxzee.blackboard.equations.series;

public class PowerSeries extends SeriesAbstract {
    /* Equation */
    private double power;
    private double a_1;

    public PowerSeries(double power, double a_1, int lowerBound, int upperBound) {
        super(lowerBound, upperBound, SeriesType.P_SERIES);
        this.power = power;
    }

    @Override
    public double aOfN(int n) {
        return a_1 * Math.pow(n, power);
    }

    @Override
    public double partialSum(int n) {
        double output = 0;

        /* ensure that the n is not greater than the end index */
        if (n > getUpperBound()) {
            n = getUpperBound();
        }

        /* Manually add each term */
        for (int i = getLowerBound(); i < n; i++) {
            output += aOfN(i);
        }
        return output;
    }

    //
    // Series tests
    //
    @Override
    public double nthTermTest() {
        if (power > 0) {
            // power is in the denominator
            return 0;
        } else {
            // power is in the numerator or power is 0
            if (a_1 > 0) {
                // positive a_1
                return Double.POSITIVE_INFINITY;
            } else {
                // negative a_1
                return Double.NEGATIVE_INFINITY;
            }
        }
    }
}
