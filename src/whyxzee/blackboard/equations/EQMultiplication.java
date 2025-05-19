package whyxzee.blackboard.equations;

import java.util.ArrayList;

import whyxzee.blackboard.terms.Term;

/**
 * A package for an equation based on multiplication
 * 
 * <p>
 * The functionality of this class has not been checked, and it does not work.
 */
public class EQMultiplication extends MathFunction {

    /**
     * 
     * @param constant the coefficient of the equation.
     * @param terms
     */
    public EQMultiplication(Term... terms) {
        super(FunctionType.MULTIPLICATION, terms);
    }

    public EQMultiplication(ArrayList<Term> terms) {
        super(FunctionType.MULTIPLICATION, terms);
    }

    @Override
    public String toString() {
        String output = "";
        for (Term term : getTermArray()) {
            output += "(" + term.toString() + ")";
        }
        return output;
    }

    @Override
    public String printConsole() {
        String output = "";
        for (Term term : getTermArray()) {
            output += "(" + term.printConsole() + ")";
        }
        return output;
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
