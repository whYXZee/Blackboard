package whyxzee.blackboard.math.pure.equations;

import java.util.ArrayList;

import whyxzee.blackboard.math.pure.terms.Term;

public class EquationUtils {
    public static final ArrayList<Term> deepCopyTerms(ArrayList<Term> original) {
        ArrayList<Term> copy = new ArrayList<>(original.size());
        for (Term i : original) {
            copy.add(i.clone());
        }
        return copy;
    }

}
