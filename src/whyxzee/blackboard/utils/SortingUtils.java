package whyxzee.blackboard.utils;

import java.util.ArrayList;

import whyxzee.blackboard.terms.*;
import whyxzee.blackboard.terms.Term.TermType;

public class SortingUtils {
    public static ArrayList<Term> sortTerms(ArrayList<Term> terms, TermType termType) {
        if (terms.size() == 0) {
            return new ArrayList<Term>();
        }

        for (int i = 1; i < terms.size(); i++) {
            int currentIndex = i;
            int sortedIndex = i - 1;

            switch (termType) {
                case POLYNOMIAL:
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
                    break;
                case EXPONENTIAL:
                    // sorted by e -> highest base -> lowest base
                    ExponentialTerm currentExp = (ExponentialTerm) terms.get(currentIndex);
                    ExponentialTerm sortedExp = (ExponentialTerm) terms.get(sortedIndex);

                    boolean currentIsE = currentExp.isBaseE();
                    boolean sortedIsE = sortedExp.isBaseE();

                    if (currentIsE) {
                        while (!sortedIsE) {
                            /* Swapping the two */
                            terms.set(currentIndex, sortedExp);
                            terms.set(sortedIndex, currentExp);

                            /* Moving back */
                            currentIndex--;
                            sortedIndex--;
                            if (sortedIndex < 0) {
                                break;
                            }
                            sortedExp = (ExponentialTerm) terms.get(sortedIndex);
                            sortedIsE = sortedExp.isBaseE();
                        }
                    } else {
                        while (currentExp.getBase() > sortedExp.getBase() && !sortedIsE) {
                            /* Swapping the two */
                            terms.set(currentIndex, sortedExp);
                            terms.set(sortedIndex, currentExp);

                            /* Moving back */
                            currentIndex--;
                            sortedIndex--;
                            if (sortedIndex < 0) {
                                break;
                            }
                            sortedExp = (ExponentialTerm) terms.get(sortedIndex);
                            sortedIsE = sortedExp.isBaseE();
                        }
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
    public static ArrayList<Term> condenseExpTerms(ArrayList<Term> terms) {
        ArrayList<ExponentialTerm> expTerms = new ArrayList<ExponentialTerm>();

        for (Term i : terms) {
            ExponentialTerm expTerm = (ExponentialTerm) i;
            expTerms.add(expTerm);
        }

        for (ExponentialTerm i : expTerms) {
            i.condense();
        }

        return new ArrayList<Term>(expTerms);
    }
}
