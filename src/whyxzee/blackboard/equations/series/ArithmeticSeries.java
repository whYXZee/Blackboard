package whyxzee.blackboard.equations.series;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.equations.EQSequence;
import whyxzee.blackboard.terms.PolynomialTerm;
import whyxzee.blackboard.terms.variables.USub;

public class ArithmeticSeries extends SeriesAbstract {
    //
    // Variables
    //
    private double difference;
    private double a_1;

    public ArithmeticSeries(double difference, double a_1, int lowerBound, int upperBound) {
        super(lowerBound, upperBound, SeriesType.ARITHMETIC);
        this.difference = difference;
        this.a_1 = a_1;

        setGeneralFunction(new EQSequence(
                new PolynomialTerm(difference, new USub(Constants.SeriesConstants.N_MINUS_ONE)),
                new PolynomialTerm(a_1)));
    }

    //
    // Arithmetic Methods
    //

    // @Override
    // public double aOfN(int n) {
    // n = nInBounds(n);
    // return (difference * (n - 1)) + a_1;
    // }

    @Override
    public double partialSum(int n) {
        n = nInBounds(n);
        return ((double) n / 2) * (a_1 + aOfN(n));
    }

    @Override
    public double limInfSolve() {
        if (difference > 0) {
            // positive difference
            return Double.POSITIVE_INFINITY;
        } else {
            return Double.NEGATIVE_INFINITY;
        }
    }
}
