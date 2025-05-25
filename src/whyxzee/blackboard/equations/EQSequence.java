package whyxzee.blackboard.equations;

import java.util.ArrayList;

import whyxzee.blackboard.terms.*;

/**
 * A package for equations that are a sequence, meaning that they are chained
 * with addition and subtraction.
 * 
 * <p>
 * The functionality of this class has been checked on {@code 5/16/2025}, and
 * nothing has changed since.
 */
public class EQSequence extends MathFunction {

    public EQSequence(Term... terms) {
        super(FunctionType.SEQUENCE, terms);
    }

    public EQSequence(ArrayList<Term> terms) {
        super(FunctionType.SEQUENCE, terms);
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
    public void simplify() {
        /* Initializing variables */
        ArrayList<Term> newTermList = new ArrayList<Term>();

        /* Polynomial Term */
        Term polyTerm = PolynomialTerm.add(getPolynomialTerms());
        if (polyTerm != null) {
            switch (polyTerm.getVar().getVarType()) {
                case U_SUB_EQ:
                    newTermList.addAll(polyTerm.getVar().getInnerFunction().getTermArray());
                default:
                    newTermList.add(polyTerm);
                    break;
            }
        }

        /* Exponential Term */
        Term expTerm = ExponentialTerm.add(getExponentialTerms());
        if (expTerm != null) {
            switch (expTerm.getVar().getVarType()) {
                case U_SUB_EQ:
                    newTermList.addAll(expTerm.getVar().getInnerFunction().getTermArray());
                default:
                    newTermList.add(expTerm);
                    break;
            }
        }

        /* Signum Term */
        Term signTerm = SignumTerm.add(getSignumTerms());
        if (signTerm != null) {
            switch (signTerm.getVar().getVarType()) {
                case U_SUB_EQ:
                    newTermList.addAll(signTerm.getVar().getInnerFunction().getTermArray());
                default:
                    newTermList.add(signTerm);
                    break;
            }
        }

        /* Override */
        setTermArray(newTermList);
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
