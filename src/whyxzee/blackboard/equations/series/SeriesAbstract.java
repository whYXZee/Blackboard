package whyxzee.blackboard.equations.series;

import whyxzee.blackboard.equations.MathFunction;

public class SeriesAbstract {
    /*  */
    private MathFunction generalFunction;
    private int startIndex;
    private int endIndex;
    private boolean isInfinite;

    public SeriesAbstract(MathFunction generalFunction, int startIndex, int endIndex) {
        this.generalFunction = generalFunction;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.isInfinite = false;
    }

    public SeriesAbstract(MathFunction generalFunction, int startIndex) {
        this.generalFunction = generalFunction;
        this.startIndex = startIndex;
        this.endIndex = Integer.MAX_VALUE;
        this.isInfinite = true;
    }

    public SeriesAbstract(int startIndex, int endIndex) {
        this.generalFunction = null;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.isInfinite = false;
    }

    public double aOfN(int n) {
        return generalFunction.solve(n);
    }

    public double partialSum(int n) {
        return 0;
    }

    public double limInfSolve() {
        return 0;
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

    public final MathFunction getGeneralFunction() {
        return generalFunction;
    }

    public final void setGeneralFunction(MathFunction generalFunction) {
        this.generalFunction = generalFunction;
    }

    public final int getStartIndex() {
        return startIndex;
    }

    public final void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public final int getEndIndex() {
        return endIndex;
    }

    public final void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
}