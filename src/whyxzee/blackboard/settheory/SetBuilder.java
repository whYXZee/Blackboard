package whyxzee.blackboard.settheory;

import java.util.ArrayList;
import java.util.Arrays;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.NumberAbstract;
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
        super(setName, var, SetType.SET_BUILDER);
    }

    public SetBuilder(String setName, String var, SetAbstract[] domains) {
        super(setName, var, SetType.SET_BUILDER);
        this.domains = domains;
    }

    public SetBuilder(String setName, String var, SetAbstract[] domains, ArrayList<PredicateAbstract> predicates) {
        super(setName, var, SetType.SET_BUILDER);
        this.domains = domains;
        this.predicates = predicates;
    }

    public SetBuilder(String setName, String var, ArrayList<PredicateAbstract> predicates) {
        super(setName, var, SetType.SET_BUILDER);
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

    public final void simplify() {
        // int[][] indexes = SetUtils.indexOfPredicates(predicates);

    }

    //
    // Arithmetic Methods
    //
    @Override
    public final SetAbstract union(SetAbstract other) {
        if (!other.getVar().equals(getVar())) {
            return null;
        }

        SetAbstract[] newDomains;

        switch (other.getSetType()) {
            case SET_LIST:
                return null;
            case INTERVAL:
                return null;
            case SET_BUILDER:
                SetBuilder builder = (SetBuilder) other;

                /* Domains */
                newDomains = getDomains();
                for (SetAbstract i : builder.getDomains()) {
                    newDomains = SetUtils.augmentDomains(newDomains, i);
                }
                Arrays.sort(newDomains);

                /* Predicates */
                ArrayList<PredicateAbstract> newPredicates = getPredicates();
                newPredicates.addAll(builder.getPredicates());

                return new SetBuilder("Union Set", getVar(), newDomains, SetUtils.sortPredicates(newPredicates));
            default:
                newDomains = SetUtils.augmentDomains(getDomains(), other);

                this.setDomains(newDomains);
                return this;
        }
    }

    //
    // Get & Set Methods
    //
    public final SetAbstract[] getDomains() {
        return domains;
    }

    public final void setDomains(SetAbstract[] domains) {
        this.domains = domains;
    }

    public final ArrayList<PredicateAbstract> getPredicates() {
        return predicates;
    }

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
    public final boolean inDomain(NumberAbstract number) {
        for (SetAbstract i : domains) {
            if (!i.inSet(number)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public final boolean inSet(NumberAbstract number) {
        if (!inDomain(number)) {
            return false;
        }

        for (PredicateAbstract i : predicates) {
            if (!i.checkPredicate(number)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public final boolean equals(SetAbstract other) {
        if (!similarSetType(other) || !getSetName().equals(other.getSetName())) {
            return false;
        }

        // TODO: compare domains and predicates
        return true;
    }
}
