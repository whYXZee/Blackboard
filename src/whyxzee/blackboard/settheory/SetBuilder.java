package whyxzee.blackboard.settheory;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.settheory.predicates.PredicateAbstract;
import whyxzee.blackboard.utils.SetUtils;

/**
 * A package for creating Sets under the Set Builder Notation.
 * 
 * The functionality of this class has been checked on {@code 6/3/2025}, and
 * nothing has changed since.
 */
public class SetBuilder extends SetAbstract {
    /* Variable */
    private String var;
    private ArrayList<AmbiguousList> domains;
    private ArrayList<PredicateAbstract> predicates;

    public SetBuilder(String setName, String var) {
        super(setName, SetType.BUILDER);
        this.var = var;
        this.domains = new ArrayList<AmbiguousList>();
        this.predicates = new ArrayList<PredicateAbstract>();

    }

    public SetBuilder(String setName, String var, ArrayList<AmbiguousList> domains,
            ArrayList<PredicateAbstract> predicates) {
        super(setName, SetType.BUILDER);
        this.var = var;
        this.domains = domains;
        this.predicates = predicates;

        if (domains == null) {
            this.domains = new ArrayList<AmbiguousList>();
        }
        if (predicates == null) {
            this.predicates = new ArrayList<PredicateAbstract>();
        }
    }

    @Override
    public final String toString() {
        String output = getSetName() + " = ";
        if (domains.size() == 0 && predicates.size() == 0) {
            return output + Constants.Unicode.NULL_SET;
        }

        output += "{" + var + getVarDomains();
        for (int i = 0; i < predicates.size(); i++) {
            if (i != 0) {
                output += ",";
            } else {
                output += Constants.Unicode.SET_SUCH_THAT;
            }
            output += predicates.get(i);
        }
        output += "}";

        return output;
    }

    @Override
    public String printConsole() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printConsole'");
    }

    @Override
    public IntervalSet toInterval() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toInterval'");
    }

    @Override
    public DefinedList toDefinedList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toDefinedList'");
    }

    @Override
    public SetBuilder toBuilder() {
        return this;
    }

    //
    // Get & Set Methods
    //
    private final String getVarDomains() {
        if (domains.size() == 0) {
            return "";
        }

        String output = Constants.Unicode.ELEMENT_OF + domains.get(0).toString();
        for (int i = 1; i < domains.size(); i++) {
            output += "," + domains.get(i);
        }
        return output;
    }

    public final ArrayList<AmbiguousList> getDomains() {
        return domains;
    }

    public final void setDomains(ArrayList<AmbiguousList> domains) {
        this.domains = domains;
    }

    public final void unionDomain(AmbiguousList addend) {
        if (!inDomains(addend)) {
            domains.add(addend);
            setDomains(SetUtils.unionDomains(domains));
        }
    }

    public final ArrayList<PredicateAbstract> getPredicates() {
        return predicates;
    }

    //
    // Arithmetic Methods
    //
    @Override
    public SetAbstract union(SetAbstract other) {
        switch (other.getType()) {
            case AMBIGUOUS_LIST:
                break;
            case BUILDER:
                break;
            case DEFINED_LIST:
                break;
            case INTERVAL:
                break;
            case NULL:
                return this;
            case SOLUTION:
                break;
            default:
                break;

        }
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'union'");
    }

    @Override
    public SetAbstract disjunction(SetAbstract other) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disjoint'");
    }

    @Override
    public SetAbstract complement(SetAbstract universe) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'complement'");
    }

    //
    // Boolean Methods
    //
    @Override
    public boolean inSet(NumberAbstract number) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'inSet'");
    }

    @Override
    public boolean equals(SetAbstract other) {
        throw new UnsupportedOperationException();
    }

    public final boolean inDomains(AmbiguousList domain) {
        for (AmbiguousList i : domains) {
            if (i.equals(domain)) {
                return true;
            }
        }
        return false;
    }
}
