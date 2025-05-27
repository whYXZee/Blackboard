package whyxzee.blackboard.equations;

import java.util.ArrayList;

import whyxzee.blackboard.terms.*;
import whyxzee.blackboard.terms.Term.TermType;
import whyxzee.blackboard.utils.SortingUtils;

/**
 * A package for equations that are a sequence, meaning that they are chained
 * with addition and subtraction.
 * 
 * <p>
 * The functionality of this class has been checked on {@code 5/16/2025}, and
 * nothing has changed since.
 */
public class EQSequence extends MathFunction {
    private boolean shouldAutoCondense;

    public EQSequence(boolean shouldAutoCondense, Term... terms) {
        super(FunctionType.SEQUENCE, terms);
        this.shouldAutoCondense = shouldAutoCondense;
    }

    public EQSequence(Term... terms) {
        super(FunctionType.SEQUENCE, terms);
        this.shouldAutoCondense = true;
    }

    public EQSequence(boolean shouldAutoCondense, ArrayList<Term> terms) {
        super(FunctionType.SEQUENCE, terms);
        this.shouldAutoCondense = shouldAutoCondense;
    }

    public EQSequence(ArrayList<Term> terms) {
        super(FunctionType.SEQUENCE, terms);
        this.shouldAutoCondense = true;
    }

    @Override
    public String toString() {
        String output = "";
        ArrayList<Term> termArray = getTermArray();

        for (int i = 0; i < termArray.size(); i++) {
            Term term = termArray.get(i);

            if (i == 0) {
                output += term;
            } else {
                if (term.isNegative()) {
                    output += " - " + term.negate();
                } else {
                    output += " + " + term;
                }
            }

        }

        return output;
    }

    @Override
    public String printConsole() {
        String output = "";
        ArrayList<Term> termArray = getTermArray();

        for (int i = 0; i < termArray.size(); i++) {
            Term term = termArray.get(i);

            if (i == 0) {
                output += term.printConsole();
            } else {
                if (term.isNegative()) {
                    output += " - " + term.negate().printConsole();
                } else {
                    output += " + " + term.printConsole();
                }
            }

        }

        return output;
    }

    @Override
    public final void simplify() {
        for (TermType i : Term.TermType.values()) {
            performAdditionOn(i);
        }

        // if (shouldAutoCondense) {
        // /* Condensing Exponentials */
        // condense(TermType.EXPONENTIAL);
        // }
    }

    @Override
    public void merge(MathFunction function) {
        if (function.getFunctionType() == FunctionType.SEQUENCE) {
            addToTermArray(function.getTermArray());
        }
    }

    //
    // Arithmetic Methods
    //

    @Override
    public double solve(double value) {
        double output = 0;

        for (Term term : getTermArray()) {
            output += term.solve(value);
        }

        return output;
    }

    @Override
    public MathFunction derive() {
        ArrayList<Term> outputTerms = new ArrayList<Term>();
        for (Term term : getTermArray()) {
            Term derived = term.derive();
            if (!derived.equals(PolynomialTerm.ZERO_TERM)) {
                // if the derivative is not 0 nor DNE
                outputTerms.add(derived);
            }
        }

        return new EQSequence(outputTerms);
    }
}
