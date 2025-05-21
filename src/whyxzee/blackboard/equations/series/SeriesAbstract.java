package whyxzee.blackboard.equations.series;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.display.BlackboardDisplay;
import whyxzee.blackboard.equations.MathFunction;

/**
 * A package for series equaations.
 * 
 * <p>
 * The functionality of this class has not been checked.
 */
public class SeriesAbstract {
    /* General Series Variables */
    private MathFunction generalFunction;
    private int lowerBound;
    private int upperBound;

    /* Series classification */
    private SeriesType seriesType;
    private boolean isInfinite;
    private boolean isAlternating;

    public enum SeriesType {
        ARITHMETIC,
        GEOMETRIC,
        P_SERIES,
        NONE
    }

    public SeriesAbstract(MathFunction generalFunction, int lowerBound, int upperBound) {
        /* Series Construction */
        this.generalFunction = generalFunction;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;

        /* Series Identification */
        seriesType = SeriesType.NONE;
        this.isInfinite = false;
        this.isAlternating = false;
    }

    public SeriesAbstract(MathFunction generalFunction, int lowerBound) {
        /* Series Construction */
        this.generalFunction = generalFunction;
        this.lowerBound = lowerBound;
        this.upperBound = Integer.MAX_VALUE;

        /* Series Identification */
        seriesType = SeriesType.NONE;
        this.isInfinite = true;
        this.isAlternating = false;
    }

    public SeriesAbstract(int startIndex, int endIndex, SeriesType seriesType) {
        /* Series Construction */
        this.generalFunction = null;
        this.lowerBound = startIndex;
        this.upperBound = endIndex;

        /* Series Identification */
        this.seriesType = seriesType;
        this.isInfinite = false;
        this.isAlternating = false;

        /* Infinity Upper Bound */
        if (endIndex == Integer.MAX_VALUE) {
            isInfinite = true;
        }
    }

    public String toString() {
        if (isInfinite) {
            return Constants.Unicode.UPPERCASE_SIGMA + generalFunction.toString() + "\nLower Bound: n = " + lowerBound
                    + ", Upper Bound: " + Constants.Unicode.INFINITY;
        }
        return Constants.Unicode.UPPERCASE_SIGMA + generalFunction.toString() + "\nLower Bound: n = " + lowerBound
                + ", Upper Bound: " + upperBound;
    }

    public String printConsole() {
        if (isInfinite) {
            return Constants.Unicode.UPPERCASE_SIGMA + generalFunction.printConsole() + "\nLower Bound: n = "
                    + lowerBound + ", Upper Bound: " + Constants.Unicode.INFINITY;
        }
        return Constants.Unicode.UPPERCASE_SIGMA + generalFunction.printConsole() + "\nLower Bound: n = " + lowerBound
                + ", Upper Bound: " + upperBound;
    }

    public void createBlackboardLabels(BlackboardDisplay display) {
        // TODO: unimplemented method "createBlackboardLabels()"
        throw new UnsupportedOperationException("Unimplemented method 'createBlackboardLabels'");
    }

    //
    // Arithmetic Methods
    //

    public final int nInBounds(int n) {
        if (n > upperBound) {
            return upperBound;
        }
        return n;
    }

    public double aOfN(int n) {
        n = nInBounds(n);
        return generalFunction.solve(n);
    }

    public double partialSum(int n) {
        double output = 0;
        for (int i = lowerBound; i < n; i++) {
            output += aOfN(i);
        }
        return output;
    }

    /**
     * @return the value of the series as n approaches infinity.
     */
    public double limInfSolve() {
        throw new UnsupportedOperationException("Unsupported method 'limInfSolve'");
    }

    //
    // Series Tests
    //
    /**
     * 
     * @return the value of the general equation as n approaches infinity.
     */
    public double nthTermTest() {
        // TODO: unimplemented method "nthTermTest()"
        throw new UnsupportedOperationException("Unimplemented method 'nthTermTest'");
    }

    public void integralTest() {
        // TODO: unimplemented method "integralTest()"
        throw new UnsupportedOperationException("Unimplemented method 'integralTest'");
    }

    public void directComparison() {
        // TODO: unimplemented method "directComparison()"
        throw new UnsupportedOperationException("Unimplemented method 'directComparison'");
    }

    public void limitComparison() {
        // TODO: unimplemented method "lmiitComparison()"
        throw new UnsupportedOperationException("Unimplemented method 'limitComparison'");
    }

    public void ratioTest() {
        // TODO: unimplemented method "ratioTest()"
        throw new UnsupportedOperationException("Unimplemented method 'ratioTest'");
    }

    public boolean alternatingSeriesTest() {
        if (nthTermTest() != 0) {
            // step one: nth term test. if failed, then can't be alternating
            return false;
        }

        // TODO: unimplemented method "alternatingSeriesTest()"
        throw new UnsupportedOperationException("Uncomplete method 'alternatingSeriesTest'");
    }

    /**
     * 
     * @param n the index of the series
     * @return the error of the series. If NaN, then the series is not alternating.
     */
    public double alternatingSeriesError(int n) {
        if (alternatingSeriesTest() && isAlternating) {
            // if the series is alternating and is a valid alternating series
            return Math.abs(generalFunction.solve(n + 1));
        }
        // non alternating series, so no error
        return Double.NaN;
    }

    //
    // Get & Set Methods
    //
    public final boolean getIsInfinite() {
        return isInfinite;
    }

    public final void setIsInfinite(boolean isInfinite) {
        this.isInfinite = isInfinite;
    }

    public MathFunction getGeneralFunction() {
        return generalFunction;
    }

    public final void setGeneralFunction(MathFunction generalFunction) {
        this.generalFunction = generalFunction;
    }

    public final int getLowerBound() {
        return lowerBound;
    }

    public final void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    public final int getUpperBound() {
        return upperBound;
    }

    public final void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }

    public final SeriesType getSeriesType() {
        return seriesType;
    }

    public final void setSeriesType(SeriesType seriesType) {
        this.seriesType = seriesType;
    }

    /**
     * @return the number of terms in the series
     */
    public final int getTermNumber() {
        if (isInfinite) {
            // infinite series have infinite terms
            return Integer.MAX_VALUE;
        }
        return upperBound - lowerBound + 1;
    }
}