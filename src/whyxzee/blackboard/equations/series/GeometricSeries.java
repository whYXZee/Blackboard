package whyxzee.blackboard.equations.series;

public class GeometricSeries extends SeriesAbstract {
    /* Equation */
    private double ratio;
    private double a_1;

    public GeometricSeries(double ratio, double a_1, int startIndex, int endIndex) {
        super(startIndex, endIndex);
        this.ratio = ratio;
        this.a_1 = a_1;

        if (Math.abs(ratio) < 1) {
            setIsInfinite(true);
        }
    }

    @Override
    public double aOfN(int n) {
        return Math.pow(ratio, n) * a_1;
    }

    @Override
    public double partialSum(int n) {
        return a_1 * ((1 - Math.pow(ratio, n)) / (1 - ratio));
    }

    public double limInfSolve() {
        if (getIsInfinite()) {
            // infinite sum
            return a_1 * (1.0 / (1 - ratio));
        } else {
            // should technically be pos infinity or DNE cuz of 2 numbers
            if (a_1 > 0) {
                // positive starting term
                return Double.POSITIVE_INFINITY;
            } else {
                // negative starting term;
                return Double.NEGATIVE_INFINITY;
            }
        }
    }

}
