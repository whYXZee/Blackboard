package whyxzee.blackboard.settheory.arithmetic;

import java.util.ArrayList;

import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.settheory.DefinedList;
import whyxzee.blackboard.settheory.NullSet;
import whyxzee.blackboard.settheory.SetAbstract;
import whyxzee.blackboard.utils.SetUtils;

public class UnionDefinedLists {
    /* Variables */
    private static ArrayList<NumberAbstract> numbers = new ArrayList<NumberAbstract>();

    public static final SetAbstract performUnion(DefinedList... lists) {
        if (lists.length == 0) {
            return new NullSet("Null");
        } else if (lists.length == 1) {
            return lists[0];
        }

        if (numbers.size() != 0) {
            numbers.clear();
        }

        for (DefinedList list : lists) {
            for (NumberAbstract i : list.getNumbers()) {
                if (!contains(i)) {
                    numbers.add(i);
                }
            }
        }

        return new DefinedList(SetUtils.unionString(lists), numbers);
    }

    //
    // Boolean Methods
    //
    private static final boolean contains(NumberAbstract number) {
        for (NumberAbstract i : numbers) {
            if (i.equals(number)) {
                return true;
            }
        }

        return false;
    }
}
