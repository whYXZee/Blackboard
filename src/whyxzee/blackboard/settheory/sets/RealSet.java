package whyxzee.blackboard.settheory.sets;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.settheory.SetAbstract;

public class RealSet extends SetAbstract {

    public RealSet() {
        super(Constants.Unicode.REAL_SET, "", SetType.COMMON);
    }

    @Override
    public final String toString() {
        return getSetName();
    }

    @Override
    public String printConsole() {
        return toString();
    }

    @Override
    public boolean inSet(NumberAbstract number) {
        return !number.isImaginary();
    }

}
