package whyxzee.blackboard.math.pure.equations;

import java.util.ArrayList;

import whyxzee.blackboard.math.pure.terms.Term;
import whyxzee.blackboard.math.pure.terms.Term.TermType;
import whyxzee.blackboard.math.pure.terms.arithmetic.AdditionAbstract;
import whyxzee.blackboard.math.pure.terms.arithmetic.MultiplicationAbstract;
import whyxzee.blackboard.utils.SortingUtils;

/**
 * The highest-class for generic math functions.
 */
public abstract class OldMathFunction {
    /* Terms */
    private ArrayList<Term> termArray = new ArrayList<Term>();
    private ArrayList<Term> powerTerms = new ArrayList<Term>();
    private ArrayList<Term> expTerms = new ArrayList<Term>();
    private ArrayList<Term> logTerms = new ArrayList<Term>();
    private ArrayList<Term> trigTerms = new ArrayList<Term>();
    private ArrayList<Term> absValTerms = new ArrayList<Term>();
    private ArrayList<Term> factorialTerms = new ArrayList<Term>();
    private ArrayList<Term> signumTerms = new ArrayList<Term>();

    /* Function Identification */
    private FunctionType functionType;

    public enum FunctionType {
        SEQUENCE,
        MULTIPLICATION
    }

    public OldMathFunction(FunctionType functionType, Term... terms) {
        /* Transfer terms from ArrayList to array */
        for (Term i : terms) {
            add(i);
        }
        organizeTerms();

        /* Identification */
        this.functionType = functionType;
    }

    public OldMathFunction(FunctionType functionType, ArrayList<Term> terms) {
        for (Term i : terms) {
            add(i);
        }
        organizeTerms();

        /* Identification */
        this.functionType = functionType;
    }

    public abstract String toString();

    public abstract String printConsole();

    public abstract void simplify();

    /**
     * Simplifies then organizes the terms so that they are in the correct order.
     */
    public final void organizeTerms() {
        simplify();

        /* Polynomial Term */
        powerTerms = SortingUtils.sortTerms(powerTerms, TermType.POWER);
        expTerms = SortingUtils.sortTerms(expTerms, TermType.EXPONENTIAL);
        logTerms = SortingUtils.sortTerms(logTerms, TermType.LOGARITHMIC);
        trigTerms = SortingUtils.sortTerms(trigTerms, TermType.TRIGONOMETRIC);

        termArray = new ArrayList<Term>() {
            {
                addAll(powerTerms);
                addAll(expTerms);
                addAll(logTerms);
                addAll(trigTerms);
                addAll(absValTerms);

                /* Niche */
                addAll(factorialTerms);
                addAll(signumTerms);
            }
        };
    }

    // public final void condense(TermType termType) {
    // for (Term i : getTermArray(termType)) {
    // i.condense();
    // }
    // }

    public abstract void merge(OldMathFunction function);

    public final void clearTermArrays() {
        powerTerms.clear();
        expTerms.clear();
        logTerms.clear();
        trigTerms.clear();
        absValTerms.clear();

        /* Niche Terms */
        factorialTerms.clear();
        signumTerms.clear();
    }

    public final void performAdditionOn(TermType termType) {
        AdditionAbstract addFunction = new AdditionAbstract();
        switch (termType) {
            case POWER:
                powerTerms = addFunction.performAddition(powerTerms);
                break;
            case EXPONENTIAL:
                expTerms = addFunction.performAddition(expTerms);
                break;
            case LOGARITHMIC:
                logTerms = addFunction.performAddition(logTerms);
                break;
            case TRIGONOMETRIC:
                trigTerms = addFunction.performAddition(trigTerms);
                break;
            case ABSOLUTE_VALUE:
                absValTerms = addFunction.performAddition(absValTerms);
                break;

            /* Niche */
            case FACTORIAL:
                factorialTerms = addFunction.performAddition(factorialTerms);
                break;
            case SIGNUM:
                signumTerms = addFunction.performAddition(signumTerms);
                break;
            default:
                break;
        }
    }

    public final void performMultiplicationOn(TermType termType) {
        MultiplicationAbstract multiplyFunction = new MultiplicationAbstract();
        switch (termType) {
            case POWER:
                powerTerms = multiplyFunction.performMultiplication(powerTerms);
                break;
            case EXPONENTIAL:
                expTerms = multiplyFunction.performMultiplication(expTerms);
                break;
            case LOGARITHMIC:
                logTerms = multiplyFunction.performMultiplication(logTerms);
                break;
            case TRIGONOMETRIC:
                trigTerms = multiplyFunction.performMultiplication(trigTerms);
                break;
            case ABSOLUTE_VALUE:
                absValTerms = multiplyFunction.performMultiplication(absValTerms);
                break;

            /* Niche */
            case FACTORIAL:
                factorialTerms = multiplyFunction.performMultiplication(factorialTerms);
                break;
            case SIGNUM:
                signumTerms = multiplyFunction.performMultiplication(signumTerms);
                break;
            default:
                break;
        }
    }

    //
    // Arithmetic Functions
    //

    /**
     * Solves the function with an input.
     * 
     * @param value
     * @return
     */
    public abstract double solve(double value);

    public abstract OldMathFunction negate();

    /**
     * 
     * @return
     */
    public abstract OldMathFunction derive();

    //
    // Get & Set Methods
    //
    public final ArrayList<Term> getTermArray() {
        return termArray;
    }

    /**
     * 
     * @param termType the desired type of Terms
     * @return the given termArray with TermType {@code termType}
     */
    public final ArrayList<Term> getTermArray(TermType termType) {
        switch (termType) {
            case POWER:
                return powerTerms;
            case EXPONENTIAL:
                return expTerms;
            case LOGARITHMIC:
                return logTerms;
            case TRIGONOMETRIC:
                return trigTerms;
            case ABSOLUTE_VALUE:
                return absValTerms;

            /* Niche */
            case FACTORIAL:
                return factorialTerms;
            case SIGNUM:
                return signumTerms;
            default:
                return null;
        }
    }

    public final int termArraySize() {
        return termArray.size();
    }

    public final void setTermArray(Term... terms) {
        clearTermArrays();
        for (Term i : terms) {
            add(i);
        }
    }

    public final void setTermArray(ArrayList<Term> terms) {
        clearTermArrays();
        for (Term i : terms) {
            add(i);
        }
    }

    public final void setTermArray(TermType termType, ArrayList<Term> terms) {
        switch (termType) {
            case POWER:
                powerTerms = terms;
                break;
            case EXPONENTIAL:
                expTerms = terms;
                break;
            case LOGARITHMIC:
                logTerms = terms;
                break;
            case TRIGONOMETRIC:
                trigTerms = terms;
                break;
            case ABSOLUTE_VALUE:
                absValTerms = terms;
                break;

            /* Niche */
            case FACTORIAL:
                factorialTerms = terms;
                break;
            case SIGNUM:
                signumTerms = terms;
                break;
            default:
                break;
        }
    }

    public final void addToTermArray(ArrayList<Term> terms) {
        for (Term i : terms) {
            add(i);
        }
        organizeTerms();
    }

    public final FunctionType getFunctionType() {
        return functionType;
    }

    /**
     * Augments the <b>polynomialTerms</b> ArrayList.
     * 
     * @param term
     */
    public final void addPowerTerm(Term term) {
        powerTerms.add(term);
    }

    public final void setPowerTerms(ArrayList<Term> terms) {
        powerTerms = terms;
    }

    public final void setPowerTerms(Term... terms) {
        powerTerms.clear();
        for (Term i : terms) {
            addPowerTerm(i);
        }
    }

    /**
     * Augments the <b>exponentialTerms</b> ArrayList.
     * 
     * @param term
     */
    public final void addExpTerm(Term term) {
        expTerms.add(term);
    }

    public final void setExpTerms(ArrayList<Term> terms) {
        expTerms = terms;
    }

    public final void setExpTerms(Term... terms) {
        expTerms.clear();
        for (Term i : terms) {
            addExpTerm(i);
        }
    }

    /**
     * Augments the <b>logTerms</b> ArrayList.
     * 
     * @param term
     */
    public final void addLogTerm(Term term) {
        logTerms.add(term);
    }

    public final void setLogTerms(Term... terms) {
        logTerms.clear();
        for (Term i : terms) {
            addLogTerm(i);
        }
    }

    public final void setLogTerms(ArrayList<Term> terms) {
        logTerms = terms;
    }

    /**
     * Augments the <b>trigTerms</b> ArrayList.
     * 
     * @param term
     */
    public final void addTrigTerm(Term term) {
        trigTerms.add(term);
    }

    public final void setTrigTerms(ArrayList<Term> terms) {
        trigTerms = terms;
    }

    public final void setTrigTerms(Term... terms) {
        trigTerms.clear();
        for (Term i : terms) {
            addTrigTerm(i);
        }
    }

    /**
     * Augments the <b>absValTerms</b> ArrayList.
     * 
     * @param term
     */
    public final void addAbsValTerm(Term term) {
        absValTerms.add(term);
    }

    public final void setAbsValTerms(ArrayList<Term> terms) {
        absValTerms = terms;
    }

    public final void setAbsValTerms(Term... terms) {
        absValTerms.clear();
        for (Term i : terms) {
            addAbsValTerm(i);
        }
    }

    /**
     * Augments the <b>factorialTerms</b> ArrayList.
     * 
     * @param term
     */
    public final void addFactorialTerm(Term term) {
        factorialTerms.add(term);
    }

    public final void setFactorialTerms(ArrayList<Term> terms) {
        factorialTerms = terms;
    }

    public final void setFactorialTerms(Term... terms) {
        factorialTerms.clear();
        for (Term i : terms) {
            addFactorialTerm(i);
        }
    }

    /**
     * Augments the <b>signumTerms</b> ArrayList.
     * 
     * @param term
     */
    public final void addSignumTerm(Term term) {
        signumTerms.add(term);
    }

    public final void setSignTerms(ArrayList<Term> terms) {
        signumTerms = terms;
    }

    public final void setSignTerms(Term... terms) {
        signumTerms.clear();
        for (Term i : terms) {
            addSignumTerm(i);
        }
    }

    /**
     * Augments the respective TermArray.
     */
    public final void add(Term term) {
        switch (term.getTermType()) {
            case POWER:
                addPowerTerm(term);
                break;
            case EXPONENTIAL:
                addExpTerm(term);
                break;
            case LOGARITHMIC:
                addLogTerm(term);
                break;
            case TRIGONOMETRIC:
                addTrigTerm(term);
                break;
            case ABSOLUTE_VALUE:
                addAbsValTerm(term);
                break;

            /* Niche */
            case FACTORIAL:
                addFactorialTerm(term);
                break;
            case SIGNUM:
                addSignumTerm(term);
                break;
            default:
                break;
        }
    }

    //
    // Boolean Methods
    //
    public final boolean isFunctionType(FunctionType functionType) {
        return this.functionType == functionType;
    }

    public final boolean equals(OldMathFunction other) {
        if (functionType != other.getFunctionType()) {
            return false;
        }

        ArrayList<Term> otherTermArray = other.getTermArray();
        if (termArray.size() != otherTermArray.size()) {
            return false;
        }

        for (int i = 0; i < termArray.size(); i++) {
            if (!termArray.get(i).equals(otherTermArray.get(i))) {
                // not equal
                return false;
            }
        }
        // all terms are the same at this point
        return true;
    }

    /**
     * Gets the boolean if the Term Array with TermType termType is empty.
     * 
     * @param termType
     * @return
     */
    public final boolean isTermArrayEmpty(TermType termType) {
        switch (termType) {
            case POWER:
                return powerTerms.size() == 0;
            case EXPONENTIAL:
                return expTerms.size() == 0;
            case LOGARITHMIC:
                return logTerms.size() == 0;
            case TRIGONOMETRIC:
                return trigTerms.size() == 0;
            case ABSOLUTE_VALUE:
                return absValTerms.size() == 0;

            /* Niche */
            case FACTORIAL:
                return factorialTerms.size() == 0;
            case SIGNUM:
                return signumTerms.size() == 0;
            default:
                return true;
        }
    }
}
