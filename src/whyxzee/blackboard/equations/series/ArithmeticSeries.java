package whyxzee.blackboard.equations.series;

public class ArithmeticSeries extends SeriesAbstract {
    //
    // Variables
    //
    private double difference;
    private double a_1;

    public ArithmeticSeries(double difference, double a_1, int startIndex, int endIndex) {
        super(startIndex, endIndex);
        this.difference = difference;
        this.a_1 = a_1;
    }

    @Override
    public double aOfN(int n) {
        return (difference * (n - 1)) + a_1;
    }

    @Override
    public double partialSum(int n) {
        return ((double) n / 2) * (a_1 + aOfN(n));
    }

    public double limInfSolve() {
        if (difference > 0) {
            // positive difference
            return Double.POSITIVE_INFINITY;
        } else {
            return Double.NEGATIVE_INFINITY;
        }
    }

}
