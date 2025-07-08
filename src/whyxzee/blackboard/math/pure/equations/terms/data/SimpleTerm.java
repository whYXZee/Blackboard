package whyxzee.blackboard.math.pure.equations.terms.data;

import whyxzee.blackboard.math.pure.equations.terms.PowerTerm;
import whyxzee.blackboard.math.pure.equations.terms.TermData;

public class SimpleTerm extends TermData {
    public SimpleTerm(TermType type) {
        super(type);
    }

    @Override
    public final TermData clone() {
        return new SimpleTerm(getTermType());
    }

    // #region String/Display
    @Override
    public final String termString(String varString) {
        switch (getTermType()) {
            case POWER:
                return varString;
            default:
                return "";
        }
    }
    // #endregion

    // #region Addition
    @Override
    public boolean isAddend(PowerTerm a, PowerTerm b) {
        // TODO: check matching types
        if (a.getData().getTermType() != b.getData().getTermType()) {
            return false;
        }

        return a.getPower().equals(b.getPower())
                && a.getVar().equals(b.getVar());
    }

    @Override
    public PowerTerm add(PowerTerm a, PowerTerm b) {
        return new PowerTerm(a.getCoef().add(b.getCoef()), a.getVar(), a.getPower(), this);
    }
    // #endregion

    // #region Multiplication
    @Override
    public boolean isFactor(PowerTerm a, PowerTerm b) {
        if (a.getData().getTermType() != b.getData().getTermType()) {
            return false;
        }

        return a.getVar().equals(b.getVar());
    }

    @Override
    public PowerTerm multiply(PowerTerm a, PowerTerm b) {
        return new PowerTerm(a.getCoef().multiply(b.getCoef()), a.getVar(), a.getPower().add(b.getVar()), this);
    }
    // #endregion

    // #region Inverse
    @Override
    public final TermData inverse() {
        return this; // lol
    }

    @Override
    public PowerTerm applyInverseTo(PowerTerm a, PowerTerm o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'applyInverseTo'");
    }
    // #endregion

    // #region Comparison Bools
    @Override
    public final boolean equals(Object o) {
        if (o == null || !(o instanceof SimpleTerm)) {
            return false;
        }

        SimpleTerm other = (SimpleTerm) o;
        return getTermType() == other.getTermType();
    }

    @Override
    public boolean equals(PowerTerm a, PowerTerm o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'equals'");
    }
    // #endregion

}
