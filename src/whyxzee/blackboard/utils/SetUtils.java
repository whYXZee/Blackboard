package whyxzee.blackboard.utils;

import java.util.ArrayList;

import whyxzee.blackboard.settheory.AmbiguousList;

public class SetUtils {
    //
    //
    //
    public static final ArrayList<AmbiguousList> unionDomains(ArrayList<AmbiguousList> domains) {
        if (domains.size() == 0) {
            return domains;
        }

        ArrayList<AmbiguousList> output = new ArrayList<AmbiguousList>();
        int iGreatestOrder = 0; // index of greatest order
        for (int i = 1; i < domains.size(); i++) {
            int indexOrder = domains.get(i).getOrder();
            if (iGreatestOrder < indexOrder) {
                iGreatestOrder = i;
            }
        }

        output.add(domains.get(iGreatestOrder));
        return output;
    }

}
