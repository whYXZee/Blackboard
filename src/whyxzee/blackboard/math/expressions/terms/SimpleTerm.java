package whyxzee.blackboard.math.expressions.terms;

import whyxzee.blackboard.math.expressions.Term;
import whyxzee.blackboard.math.expressions.TermData;

// essentially a power term
public class SimpleTerm implements TermData {

    public SimpleTerm() {
    }

    @Override
    public SimpleTerm clone() {
        return new SimpleTerm();
    }

    @Override
    public String termString(String varString) {
        return varString;
    }

    // #region Addition
    @Override
    public final boolean isAddend(Term dis, Term o) {
        if (dis.getDataClass() != o.getDataClass()) {
            // must be the same TermData
            return false;
        }

        return dis.getPower().equals(o.getPower())
                && dis.getVar().equals(o.getVar())
                && extraAddendConditions(dis, o);
    }

    public boolean extraAddendConditions(Term dis, Term o) {
        return true;
    }

    @Override
    public Term add(Term dis, Term o) {
        return new Term(dis.getCoef().add(o.getCoef()), dis.cloneVar(), dis.getPower(), this);
    }
    // #endregion

    // #region Multiplication
    @Override
    public final boolean isFactor(Term dis, Term o) {
        if (dis.getDataClass() != o.getDataClass()) {
            return false;
        }

        return dis.getVar().equals(o.getVar()) && extraFactorConditions(dis, o);
    }

    public boolean extraFactorConditions(Term dis, Term o) {
        return true;
    }

    @Override
    public Term multiply(Term a, Term b) {
        return new Term(a.getCoef().multiply(b.getCoef()), a.cloneVar(), a.getPower().add(b.getPower()), this);
    }
    // #endregion

    // #region Inverse
    @Override
    public TermData inverse() {
        return clone();
    }

    @Override
    public Term applyInverseTo(Term o) {
        return o; // nothing changes so :shrug:
    }
    // #endregion

    @Override
    public boolean equals(Term a, Term o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'equals'");
    }

}
