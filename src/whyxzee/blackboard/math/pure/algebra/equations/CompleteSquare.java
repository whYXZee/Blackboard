package whyxzee.blackboard.math.pure.algebra.equations;

import java.util.ArrayList;

import whyxzee.blackboard.math.applied.settheory.DefinedList;
import whyxzee.blackboard.math.pure.equations.SequentialFunc;
import whyxzee.blackboard.math.pure.numbers.BNumber;
import whyxzee.blackboard.math.pure.terms.PowerTerm;
import whyxzee.blackboard.math.pure.terms.Term;
import whyxzee.blackboard.math.pure.terms.variables.USub;

/**
 * A package for solving "completing the square" style of problems.
 * 
 * <p>
 * The functionality of this class has been checked on <b>6/14/2025</b> and the
 * following needs to be addressed:
 * <ul>
 * <li>implementation for complex/imaginary numbers
 */
public class CompleteSquare {
    // TODO: complex/imaginary numbers?
    public static final DefinedList performOp(String varToSolve, ArrayList<Term> leftSide, BNumber rightSide) {
        /* Initializing variables */
        BNumber a = leftSide.get(0).getCoef(); // first term is highest power
        Term term = leftSide.get(1);
        BNumber b = term.getCoef();
        BNumber c = leftSide.get(2).getCoef();
        BNumber square;
        BNumber rightAddend;

        if (a.equals(1)) {
            square = b.clone();
            square.multiplyScalar(0.5);
            rightAddend = square.clone();
            rightAddend.power(2);
        } else {
            BNumber bPrime = b.clone();
            bPrime.divide(a);
            square = bPrime.clone();
            square.multiplyScalar(0.5);
            rightAddend = square.clone();
            rightAddend.power(2);
            rightAddend.multiply(a);
        }

        rightSide.add(c.negate(), rightAddend);
        term.setCoef(1);
        SequentialFunc binom = new SequentialFunc(term, new PowerTerm(square));
        SequentialFunc lSide = new SequentialFunc(new PowerTerm(a, new USub(binom), 2));

        return SolveFor.performOp(varToSolve, lSide, new SequentialFunc(rightSide.toTerm().toTermArray()));
    }

}
