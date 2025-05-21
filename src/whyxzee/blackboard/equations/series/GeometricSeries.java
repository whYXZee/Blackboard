package whyxzee.blackboard.equations.series;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.equations.EQMultiplication;
import whyxzee.blackboard.terms.ExponentialTerm;
import whyxzee.blackboard.terms.variables.USub;

public class GeometricSeries extends SeriesAbstract {
    /* Equation */
    private double ratio;
    private double a_1;

    public GeometricSeries(double ratio, double a_1, int lowerBound, int upperBound) {
        super(lowerBound, upperBound, SeriesType.GEOMETRIC);
        this.ratio = ratio;
        this.a_1 = a_1;

        setGeneralFunction(new EQMultiplication(
                new ExponentialTerm(a_1, new USub(Constants.SeriesConstants.N_MINUS_ONE), ratio)));
    }

    // @Override
    // public double aOfN(int n) {
    // n = nInBounds(n);
    // return Math.pow(ratio, n - 1) * a_1;
    // }

    @Override
    public double partialSum(int n) {
        n = nInBounds(n);
        return a_1 * ((1 - Math.pow(ratio, n)) / (1 - ratio));
    }

    @Override
    public double limInfSolve() {
        if (Math.abs(ratio) < 1) {
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

    //
    // Series Tests
    //

    @Override
    public double nthTermTest() {
        if (Math.abs(ratio) < 1) {
            return 0;
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

    @Override
    public double alternatingSeriesError(int n) {
        if (ratio < 0 && ratio > -1) {
            // alternating series
            return Math.abs(aOfN(n + 1));
        }
        // not alternating series, so no error
        return Double.NaN;
    }

}
