package whyxzee.blackboard.math.pure.equations;

import java.util.ArrayList;

import whyxzee.blackboard.math.pure.numbers.BNumber;
import whyxzee.blackboard.math.pure.terms.PowerTerm;
import whyxzee.blackboard.math.pure.terms.Term;
import whyxzee.blackboard.math.pure.terms.Term.TermType;

public class SortUtils {
    public static final ArrayList<Term> sortTerms(ArrayList<Term> terms, TermType termType) {
        if (terms == null || terms.size() == 0) {
            return new ArrayList<Term>();
        }

        switch (termType) {
            case POWER:
                return sortPowers(terms);
            default:
                return new ArrayList<Term>();
        }
    }

    ///
    /// Abstracting the Problem
    ///
    private static final ArrayList<Term> sortPowers(ArrayList<Term> terms) {
        for (int i = 0; i < terms.size(); i++) {
            int currentIndex = i;
            int sortedIndex = i - 1;

            Term currentTerm = terms.get(currentIndex);
            Term sortedTerm = terms.get(sortedIndex);

            // sorted by highest power -> lowest power
            while (comparePolys((PowerTerm) currentTerm, (PowerTerm) sortedTerm)) {
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
        return terms;
    }

    /**
     * Compares two polynomial terms based on highest power -> lowest power then
     * lowest ASCII value -> highest ASCII value
     * 
     * @param currentTerm
     * @param sortedTerm
     * @return
     */
    private static final boolean comparePolys(PowerTerm currentTerm, PowerTerm sortedTerm) {
        // currentPower > sortedPower
        BNumber currentPower = currentTerm.getPower();
        BNumber sortedPower = sortedTerm.getPower();
        if (currentPower.equals(sortedPower)) {
            // TODO: sort by ascii
        }

        return currentPower.greaterThan(sortedPower);
    }
}
