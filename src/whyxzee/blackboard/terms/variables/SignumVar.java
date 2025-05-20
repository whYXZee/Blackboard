package whyxzee.blackboard.terms.variables;

import whyxzee.blackboard.terms.PolynomialTerm;
import whyxzee.blackboard.terms.Term;

public class SignumVar extends Variable {
    public SignumVar(String var) {
        super("sgn(" + var + ")", 1, VarType.SIGNUM);
    }

    @Override
    public String toString() {
        return getVar();
    }

    @Override
    public String printConsole() {
        return toString();
    }

    //
    // Arithmetic Methods
    //
    @Override
    public double solve(double value) {
        return Math.signum(value);
    }

    @Override
    public Term derive() {
        return PolynomialTerm.ZERO_TERM;
    }

    @Override
    public boolean needsChainRule() {
        return false;
    }
}
