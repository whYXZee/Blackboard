package whyxzee.blackboard.terms;

import whyxzee.blackboard.terms.variables.Variable;

public class IntegralTerm extends Term {
    /* Variables */
    private double lowerBound;
    private double upperBound;

    public IntegralTerm(double num, Variable var, double lowerBound, double upperBound) {
        super(num, var, TermType.INTEGRAL);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    public String printConsole() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printConsole'");
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public double solve(double value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'solve'");
    }

    @Override
    public Term negate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'negate'");
    }

    @Override
    public Term derive() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'derive'");
    }

    @Override
    public double limInfSolve() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'limInfSolve'");
    }
}
