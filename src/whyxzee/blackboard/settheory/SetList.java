package whyxzee.blackboard.settheory;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;

public class SetList extends SetAbstract {
    /* Variables */
    private ArrayList<Double> values;

    public SetList(String setName, String var, ArrayList<Double> values) {
        super(setName, var);
        this.values = values;
    }

    public SetList(String setName, String var, double... values) {
        super(setName, var);
        this.values = new ArrayList<Double>();
        for (double i : values) {
            this.values.add(i);
        }
    }

    public final String toString() {
        String output = getSetName() + " = ";
        if (values.size() == 0) {
            return output + Constants.Unicode.NULL_SET;
        }

        /* First Term */
        output += "{" + values.get(0);
        if (values.get(0) == Double.NEGATIVE_INFINITY) {
            output += ", ...";
        }
        for (int i = 1; i < values.size(); i++) {
            double indexVal = values.get(i);
            if (indexVal == Double.POSITIVE_INFINITY) {
                output += ", ... ";
            } else {
                output += ", ";
            }
            output += indexVal;
        }

        return output + "}";
    }

    @Override
    public String printConsole() {
        return toString();
    }

    //
    // Boolean Methods
    //
    @Override
    public boolean inSet(double value) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'inSet'");
    }
}
