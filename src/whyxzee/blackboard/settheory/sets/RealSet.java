package whyxzee.blackboard.settheory.sets;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.settheory.SetAbstract;

public class RealSet extends SetAbstract {

    public RealSet() {
        super(Constants.Unicode.REAL_SET, "");
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
    public boolean inSet(double value) {
        return 

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'inSet'");
    }

}
