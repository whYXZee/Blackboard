package whyxzee.blackboard.settheory.predicates;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.settheory.IntervalSet;
import whyxzee.blackboard.settheory.SetAbstract;

public class ElementOf extends PredicateAbstract {
    /* Variables */
    private SetAbstract domain;
    private boolean notElementOf;

    public ElementOf(String var, SetAbstract domain) {
        super(var, PredicateType.ELEMENT_OF);
        this.domain = domain;
        this.notElementOf = false;
    }

    public ElementOf(String var, SetAbstract domain, boolean notElementOf) {
        super(var, PredicateType.ELEMENT_OF);
        this.domain = domain;
        this.notElementOf = notElementOf;
    }

    @Override
    public final String toString() {
        return getVar() + (notElementOf ? Constants.Unicode.NOT_ELEMENT_OF : Constants.Unicode.ELEMENT_OF)
                + domain.getSetName();
    }

    @Override
    public String printConsole() {
        return toString();
    }

    @Override
    public IntervalSet toInterval() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toInterval'");
    }

    //
    // Get & Set Methods
    //
    public final SetAbstract getDomain() {
        return domain;
    }

    public final boolean isNotElementOf() {
        return notElementOf;
    }

    //
    // Boolean Methods
    //
    @Override
    public boolean checkPredicate(NumberAbstract number) {
        return notElementOf ? !domain.inSet(number) : domain.inSet(number);
    }

    @Override
    public final boolean equals(PredicateAbstract other) {
        if (!other.isType(PredicateType.ELEMENT_OF)) {
            return false;
        }

        ElementOf e = (ElementOf) other;
        return (getVar().equals(e.getVar()))
                && (notElementOf == e.isNotElementOf())
                && (domain.equals(e.getDomain()));
    }

}
