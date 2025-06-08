package whyxzee.blackboard.utils;

import java.util.ArrayList;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.settheory.AmbiguousList;
import whyxzee.blackboard.settheory.DefinedList;
import whyxzee.blackboard.settheory.IntervalSet;
import whyxzee.blackboard.settheory.SetAbstract;
import whyxzee.blackboard.settheory.SetBuilder;
import whyxzee.blackboard.settheory.SetAbstract.SetType;
import whyxzee.blackboard.settheory.predicates.ElementOf;
import whyxzee.blackboard.settheory.predicates.EqualPredicate;
import whyxzee.blackboard.settheory.predicates.OrPredicate;
import whyxzee.blackboard.settheory.predicates.PredicateAbstract;
import whyxzee.blackboard.settheory.predicates.RangePredicate;
import whyxzee.blackboard.settheory.predicates.PredicateAbstract.PredicateType;

public class SetUtils {
    public static final String unionString(SetAbstract... sets) {
        if (sets.length == 0) {
            return "null";
        }
        String output = sets[0].getSetName();
        for (int i = 1; i < sets.length; i++) {
            output += Constants.Unicode.UNION + sets[i].getSetName();
        }
        return output;
    }

    /**
     * The functionality of this class has been checked on {@code 6/5/2025}, and the
     * following needs to be checked:
     * <ul>
     * <li>union of OrPredicates with ElementOf in unionDomains()
     */
    public static final class UnionHelper {
        /**
         * Performs a union on {@link whyxzee.blackboard.settheory.predicates.ElementOf}
         * predicates.
         * 
         * @param predicates
         * @return
         */
        public static final ArrayList<PredicateAbstract> unionDomains(ArrayList<PredicateAbstract> predicates) {
            // TODO: implement "not element of" type of predicates
            // TODO: abstract into OrPrediate, AndPredicate, and SetBuilder
            if (predicates.size() == 0) {
                return predicates;
            }

            ArrayList<AmbiguousList> domains = new ArrayList<AmbiguousList>();
            ArrayList<PredicateAbstract> output = new ArrayList<PredicateAbstract>();
            for (PredicateAbstract i : predicates) {
                if (!i.isType(PredicateType.ELEMENT_OF) && !i.isType(PredicateType.OR)) {
                    break;
                }

                if (i.isType(PredicateType.OR)) {
                    // has not been tested
                    for (PredicateAbstract j : ((OrPredicate) i).getPredicates()) {
                        if (j.isType(PredicateType.ELEMENT_OF)) {
                            SetAbstract jDomain = ((ElementOf) j).getDomain();
                            if (jDomain.isType(SetType.AMBIGUOUS_LIST)) {
                                domains.add((AmbiguousList) jDomain);
                            } else {
                                output.add(j);
                            }
                        }
                    }
                } else {
                    SetAbstract iDomain = ((ElementOf) i).getDomain();
                    if (iDomain.isType(SetType.AMBIGUOUS_LIST)) {
                        domains.add((AmbiguousList) iDomain);
                    } else {
                        output.add(i);
                    }
                }
            }

            if (domains.size() == 0) {
                return output;
            }

            int iGreatestOrder = 0; // index of greatest order
            int greatestOrder = domains.get(0).getDomain().getOrder();
            for (int i = 1; i < domains.size(); i++) {
                int indexOrder = domains.get(i).getDomain().getOrder();
                if (greatestOrder < indexOrder) {
                    iGreatestOrder = i;
                    greatestOrder = domains.get(i).getDomain().getOrder();
                }
            }

            output.add(domains.get(iGreatestOrder).toPredicate());
            return output;
        }

        /**
         * Returns an array, where the 0th index is the number of domains needed, while
         * the 1st index is the index of the number that does not fit the domain.
         * 
         * @param numbers
         * @param domain
         * @return 0 if the domain covers all the inputted numbers
         *         <li>1 if the domain covers all the inputted numbers except one
         *         <li>2+ if more than one domain is needed
         */
        public static final int[] needsTwoDomains(ArrayList<NumberAbstract> numbers, AmbiguousList domain) {
            int[] output = { 0, 0 };

            for (int i = 0; i < numbers.size(); i++) {
                NumberAbstract iNum = numbers.get(i);
                if (!domain.inSet(iNum)) {
                    System.out.println(iNum);
                    output[0]++;
                    output[1] = i;
                }
                if (output[0] == 2) {
                    return output;
                }
            }

            return output;
        }

        public static final int[] needsTwoDomains(ArrayList<NumberAbstract> numbers, SetBuilder builder) {
            int[] output = { 0, 0 };
            for (int i = 0; i < numbers.size(); i++) {
                NumberAbstract iNum = numbers.get(i);
                if (!builder.inSet(iNum)) {
                    output[0]++;
                    output[1] = i;
                }
                if (output[0] == 2) {
                    return output;
                }
            }

            return output;
        }

        public static final ArrayList<PredicateAbstract> numbersInInterval(DefinedList list, IntervalSet interval) {
            ArrayList<NumberAbstract> numbers = list.getNumbers();
            ArrayList<NumberAbstract> extraNumbers = new ArrayList<NumberAbstract>();
            for (NumberAbstract i : numbers) {
                if (!interval.inSet(i)) {
                    extraNumbers.add(i);
                }
            }

            ArrayList<PredicateAbstract> predicates = new ArrayList<PredicateAbstract>();
            predicates.add(new RangePredicate(Constants.NumberConstants.DEFAULT_VAR, interval));
            if (extraNumbers.size() == 0) {
                return predicates;
            } else if (extraNumbers.size() == 1) {
                predicates.add(new EqualPredicate(Constants.NumberConstants.DEFAULT_VAR, extraNumbers.get(0)));
            } else {
                predicates.add(new ElementOf(Constants.NumberConstants.DEFAULT_VAR, list));
            }

            return new OrPredicate(predicates).toPredicateList();
        }
    }

    //
    // Boolean Methods
    //
    /**
     * Checks two intervals to see if they make up a split region. The following
     * criteria must be met for a {@code true} return value:
     * <ul>
     * <li>both <b>regionOne's</b> upper bound and <b>regionTwo's</b> lower bound
     * are not open.
     * <li><b>regionOne's</b> upper bound and <b>regionTwo's</b> lower bound must be
     * the same number.
     * </ul>
     */
    public static final boolean isRegionSplit(IntervalSet regionOne, IntervalSet regionTwo) {
        if (regionTwo.isLowOpen() && regionOne.isUpOpen()) {
            return false;
        }

        return regionTwo.getLowBound().equals(regionOne.getUpBound());
    }

    /**
     * Checks two intervals to see if they make up a split region. The following
     * criteria must be met for a {@code true} return value:
     * <ul>
     * <li>both <b>regionOne's</b> upper bound and <b>regionTwo's</b> lower bound
     * are not open
     * <li><b>regionOne's</b> upper bound and <b>regionTwo's</b> lower bound must be
     * the same number.
     * </ul>
     */
    public static final boolean isRegionSplit(RangePredicate regionOne, RangePredicate regionTwo) {
        if (regionTwo.isLowOpen() && regionOne.isUpOpen()) {
            return false;
        }

        return regionTwo.getLowBound().equals(regionOne.getUpBound());
    }
}
