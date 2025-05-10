package whyxzee.blackboard.equations.series;

import whyxzee.blackboard.equations.MathFunction;
import whyxzee.blackboard.terms.values.Value;

public abstract class SeriesAbstract {
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

    public abstract double aOfN(int n);

    public abstract double partialSum(int n);
}