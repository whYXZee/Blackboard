package whyxzee.blackboard.settheory;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.settheory.arithmetic.UnionPredicate;
import whyxzee.blackboard.settheory.predicates.PredicateAbstract;

/**
 * A package for creating Sets under the Set Builder Notation.
 * 
 * The functionality of this class has been checked on {@code 6/5/2025}, and
 * nothing has changed since.
 */
public class SetBuilder extends SetAbstract {
    /* Variable */
    private String var;
    private ArrayList<PredicateAbstract> predicates;

    public SetBuilder(String setName, String var, ArrayList<PredicateAbstract> predicates) {
        super(setName, SetType.BUILDER);
        this.var = var;
        this.predicates = predicates;

        if (predicates == null) {
            this.predicates = new ArrayList<PredicateAbstract>();
        }
    }

    @Override
    public final String toString() {
        String output = getSetName() + " = ";
        if (predicates.size() == 0) {
            return output + Constants.Unicode.NULL_SET;
        }

        output += "{" + var;
        for (int i = 0; i < predicates.size(); i++) {
            if (i != 0) {
                output += ", ";
            } else {
                output += " " + Constants.Unicode.SET_SUCH_THAT + " ";
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
    public final ArrayList<PredicateAbstract> getPredicates() {
        return predicates;
    }

    public final void unionPredicate(PredicateAbstract addend) {
        if (!inPredicates(addend)) {
            predicates.add(addend);
            UnionPredicate unionPredicate = new UnionPredicate();
            predicates = unionPredicate.performUnion(predicates);
        }
    }

    public final void unionPredicates(ArrayList<PredicateAbstract> addends) {
        for (PredicateAbstract i : addends) {
            if (!inPredicates(i)) {
                predicates.add(i);
            }
        }
        UnionPredicate unionPredicate = new UnionPredicate();
        predicates = unionPredicate.performUnion(predicates);
    }

    //
    // Arithmetic Methods
    //
    @Override
    public final NumberAbstract cardinality() {
        throw new UnsupportedOperationException();
    }

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

    public final boolean inPredicates(PredicateAbstract predicate) {
        for (PredicateAbstract i : predicates) {
            if (i.equals(predicate)) {
                return true;
            }
        }
        return false;
    }
}
