package whyxzee.blackboard.math.pure.equations;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;

import whyxzee.blackboard.math.pure.equations.terms.PowerTerm;
import whyxzee.blackboard.math.pure.numbers.Complex;

/**
 * <p>
 * The functionality of this class has been checked on <b>6/22/2025</b> and
 * nothing has changed since.
 */
public class TermArray implements Collection<PowerTerm> {
    /* Variables */
    private PowerTerm[] arr;
    // when cloning, the elements need to be cloned but not the array.

    // #region Constructors
    public TermArray(PowerTerm... terms) {
        arr = terms;
    }

    public TermArray(Collection<? extends PowerTerm> c) {
        arr = new PowerTerm[0];
        addAll(c);
    }
    // #endregion

    // #region String/Display
    @Override
    public final String toString() {
        if (arr.length == 0) {
            return "[]";
        }

        String output = "[" + arr[0];
        for (int i = 1; i < arr.length; i++) {
            output += ", " + arr[i];
        }
        output += "]";
        return output;
    }
    // #endregion

    // #region Copying/Cloning
    public final void copy(TermArray o) {
        this.arr = o.getArr();
    }

    /**
     * Returns a deep copy of every element in the array.
     */
    @Override
    public final TermArray clone() {
        PowerTerm[] newArr = new PowerTerm[arr.length];

        for (int i = 0; i < arr.length; i++) {
            newArr[i] = arr[i].clone();
        }
        return new TermArray(newArr);
    }
    // #endregion

    // #region Get/Set
    public final PowerTerm[] getArr() {
        return arr;
    }
    // #endregion

    // #region Indices
    public final PowerTerm get(int index) {
        return arr[index];
    }

    public final void update(int index, PowerTerm term) {
        arr[index] = term;
    }

    public final int indexOf(PowerTerm term) {
        for (int i = 0; i < arr.length; i++) {
            if (term.equals(arr[i])) {
                return i;
            }
        }
        return -1;
    }

    public final int indexOf(Predicate<PowerTerm> criteria) {
        for (int i = 0; i < arr.length; i++) {
            if (criteria.test(arr[i])) {
                return i;
            }
        }
        return -1;
    }
    // #endregion

    // #region Sizes
    /**
     * Resizes the array as to remove empty spaces at the end.
     */
    public final void trimSize(int numOfEmptyIndices) {
        PowerTerm[] old = arr;
        arr = new PowerTerm[old.length - numOfEmptyIndices];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = old[i];
        }
    }

    @Override
    public final int size() {
        return arr.length;
    }

    /**
     * Checks if <b>arr</b> has size <b>size</b>.
     * 
     * @param size
     * @return
     */
    public final boolean isSize(int size) {
        return arr.length == size;
    }

    @Override
    public final boolean isEmpty() {
        return arr.length == 0;
    }
    // #endregion

    // #region Adding Indices
    @Override
    public boolean add(PowerTerm e) {
        PowerTerm[] old = arr;
        arr = new PowerTerm[old.length + 1];
        for (int i = 0; i < old.length; i++) {
            arr[i] = old[i];
        }
        arr[old.length] = e;

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends PowerTerm> c) {
        PowerTerm[] old = arr;
        arr = new PowerTerm[old.length + c.size()];

        /* Adding the old terms */
        for (int i = 0; i < old.length; i++) {
            arr[i] = old[i];
        }

        /* Adding new terms */
        PowerTerm[] cArr = (PowerTerm[]) c.toArray();
        for (int i = 0; i < c.size(); i++) {
            arr[old.length + i] = cArr[i];
        }

        return true;
    }
    // #endregion

    // #region Removing Indices
    @Override
    public void clear() {
        arr = new PowerTerm[0];
    }

    public boolean remove(int index) {
        for (int i = 0; i < arr.length; i++) {
            PowerTerm iTerm = arr[i];
            if (i > index) {
                arr[i - 1] = iTerm;
            }
        }

        trimSize(1);
        return true;
    }

    /**
     * Removes all instances of <b>o</b>.
     * 
     * @param o
     * @return {@code true} if at least one object was removed
     *         <li>{@code false} if no objects were removed
     */
    @Override
    public boolean remove(Object o) {
        int shiftBackBy = 0;

        for (int i = 0; i < arr.length; i++) {
            PowerTerm iTerm = arr[i];
            if (iTerm.equals(o)) {
                shiftBackBy++;
            } else {
                arr[i - shiftBackBy] = iTerm;
            }
        }

        /* Resizes the array */
        trimSize(shiftBackBy);

        return shiftBackBy != 0;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean allRemoved = true;
        for (Object i : c) {
            if (!remove(i)) {
                allRemoved = false;
            }
        }
        return allRemoved;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retainAll'");
    }
    // #endregion

    // #region Conversion Methods
    @Override
    public Object[] toArray() {
        return arr;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // transfers the contents of this into a

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }
    // #endregion

    // #region Overlap Bools
    /**
     * Utilizies
     * {@link whyxzee.blackboard.math.pure.equations.terms.PowerTerm#equals(Object)}
     * to check if the array contains <b>o</b>.
     * 
     * @param o
     * @return
     */
    @Override
    public boolean contains(Object o) {
        for (PowerTerm i : arr) {
            if (i.equals(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Utilizes the condition when checking if an array contains an object.
     * 
     * @param condition allows for a method reference.
     * @return
     */
    public boolean containsVar(String var) {
        for (PowerTerm i : arr) {
            if (i.containsVar(var)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object i : c) {
            if (!contains(i)) {
                return false;
            }
        }
        return true;
    }

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

    // #endregion

    // #region Iteration
    @Override
    public Iterator<PowerTerm> iterator() {
        return new TermArrayIterator(arr);
    }

    /**
     * <p>
     * The functionality of this class has been tested on <b>6/22/2025</b>, and
     * nothing has changed since.
     */
    class TermArrayIterator implements Iterator<PowerTerm> {
        /* Variables */
        private int currentIndex;
        private PowerTerm[] arr;

        public TermArrayIterator(PowerTerm[] arr) {
            currentIndex = -1;
            this.arr = arr;
        }

        @Override
        public boolean hasNext() {
            // if equals then there is no next
            return currentIndex != arr.length - 1;
        }

        @Override
        public PowerTerm next() {
            currentIndex++;
            return arr[currentIndex];
        }
    }
    // #endregion

    // #region Constants
    /**
     * Checks the entire array to determine if all of the terms are a constant.
     * 
     * @return
     */
    public final boolean areAllConstants() {
        for (PowerTerm i : arr) {
            if (!i.isConstant()) {
                return false;
            }
        }
        return true;
    }

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
        PowerTerm constant = getConstant();

        if (!constant.getCoef().equals(0)) {
            remove(constant);
        }
        return constant;
    }

    /**
     * Returns all of the constants in <b>arr</b>.
     * 
     * @return
     */
    public final TermArray getConstants() {
        TermArray constants = new TermArray();

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
    public final TermArray getConstantsAndRemove() {
        TermArray constants = new TermArray();

        for (PowerTerm i : arr) {
            if (i.isConstant()) {
                constants.add(i);
            }
        }

        /* Removal */
        removeAll(constants);
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

    // #region Addition
    /**
     * A general-use package for adding terms. This package works if the
     * ArrayList<Term> contains varying types of terms.
     * 
     * <p>
     * When adding, performs deep copies of the terms as to not change the data of
     * the PowerTerms. <em>Calling add() changes the data of <b>this</b></em>.
     * 
     * <p>
     * The functionality of this class has been checked on <b>6/22/2025</b> and
     * nothing has changed since.
     */
    public final void addition() {
        if (arr == null || isEmpty()) {
            copy(new TermArray());
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
        for (PowerTerm i : expandedTerms) {
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

    // #region Multiplication
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
    public final void multiplication() {
        if (arr == null || isEmpty()) {
            copy(new TermArray());
            return;
        } else if (size() == 1) {
            // no point to doing multiplication
            return;
        }

        TermArray mTerms = new TermArray();
        Complex coef = Complex.cmplx(1, 0);
        for (PowerTerm i : arr) {
            /* Coefficient */
            if (!i.getCoef().equals(1)) {
                coef.multiply(i.getCoef());
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
}
