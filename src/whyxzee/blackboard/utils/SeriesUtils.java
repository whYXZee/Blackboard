package whyxzee.blackboard.utils;

import whyxzee.blackboard.equations.EQSequence;
import whyxzee.blackboard.equations.MathFunction;
import whyxzee.blackboard.terms.ExponentialTerm;
import whyxzee.blackboard.terms.PolynomialTerm;
import whyxzee.blackboard.terms.variables.Variable;

public class SeriesUtils {
    public static final class ComparisonSeries {
        /* Geometric Series */
        public static final MathFunction GEOMETRIC_DIVERGING = new EQSequence(
                new ExponentialTerm(1, new Variable("n", 1), 2));
        public static final MathFunction GEOMETRIC_CONVERGING = new EQSequence(
                new ExponentialTerm(1, new Variable("n", 1), 0.5));

        /* P-Series */
        public static final MathFunction P_SERIES_DIVERGING = new EQSequence(
                new PolynomialTerm(1, new Variable("n", -1, 2)));
        public static final MathFunction P_SERIES_CONVERGING = new EQSequence(
                new PolynomialTerm(1, new Variable("n", -2)));
        public static final MathFunction P_SERIES_POWER_1 = new EQSequence(
                new PolynomialTerm(1, new Variable("n", -1)));
    }
}
