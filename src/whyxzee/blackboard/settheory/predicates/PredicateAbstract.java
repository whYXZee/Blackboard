package whyxzee.blackboard.settheory.predicates;

/**
 * A package for defining which numbers are in a set.
 */
public abstract class PredicateAbstract {
    /* Variables */
    private String var;
    private PredicateType type;

    public enum PredicateType {
        INEQUALITY,
        RANGE,
        EVEN_ODD
    }

    public PredicateAbstract(String var, PredicateType type) {
        this.var = var;
        this.type = type;
    }

    public abstract String printConsole();

    //
    // Get & Set Methods
    //
    public final PredicateType getType() {
        return type;
    }

    public final void getType(PredicateType type) {
        this.type = type;
    }

    public final String getVar() {
        return var;
    }

    public final void setVar(String var) {
        this.var = var;
    }

    //
    // Boolean Methods
    //
    public abstract boolean checkPredicate(double value);
}
