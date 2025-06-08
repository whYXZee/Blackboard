package whyxzee.blackboard.settheory;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.settheory.arithmetic.UnionPredicate;
import whyxzee.blackboard.settheory.predicates.AndPredicate;
import whyxzee.blackboard.settheory.predicates.ElementOf;
import whyxzee.blackboard.settheory.predicates.EqualPredicate;
import whyxzee.blackboard.settheory.predicates.OrPredicate;
import whyxzee.blackboard.settheory.predicates.PredicateAbstract;
import whyxzee.blackboard.settheory.predicates.PredicateAbstract.PredicateType;
import whyxzee.blackboard.settheory.predicates.RangePredicate;
import whyxzee.blackboard.utils.SetUtils;

/**
 * A package for creating Sets under the Set Builder Notation.
 * 
 * The functionality of this class has been checked on {@code 6/5/2025}, and
 * the following has changed since:
 * <ul>
 * <li>union()
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

        output += "{" + var + " " + Constants.Unicode.SET_SUCH_THAT + " " + predicates.get(0);
        for (int i = 1; i < predicates.size(); i++) {
            output += ", " + predicates.get(i);
        }
        output += "}";
        return output;
    }

    @Override
    public String printConsole() {
        String output = getSetName() + " = ";
        if (predicates.size() == 0) {
            return output + Constants.Unicode.NULL_SET;
        }

        output += "{" + var + " " + Constants.Unicode.SET_SUCH_THAT + " " + predicates.get(0).printConsole();
        for (int i = 1; i < predicates.size(); i++) {
            output += ", " + predicates.get(i).printConsole();
        }
        output += "}";
        return output;
    }

    @Override
    public IntervalSet toInterval() {
        // doable if it has a range predicate
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
            if (predicates.size() == 1) {
                predicates = UnionPredicate.performUnion(predicates.get(0), addend);
            } else {
                AndPredicate and = new AndPredicate(predicates);
                predicates = UnionPredicate.performUnion(and, addend);
            }
        }
    }

    public final void unionPredicates(ArrayList<PredicateAbstract> addends) {
        System.out.println(addends);
        if (addends.size() == 1) {
            unionPredicate(addends.get(0));
        } else {
            AndPredicate and = new AndPredicate(predicates);
            addends.add(and);
            predicates = UnionPredicate.performUnion(addends);
        }
    }

    public final String getVar() {
        return var;
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
        int[] domainsNeeded;
        switch (other.getAmbiguousList()) {
            case AMBIGUOUS_LIST:
                unionPredicate(((AmbiguousList) other).toPredicate());
                return this;

            case BUILDER:
                unionPredicates(((SetBuilder) other).getPredicates());
                return this;

            case DEFINED_LIST:
                DefinedList dList = (DefinedList) other;
                domainsNeeded = SetUtils.UnionHelper.needsTwoDomains(dList.getNumbers(), this);
                /* Extra Predicates */
                if (domainsNeeded[0] == 0) {
                    return this;

                } else if (domainsNeeded[0] == 1) {
                    unionPredicate(new EqualPredicate(var, dList.getNumbers().get(domainsNeeded[1])));
                } else {
                    unionPredicate(new ElementOf(var, dList));
                }
                return this;

            case INTERVAL:
                unionPredicate(new RangePredicate(var, (IntervalSet) other));
                return this;

            case NULL:
                return this;
            default:
                return null;

        }
    }

    @Override
    public SetAbstract intersection(SetAbstract other) {
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
        for (PredicateAbstract i : predicates) {
            if (!i.checkPredicate(number)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public final boolean isSuperset(SetAbstract other) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(SetAbstract other) {
        throw new UnsupportedOperationException();
    }

    public final boolean inPredicates(PredicateAbstract predicate) {
        for (PredicateAbstract i : predicates) {
            if (i.isType(PredicateType.OR)) {
                OrPredicate or = (OrPredicate) i;
                if (or.contains(i)) {
                    return true;
                }
            } else if (i.equals(predicate)) {
                return true;
            }
        }
        return false;
    }
}
