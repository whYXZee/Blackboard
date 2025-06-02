package whyxzee.blackboard.settheory;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.settheory.predicates.PredicateAbstract;

/**
 * A package for sets as defined by Set Theory.
 * 
 * <p>
 * This package was modeled after { values of <b>var</b> such that
 * <b>predicates</b>}
 */
public class SetBuilder extends SetAbstract {
    /* Variables */
    private SetAbstract[] domains = {};
    private ArrayList<PredicateAbstract> predicates = new ArrayList<PredicateAbstract>();

    public SetBuilder(String setName, String var) {
        super(setName, var);
    }

    public SetBuilder(String setName, String var, SetAbstract[] domains) {
        super(setName, var);
        this.domains = domains;
    }

    public SetBuilder(String setName, String var, SetAbstract[] domains, ArrayList<PredicateAbstract> predicates) {
        super(setName, var);
        this.domains = domains;
        this.predicates = predicates;
    }

    public SetBuilder(String setName, String var, ArrayList<PredicateAbstract> predicates) {
        super(setName, var);
        this.predicates = predicates;
    }

    @Override
    public final String toString() {
        String output = getSetName() + " = ";
        /* Null Set */
        if (predicates.size() == 0 && domains.length == 0) {
            return output + Constants.Unicode.NULL_SET;
        }

        output += "{" + getValuesOfVar();
        for (int i = 0; i < predicates.size(); i++) {
            PredicateAbstract indexPredicate = predicates.get(i);
            if (i == 0) {
                output += " " + Constants.Unicode.SET_SUCH_THAT + " ";
            } else {
                output += ", ";
            }
            output += indexPredicate;
        }
        output += "}";
        return output;
    }

    @Override
    public final String printConsole() {
        return "";
    }

    //
    // Get & Set Methods
    //
    private final String getValuesOfVar() {
        String output = getVar();
        if (domains.length == 0) {
            return output;
        }

        output += Constants.Unicode.ELEMENT_OF;
        for (int i = 0; i < domains.length; i++) {
            if (i != 0) {
                output += ",";
            }
            output += domains[i].getSetName();
        }
        return output;
    }

    //
    // Boolean Methods
    //
    public final boolean inDomain(double value) {
        for (SetAbstract i : domains) {
            if (!i.inSet(value)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public final boolean inSet(double value) {
        if (!inDomain(value)) {
            return false;
        }

        for (PredicateAbstract i : predicates) {
            if (!i.checkPredicate(value)) {
                return false;
            }
        }

        return true;
    }
}
