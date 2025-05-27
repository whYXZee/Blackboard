package whyxzee.blackboard.utils;

import java.util.ArrayList;

import whyxzee.blackboard.terms.*;
import whyxzee.blackboard.terms.Term.TermType;
import whyxzee.blackboard.terms.TrigTerm.TrigType;

public class SortingUtils {

    public static ArrayList<Term> sortTerms(ArrayList<Term> terms, TermType termType) {
        if (terms.size() == 0) {
            return terms;
        }

        for (int i = 1; i < terms.size(); i++) {
            int currentIndex = i;
            int sortedIndex = i - 1;

            Term currentTerm = terms.get(currentIndex);
            Term sortedTerm = terms.get(sortedIndex);

            boolean currentIsE, sortedIsE;

            switch (termType) {
                case POLYNOMIAL:
                    // sorted by highest power -> lowest power
                    while (comparePolys((PolynomialTerm) currentTerm, (PolynomialTerm) sortedTerm)) {
                        /* Swapping the two */
                        terms.set(currentIndex, sortedTerm);
                        terms.set(sortedIndex, currentTerm);

                        /* Moving back */
                        currentIndex--;
                        sortedIndex--;
                        if (sortedIndex < 0) {
                            break;
                        }
                        sortedTerm = terms.get(sortedIndex);
                    }
                    break;

                case EXPONENTIAL:
                    // sorted by e -> highest base -> lowest base
                    currentIsE = ((ExponentialTerm) currentTerm).isBaseE();
                    sortedIsE = ((ExponentialTerm) sortedTerm).isBaseE();

                    if (currentIsE) {
                        while (!sortedIsE) {
                            /* Swapping the two */
                            terms.set(currentIndex, sortedTerm);
                            terms.set(sortedIndex, currentTerm);

                            /* Moving back */
                            currentIndex--;
                            sortedIndex--;
                            if (sortedIndex < 0) {
                                break;
                            }
                            sortedTerm = terms.get(sortedIndex);
                            sortedIsE = ((ExponentialTerm) sortedTerm).isBaseE();
                        }
                    } else {
                        while (compareExps((ExponentialTerm) currentTerm, (ExponentialTerm) sortedTerm)) {
                            /* Swapping the two */
                            terms.set(currentIndex, sortedTerm);
                            terms.set(sortedIndex, currentTerm);

                            /* Moving back */
                            currentIndex--;
                            sortedIndex--;
                            if (sortedIndex < 0) {
                                break;
                            }
                            sortedTerm = terms.get(sortedIndex);
                        }
                    }
                    break;
                case LOGARITHMIC:
                    // sorted by e -> highest base -> lowest base
                    currentIsE = ((LogarithmicTerm) currentTerm).isBaseE();
                    sortedIsE = ((LogarithmicTerm) sortedTerm).isBaseE();

                    if (currentIsE) {
                        while (!sortedIsE) {
                            /* Swapping the two */
                            terms.set(currentIndex, sortedTerm);
                            terms.set(sortedIndex, currentTerm);

                            /* Moving back */
                            currentIndex--;
                            sortedIndex--;
                            if (sortedIndex < 0) {
                                break;
                            }
                            sortedTerm = terms.get(sortedIndex);
                            sortedIsE = ((LogarithmicTerm) sortedTerm).isBaseE();
                        }
                    } else {
                        while (compareLogs((LogarithmicTerm) currentTerm, (LogarithmicTerm) sortedTerm)) {
                            /* Swapping the two */
                            terms.set(currentIndex, sortedTerm);
                            terms.set(sortedIndex, currentTerm);

                            /* Moving back */
                            currentIndex--;
                            sortedIndex--;
                            if (sortedIndex < 0) {
                                break;
                            }
                            sortedTerm = terms.get(sortedIndex);
                        }
                    }
                    break;

                case TRIGONOMETRIC:
                    // sorted by sin -> cos -> tan -> csc -> sec -> cot -> respective inverses
                    while (compareTrig((TrigTerm) currentTerm, (TrigTerm) sortedTerm)) {
                        /* Swapping the two */
                        terms.set(currentIndex, sortedTerm);
                        terms.set(sortedIndex, currentTerm);

                        /* Moving back */
                        currentIndex--;
                        sortedIndex--;
                        if (sortedIndex < 0) {
                            break;
                        }
                        sortedTerm = terms.get(sortedIndex);
                    }
                    break;

                default:
                    break;
            }

        }

        return terms;
    }

    /**
     * An insertion sort for terms in a Taylor Series. Sorted from lowest power ->
     * greatest power
     * 
     * @param terms
     * @return
     */
    public static ArrayList<Term> sortTaylor(ArrayList<Term> terms) {
        if (terms.size() == 0) {
            return new ArrayList<Term>();
        }

        for (int i = 1; i < terms.size(); i++) {
            int currentIndex = i;
            int sortedIndex = i - 1;

            // sorted by highest power -> lowest power
            PolynomialTerm currentPoly = (PolynomialTerm) terms.get(currentIndex);
            PolynomialTerm sortedPoly = (PolynomialTerm) terms.get(sortedIndex);

            while (currentPoly.getPower() > sortedPoly.getPower()) {
                /* Swapping the two */
                terms.set(currentIndex, sortedPoly);
                terms.set(sortedIndex, currentPoly);

                /* Moving back */
                currentIndex--;
                sortedIndex--;
                if (sortedIndex < 0) {
                    break;
                }
                sortedPoly = (PolynomialTerm) terms.get(sortedIndex);
            }

        }

        return terms;
    }

    //
    // TermType Specific
    //
    public static final boolean comparePolys(PolynomialTerm currentTerm, PolynomialTerm sortedTerm) {
        return currentTerm.getPower() > sortedTerm.getPower();
    }

    public static final boolean compareExps(ExponentialTerm currentTerm, ExponentialTerm sortedTerm) {
        return (currentTerm.getBase() > sortedTerm.getBase()) && !sortedTerm.isBaseE();
    }

    public static final boolean compareLogs(LogarithmicTerm currentTerm, LogarithmicTerm sortedTerm) {
        return (currentTerm.getBase() > sortedTerm.getBase()) && !sortedTerm.isBaseE();
    }

    public static boolean compareTrig(TrigTerm currentTerm, TrigTerm sortedTerm) {
        return trigTypeToInt(currentTerm.getTrigType()) > trigTypeToInt(sortedTerm.getTrigType());
    }

    public static int trigTypeToInt(TrigType trigType) {
        switch (trigType) {
            case SINE:
                return 11;
            case COSINE:
                return 10;
            case TANGENT:
                return 9;
            case COSECANT:
                return 8;
            case SECANT:
                return 7;
            case COTANGENT:
                return 6;

            case ARC_SINE:
                return 5;
            case ARC_COSINE:
                return 4;
            case ARC_TANGENT:
                return 3;
            case ARC_COSECANT:
                return 2;
            case ARC_SECANT:
                return 1;
            case ARC_COTANGENT:
                return 0;
            default:
                return -1;
        }
    }
}
