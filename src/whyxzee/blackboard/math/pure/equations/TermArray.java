package whyxzee.blackboard.math.pure.equations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

import whyxzee.blackboard.math.pure.equations.terms.PowerTerm;
import whyxzee.blackboard.math.pure.numbers.ComplexNum;

/**
 * <p>
 * The functionality of this class has been checked on <b>6/22/2025</b> and
 * nothing has changed since.
 */
public class TermArray {
    /* Variables */
    private ArrayList<PowerTerm> arr;

    // #region Constructors
    /**
     * A constructor for an empty TermArray.
     */
    public TermArray() {
        arr = new ArrayList<PowerTerm>();
    }

    /**
     * A constructor for a TermArray.
     * 
     * <p>
     * <em>A deep copy is not performed on <b>terms</b>, so changes made to the arr
     * will change the <b>terms</b> argument.
     * 
     * @param terms
     */
    public TermArray(ArrayList<PowerTerm> terms) {
        arr = terms;
    }

    /**
     * A constructor for a TermArray.
     * 
     * <p>
     * <em>A deep copy is not performed on <b>terms</b>, so changes made to the arr
     * will change the <b>terms</b> argument.
     * 
     * @param terms
     */
    public TermArray(PowerTerm... terms) {
        this.arr = new ArrayList<PowerTerm>();
        for (PowerTerm i : terms) {
            arr.add(i);
        }
    }
    // #endregion

    // #region String/Display
    @Override
    public final String toString() {
        return arr.toString();
    }
    // #endregion

    // #region Copying/Cloning
    /**
     * Performs a deep copy of <b>other's arr</b> and all of its terms and transfers
     * the data over to <b>this</b>.
     */
    public final void copy(TermArray other) {
        this.arr = other.clone().getArr();
    }

    @Override
    /**
     * Provides a deep copy of <b>arr</b> and all of the terms.
     */
    public final TermArray clone() {
        ArrayList<PowerTerm> copy = new ArrayList<PowerTerm>();
        for (PowerTerm i : arr) {
            copy.add(i.clone());
        }
        return new TermArray(copy);
    }
    // #endregion

    // #region Get/Set
    public final void add(PowerTerm addend) {
        arr.add(addend);
    }

    public final void addAll(Collection<? extends PowerTerm> c) {
        arr.addAll(c);
    }

    public final void addAll(TermArray c) {
        arr.addAll(c.getArr());
    }

    public final PowerTerm get(int index) {
        return arr.get(index);
    }

    public final ArrayList<PowerTerm> getArr() {
        return arr;
    }

    public final void setArr(ArrayList<PowerTerm> arr) {
        this.arr = arr;
    }

    public final void setArr(PowerTerm... arr) {
        this.arr = new ArrayList<PowerTerm>();
        for (PowerTerm i : arr) {
            this.arr.add(i);
        }
    }
    // #endregion

    // #region Size/Index
    /**
     * The number of elements in <b>arr</b>.
     * 
     * @return
     */
    public final int size() {
        return arr.size();
    }

    public final boolean isEmpty() {
        return arr.size() == 0;
    }

    public final int indexOf(PowerTerm term) {
        return arr.indexOf(term);
    }

    /**
     * 
     * @param term
     * @param criteria A reference method or a predicate.
     * @return
     */
    public final int indexOf(Predicate<PowerTerm> criteria) {
        for (int i = 0; i < size(); i++) {
            if (criteria.test(arr.get(i))) {
                return i;
            }
        }
        return -1;
    }
    // #endregion

    // #region Constant Terms
    /**
     * Returns the first constant in <b>arr</b>.
     * 
     * @return PowerTerm with a coefficient of 0 if there is no constant
     *         <li>the PowerTerm constant if there is one.
     */
    public final PowerTerm getConstant() {
        for (PowerTerm i : arr) {
            if (i.isConstant()) {
                return i;
            }
        }
        return new PowerTerm(0);
    }

    /**
     * Returns the first constant in <b>arr</b>, and removes it from the ArrayList.
     * 
     * @return PowerTerm with a coefficient of 0 if there is no constant
     *         <li>the PowerTerm constant if there is one.
     */
    public final PowerTerm getConstantAndRemove() {
        for (PowerTerm i : arr) {
            if (i.isConstant()) {
                arr.remove(i);
                return i;
            }
        }
        return new PowerTerm(0);
    }

    /**
     * Returns all of the constants in <b>arr</b>.
     * 
     * @return
     */
    public final ArrayList<PowerTerm> getConstants() {
        ArrayList<PowerTerm> constants = new ArrayList<PowerTerm>();

        for (PowerTerm i : arr) {
            if (i.isConstant()) {
                constants.add(i);
            }
        }

        return constants;
    }

    /**
     * Returns all of the constants in <b>arr</b>, and removes them from the
     * ArrayList.
     * 
     * @return
     */
    public final ArrayList<PowerTerm> getConstantsAndRemove() {
        ArrayList<PowerTerm> constants = new ArrayList<PowerTerm>();

        for (PowerTerm i : arr) {
            if (i.isConstant()) {
                constants.add(i);
            }
        }

        /* Removal */
        arr.removeAll(constants);
        return constants;
    }
    // #endregion

    // #region Specific Terms
    /**
     * Returns an ArrayList of PowerTerms that consists of solely the inputted
     * classes.
     * 
     * @param clazzes
     * @return an empty ArrayList if <b>clazzes</b> is empty or null
     *         <li>an ArrayList of PowerTerms with one of the classes from
     *         <b>clazzes</b>
     */
    public final TermArray getTermsUnion(Class<?>... clazzes) {
        if (clazzes == null || clazzes.length == 0) {
            return new TermArray();
        }

        TermArray output = new TermArray();
        for (PowerTerm i : arr) {
            for (Class<?> clazz : clazzes) {
                if (i.getClass() == clazz) {
                    output.add(i);
                    break;
                    // no need to continue checking if the class was found
                }
            }
        }
        return output;
    }

    /**
     * Returns an ArrayList of PowerTerms that does not include the inputted
     * classes.
     * 
     * @param clazzes
     * @return an empty ArrayList if <b>clazzes</b> is empty or null
     *         <li>an ArrayList of PowerTerms without any of the classes from
     *         <b>clazzes</b>
     */
    public final TermArray getTermsExcluding(Class<?>... clazzes) {
        if (clazzes == null || clazzes.length == 0) {
            return new TermArray();
        }

        TermArray output = new TermArray();
        for (PowerTerm i : arr) {
            boolean isExcluded = false;
            for (Class<?> clazz : clazzes) {
                if (i.getClass() == clazz) {
                    isExcluded = true;
                    break;
                    // no need to continue checking if the class was found
                }

                if (!isExcluded) {
                    output.add(i);
                }
            }
        }
        return output;
    }

    // #endregion

    // #region Overlap Bools
    /**
     * Checks if <b>arr</b> contains a term from the inputted class.
     * 
     * @param clazz
     * @return {@code true} if at least one term is of class clazz
     *         <li>{@code false} if no terms are of class clazz
     */
    public final boolean containsTermClass(Class<?> clazz) {
        for (PowerTerm i : arr) {
            if (i.getClass() == clazz) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks each term to see if <b>this</b> is a superset of <b>other</b>.
     * 
     * @param terms
     * @return
     */
    public final boolean isSupersetOf(TermArray terms) {
        if (terms.size() > size()) {
            // A cannot be a superset if B contains more terms
            return false;
        }

        for (PowerTerm i : terms.getArr()) {
            if (!arr.contains(i)) {
                return false;
            }
        }

        return true;
    }
    // #endregion

    // #region Add
    /**
     * A general-use package for adding terms. This package works if the
     * ArrayList<Term> contains varying types of terms.
     * 
     * <p>
     * When adding, performs deep copies as to not change the data of the
     * PowerTerms. <em>Calling add() changes the data of <b>this</b></em>.
     * 
     * <p>
     * The functionality of this class has been checked on <b>6/22/2025</b> and
     * nothing has changed since.
     */
    public final void add() {
        if (arr == null || isEmpty()) {
            copy(new TermArray(new ArrayList<PowerTerm>()));
            return;
        } else if (size() == 1) {
            return;
        }

        /* Expand Terms */
        TermArray expandedTerms = new TermArray();
        for (PowerTerm i : arr) {
            // as a byproduct, creates deep copies of the term.
            expandedTerms.addAll(i.distributeTerm());
        }

        /* Addition */
        TermArray addedTerms = new TermArray();
        for (PowerTerm i : expandedTerms.getArr()) {
            // the function will test i.isAddend(term) for getting an index
            int index = addedTerms.indexOf(i::isAddend);

            if (index != -1) {
                addedTerms.get(index).add(i);
            } else {
                addedTerms.add(i);
            }
        }

        copy(addedTerms);
    }
    // #endregion

    // #region Multiply
    /**
     * A general-use package for multiplying terms. This package works if the
     * ArrayList<Term> contains varying types of terms.
     * 
     * <p>
     * When multiplying, does NOT perform deep copies of the PowerTerms. <em>Calling
     * multiply() will change the data of <b>this</b></em>.
     * 
     * <p>
     * The functionality of this class has been checked on <b>6/22/2025</b> and
     * nothing has changed since.
     */
    public final void multiply() {
        if (arr == null || isEmpty()) {
            copy(new TermArray(new ArrayList<PowerTerm>()));
            return;
        } else if (size() == 1) {
            // no point to doing multiplication
            return;
        }

        TermArray mTerms = new TermArray();
        ComplexNum coef = new ComplexNum(1, 0);
        for (PowerTerm i : arr) {
            /* Coefficient */
            if (!i.getCoef().equals(1)) {
                coef = ComplexNum.multiply(coef, i.getCoef());
                i.setCoef(1);
            }

            if (i.isConstant()) {
                continue;
            }

            // the function will test i.isFactor(term) for getting an index
            int index = mTerms.indexOf(i::isFactor);
            if (index != -1) {
                mTerms.get(index).multiply(i);
            } else {
                mTerms.add(i);
            }

        }

        if (!coef.equals(1)) {
            mTerms.add(new PowerTerm(coef));
        }
        copy(mTerms);
    }
    // #endregion

    // #region Variables
    public final boolean containsVar(String var) {
        for (PowerTerm i : arr) {
            if (i.containsVar(var)) {
                return true;
            }
        }
        return false;
    }
    // #endregion
}
