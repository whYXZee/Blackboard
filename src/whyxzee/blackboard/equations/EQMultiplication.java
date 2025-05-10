package whyxzee.blackboard.equations;

import whyxzee.blackboard.terms.Term;

public class EQMultiplication extends MathFunction {

    /**
     * 
     * @param constant the coefficient of the equation.
     * @param terms
     */
    public EQMultiplication(Term... terms) {
        super(terms);
    }

    @Override
    public void simplify() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'simplify'");
    }

    @Override
    public double solve(double value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'solve'");
    }

    @Override
    public MathFunction derive() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'derive'");
    }

}
