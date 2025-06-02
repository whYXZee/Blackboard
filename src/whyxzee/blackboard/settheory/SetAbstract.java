package whyxzee.blackboard.settheory;

import whyxzee.blackboard.numbers.NumberAbstract;

public abstract class SetAbstract {
    private String setName;
    private String var;
    private SetType setType;

    public enum SetType {
        SET_BUILDER,
        SET_LIST,
        COMMON
    }

    public SetAbstract(String setName, String var, SetType setType) {
        this.setName = setName;
        this.var = var;
        this.setType = setType;
    }

    public abstract String printConsole();

    //
    // Arithmetic Methods
    //

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

    public final SetType getSetType() {
        return setType;
    }

    public final void setSetType(SetType setType) {
        this.setType = setType;
    }

    //
    // Boolean Methods
    //
    public final boolean isSetType(SetType setType) {
        return this.setType == setType;
    }

    public abstract boolean inSet(NumberAbstract number);
}
