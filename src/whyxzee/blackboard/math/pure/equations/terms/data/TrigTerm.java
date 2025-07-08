package whyxzee.blackboard.math.pure.equations.terms.data;

import whyxzee.blackboard.math.pure.equations.terms.PowerTerm;
import whyxzee.blackboard.math.pure.equations.terms.TermData;
import whyxzee.blackboard.math.pure.equations.variables.Variable;
import whyxzee.blackboard.math.pure.numbers.Complex;
import whyxzee.blackboard.math.utils.pure.TrigUtils;
import whyxzee.blackboard.math.utils.pure.TrigUtils.TrigType;

public class TrigTerm extends TermData {
    /* Variables */
    private TrigType trig;

    public TrigTerm(TrigType trigType) {
        super(TermType.TRIG);
        this.trig = trigType;
    }

    // #region TrigType
    public final TrigType getTrig() {
        return trig;
    }
    // #endregion

    @Override
    public TermData clone() {
        return new TrigTerm(trig);
    }

    // #region String/Display
    @Override
    public final String termString(String varString) {
        return trig.getString() + "(" + varString + ")";
    }
    // #endregion

    // #region Addition
    @Override
    public boolean isAddend(PowerTerm a, PowerTerm b) {
        if (!(a.getData() instanceof TrigTerm) || !(b.getData() instanceof TrigTerm)) {
            return false;
        }

        return a.getVar().equals(b.getVar())
                && a.getPower().equals(b.getCoef())
                && ((TrigTerm) a.getData()).getTrig() == ((TrigTerm) b.getData()).getTrig();
    }

    @Override
    public PowerTerm add(PowerTerm a, PowerTerm b) {
        return new PowerTerm(a.getCoef().add(b.getCoef()), a.getVar(), a.getPower(), this);
    }
    // #endregion

    // #region Multiplication
    @Override
    public boolean isFactor(PowerTerm a, PowerTerm b) {
        if (!(a.getData() instanceof TrigTerm) || !(b.getData() instanceof TrigTerm)) {
            return false;
        }

        return a.getVar().equals(b.getVar())
                && ((TrigTerm) a.getData()).getTrig() == ((TrigTerm) b.getData()).getTrig();
    }

    @Override
    public PowerTerm multiply(PowerTerm a, PowerTerm b) {
        return new PowerTerm(a.getCoef().multiply(b.getCoef()), a.getVar(), a.getPower().add(b.getVar()), this);
    }
    // #endregion

    // #region Inverse
    @Override
    public final TermData inverse() {
        return new TrigTerm(TrigUtils.inverseOf(trig));
    }

    @Override
    public PowerTerm applyInverseTo(PowerTerm a, PowerTerm o) {
        if (!a.getPower().equals(1)) {
            o = a.applyInversePowTo(o);
            a.setPower(1);
        }

        PowerTerm output = new PowerTerm(Complex.cmplx(1, 0), new Variable<PowerTerm>(o), Complex.cmplx(1, 0),
                this.inverse());
        return output;
    }
    // #endregion

    // #region Comparison Bools
    @Override
    public final boolean equals(Object o) {
        if (o == null || !(o instanceof TrigTerm)) {
            return false;
        }

        TrigTerm other = (TrigTerm) o;
        return trig == other.getTrig();
    }

    @Override
    public boolean equals(PowerTerm a, PowerTerm o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'equals'");
    }
    // #endregion

}
