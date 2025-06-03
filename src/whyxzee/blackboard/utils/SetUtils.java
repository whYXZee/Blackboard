package whyxzee.blackboard.utils;

import java.util.ArrayList;

import whyxzee.blackboard.numbers.NumberAbstract;
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

    public static final ArrayList<NumberAbstract> unionNum(ArrayList<NumberAbstract> numbers, AmbiguousList domain) {
        ArrayList<NumberAbstract> output = new ArrayList<NumberAbstract>();
        if (numbers.size() == 0) {
            return numbers;
        }

        for (NumberAbstract i : numbers) {
            if (!domain.inSet(i)) {
                output.add(i);
            }
        }

        return output;
    }

    /**
     * 
     * @param numbers
     * @param domain
     * @return 0 if the domain covers all the inputted numbers
     *         <li>1 if the domain covers all the inputted numbers except one
     *         <li>2+ if more than one domain is needed
     */
    public static final int needsTwoDomains(ArrayList<NumberAbstract> numbers, AmbiguousList domain) {
        if (numbers.size() == 0) {
            return 0;
        }

        int output = 0;
        for (NumberAbstract i : numbers) {
            if (!domain.inSet(i)) {
                output++;
            }
            if (output == 2) {
                return 2;
            }
        }

        return output;
    }

}
