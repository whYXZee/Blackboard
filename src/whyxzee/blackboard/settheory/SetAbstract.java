package whyxzee.blackboard.settheory;

public abstract class SetAbstract {
    private String setName;
    private String var;

    public SetAbstract(String setName, String var) {
        this.setName = setName;
        this.var = var;
    }

    public abstract String printConsole();

    //
    // Get & Set Methods
    //
    public final String getSetName() {
        return setName;
    }

    public final void setSetName(String setName) {
        this.setName = setName;
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
    public abstract boolean inSet(double value);
}
