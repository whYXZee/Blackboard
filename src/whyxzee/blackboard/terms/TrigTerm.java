package whyxzee.blackboard.terms;

import whyxzee.blackboard.equations.EQMultiplication;
import whyxzee.blackboard.equations.EQSequence;
import whyxzee.blackboard.terms.variables.USub;
import whyxzee.blackboard.terms.variables.Variable;

/**
 * A package for trigonometic terms.
 * 
 * <p>
 * The functionality of this class has been checked on {@code 5/20/2025}, and
 * the following has changed since:
 * <ul>
 * <li>similarTo()
 * <li>equals()
 */
public class TrigTerm extends Term {
    public enum TrigType {
        SINE,
        COSINE,
        TANGENT,
        COSECANT,
        SECANT,
        COTANGENT,

        ARC_SINE,
        ARC_COSINE,
        ARC_TANGENT,
        ARC_COSECANT,
        ARC_SECANT,
        ARC_COTANGENT,
    }

    /* Variables */
    private TrigType trigType;
    private String trigString;

    /**
     * 
     * @param coefficient the constant outside the trig function.
     * @param var         the variable inside of the trig function.
     * @param trigType    the type of trig function.
     */
    public TrigTerm(double coefficient, Variable var, TrigType trigType) {
        super(coefficient, var, TermType.TRIGONOMETRIC);
        this.trigType = trigType;
        trigString = declareTrigString();
    }

    @Override
    public final String toString() {
        double coef = getCoef();
        if (coef == 0) {
            return "0";
        } else if (coef == 1) {
            return trigString + "(" + getVar().toString() + ")";
        } else {
            return Double.toString(coef) + trigString + "(" + getVar().toString() + ")";
        }
    }

    @Override
    public final String printConsole() {
        double coef = getCoef();
        if (coef == 0) {
            return "0";
        } else if (coef == 1) {
            return trigString + "(" + getVar().printConsole() + ")";
        } else {
            return Double.toString(coef) + trigString + "(" + getVar().printConsole() + ")";
        }
    }

    //
    // Get and Set Methods
    //
    private final String declareTrigString() {
        switch (trigType) {
            case SINE:
                return "sin";
            case COSINE:
                return "cos";
            case TANGENT:
                return "tan";
            case COSECANT:
                return "csc";
            case SECANT:
                return "sec";
            case COTANGENT:
                return "cot";

            case ARC_SINE:
                return "arcsin";
            case ARC_COSINE:
                return "arccos";
            case ARC_TANGENT:
                return "arctan";
            case ARC_COSECANT:
                return "arccsc";
            case ARC_SECANT:
                return "arcsec";
            case ARC_COTANGENT:
                return "arccot";
            default:
                return "";
        }
    }

    public final TrigType getTrigType() {
        return trigType;
    }

    //
    // Arithmetic Methods
    //

    @Override
    public final double solve(double value) {
        /* Initializing variables */
        double innerVal = getVar().solve(value);
        double trigVal = 0;

        /* Algorithm */
        switch (trigType) {
            case SINE:
                trigVal = Math.sin(innerVal);
                break;
            case COSINE:
                trigVal = Math.cos(innerVal);
                break;

            /* doesn't factor for 1/0 nor NaN */
            case TANGENT:
                trigVal = Math.tan(innerVal);
                break;
            case COSECANT:
                trigVal = 1 / Math.sin(innerVal);
                break;
            case SECANT:
                trigVal = 1 / Math.cos(innerVal);
                break;
            case COTANGENT:
                trigVal = 1 / Math.tan(innerVal);
                break;

            case ARC_SINE:
                trigVal = Math.asin(innerVal);
                break;
            case ARC_COSINE:
                trigVal = Math.acos(innerVal);
                break;

            /* doesn't factor for 1/0 nor NaN */
            case ARC_TANGENT:
                trigVal = Math.atan(innerVal);
                break;
            case ARC_SECANT:
                trigVal = Math.acos(1 / innerVal);
                break;
            case ARC_COSECANT:
                trigVal = Math.asin(1 / innerVal);
                break;
            case ARC_COTANGENT:
                trigVal = Math.atan(1 / innerVal);
                break;
            default:
                break;
        }

        return getCoef() * trigVal;
    }

    @Override
    public final Term negate() {
        return new TrigTerm(-1 * getCoef(), getVar(), trigType);
    }

    @Override
    public final Term derive() {
        /* Initializing variables */
        double coef = getCoef();
        Variable variable = getVar().clone();
        boolean shouldChainRule = variable.needsChainRule();

        /* With chain rule */
        switch (trigType) {
            case SINE:
                if (shouldChainRule) {
                    // chain rule
                    USub uSubEQ = new USub(new EQMultiplication(
                            // outer function
                            new TrigTerm(1, variable, TrigType.COSINE),

                            // inner function
                            variable.derive()));
                    return new PowerTerm(coef, uSubEQ, 1);
                } else {
                    // no chain rule
                    return new TrigTerm(coef, variable, TrigType.COSINE);
                }
            case COSINE:
                if (shouldChainRule) {
                    // chain rule
                    USub uSubEQ = new USub(new EQMultiplication(
                            // outer function
                            new TrigTerm(1, variable, TrigType.SINE),

                            // inner function
                            variable.derive()));
                    return new PowerTerm(-coef, uSubEQ, 1);

                } else {
                    // no chain rule
                    return new TrigTerm(-coef, variable, TrigType.SINE);
                }
            case TANGENT:
                if (shouldChainRule) {
                    // chain rule
                    USub uSubEQ = new USub(new EQMultiplication(
                            // outer function
                            new PowerTerm(1, new USub(new TrigTerm(1, variable, TrigType.SECANT)), 2),

                            // inner function
                            variable.derive()));
                    return new PowerTerm(coef, uSubEQ, 1);
                } else {
                    // no chain rule
                    USub uSub = new USub(new TrigTerm(1, variable, TrigType.SECANT));
                    return new PowerTerm(coef, uSub, 2);
                }
            case SECANT:
                if (shouldChainRule) {
                    // chain rule
                    USub uSubEQ = new USub(new EQMultiplication(
                            // outer function
                            new PowerTerm(coef, new USub(new EQMultiplication(
                                    new TrigTerm(1, variable, TrigType.TANGENT),
                                    new TrigTerm(1, variable, TrigType.SECANT))), 1),

                            // inner function
                            variable.derive()));
                    return new PowerTerm(coef, uSubEQ, 1);
                } else {
                    // no chain rule
                    USub uSubEQ = new USub(new EQMultiplication(
                            new TrigTerm(1, variable, TrigType.TANGENT),
                            new TrigTerm(1, variable, TrigType.SECANT)));
                    return new PowerTerm(coef, uSubEQ, 1);
                }
            case COSECANT:
                if (shouldChainRule) {
                    // chain rule
                    USub uSubEQ = new USub(new EQMultiplication(
                            // outer function
                            new PowerTerm(coef, new USub(new EQMultiplication(
                                    new TrigTerm(1, variable, TrigType.COTANGENT),
                                    new TrigTerm(1, variable, TrigType.COSECANT))), 1),

                            // inner function
                            variable.derive()));
                    return new PowerTerm(-coef, uSubEQ, 1);
                } else {
                    // no chain rule
                    USub uSubEQ = new USub(new EQMultiplication(
                            new TrigTerm(1, variable, TrigType.COTANGENT),
                            new TrigTerm(1, variable, TrigType.COSECANT)));
                    return new PowerTerm(-coef, uSubEQ, 1);
                }
            case COTANGENT:
                if (shouldChainRule) {
                    // chain rule
                    USub uSubEQ = new USub(new EQMultiplication(
                            // outer function
                            new PowerTerm(coef, new USub(new TrigTerm(1, variable,
                                    TrigType.COSECANT)), 2),

                            // inner function
                            variable.derive()));
                    return new PowerTerm(-coef, uSubEQ, 1);
                } else {
                    // no chain rule
                    USub uSub = new USub(new TrigTerm(1, variable, TrigType.COSECANT));
                    return new PowerTerm(-coef, uSub, 2);
                }

            case ARC_SINE:
                if (shouldChainRule) {
                    // chain rule
                    EQSequence innerFunction = new EQSequence(
                            new PowerTerm(1),
                            new PowerTerm(-1, variable, 2));

                    EQMultiplication eq = new EQMultiplication(
                            // outter function (arcsin)
                            new PowerTerm(1, new USub(innerFunction), -1, 2),

                            // Inner function (chain rule)
                            variable.derive());

                    return new PowerTerm(coef, new USub(eq), 1);
                } else {
                    // no chain rule
                    EQSequence innerFunction = new EQSequence(
                            new PowerTerm(1),
                            new PowerTerm(-1, variable, 2));
                    return new PowerTerm(coef, new USub(innerFunction), -1, 2);
                }
            case ARC_COSINE:
                if (shouldChainRule) {
                    // chain rule
                    EQSequence innerFunction = new EQSequence(
                            new PowerTerm(1),
                            new PowerTerm(-1, variable, 2));

                    EQMultiplication eq = new EQMultiplication(
                            // outter function (arcsin)
                            new PowerTerm(1, new USub(innerFunction), -1, 2),

                            // Inner function (chain rule)
                            variable.derive());

                    return new PowerTerm(-coef, new USub(eq), 1);
                } else {
                    // no chain rule
                    EQSequence innerFunction = new EQSequence(
                            new PowerTerm(1),
                            new PowerTerm(-1, variable, 2));
                    return new PowerTerm(-coef, new USub(innerFunction), -1, 2);
                }
            case ARC_TANGENT:
                if (shouldChainRule) {
                    // chain rule
                    EQSequence innerFunction = new EQSequence(
                            new PowerTerm(1, variable, 2),
                            new PowerTerm(1));

                    EQMultiplication eq = new EQMultiplication(
                            // outter function (arcsin)
                            new PowerTerm(1, new USub(innerFunction), -1),

                            // Inner function (chain rule)
                            variable.derive());

                    return new PowerTerm(coef, new USub(eq), 1);
                } else {
                    // no chain rule
                    EQSequence innerFunction = new EQSequence(
                            new PowerTerm(1, variable, 2),
                            new PowerTerm(1));
                    return new PowerTerm(coef, new USub(innerFunction), -1);
                }
            case ARC_COSECANT:
                if (shouldChainRule) {
                    // chain rule
                    EQSequence innerFunction = new EQSequence(
                            new PowerTerm(1, variable, 2),
                            new PowerTerm(-1));

                    EQMultiplication eq = new EQMultiplication(
                            // outter function (arccsc)
                            new AbsoluteValTerm(1, variable),
                            new PowerTerm(1, new USub(innerFunction), -1),

                            // Inner function (chain rule)
                            variable.derive());

                    return new PowerTerm(-coef, new USub(eq), 1);
                } else {
                    // no chain rule
                    EQSequence innerFunction = new EQSequence(
                            new PowerTerm(1, variable, 2),
                            new PowerTerm(-1));

                    EQMultiplication eq = new EQMultiplication(
                            new AbsoluteValTerm(1, variable),
                            new PowerTerm(1, new USub(innerFunction), 1, 2));
                    return new PowerTerm(-coef, new USub(eq), -1);
                }
            case ARC_SECANT:
                if (shouldChainRule) {
                    // chain rule
                    EQSequence innerFunction = new EQSequence(
                            new PowerTerm(1, variable, 2),
                            new PowerTerm(-1));

                    EQMultiplication eq = new EQMultiplication(
                            // outter function (arccsc)
                            new AbsoluteValTerm(1, variable),
                            new PowerTerm(1, new USub(innerFunction), -1),

                            // Inner function (chain rule)
                            variable.derive());

                    return new PowerTerm(coef, new USub(eq), 1);
                } else {
                    // no chain rule
                    EQSequence innerFunction = new EQSequence(
                            new PowerTerm(1, variable, 2),
                            new PowerTerm(-1));

                    EQMultiplication eq = new EQMultiplication(
                            new AbsoluteValTerm(1, variable),
                            new PowerTerm(1, new USub(innerFunction), 1, 2));
                    return new PowerTerm(coef, new USub(eq), -1);
                }
            case ARC_COTANGENT:
                if (shouldChainRule) {
                    // chain rule
                    EQSequence innerFunction = new EQSequence(
                            new PowerTerm(1, variable, 2),
                            new PowerTerm(1));

                    EQMultiplication eq = new EQMultiplication(
                            // outter function (arcsin)
                            new PowerTerm(1, new USub(innerFunction), -1),

                            // Inner function (chain rule)
                            variable.derive());

                    return new PowerTerm(-coef, new USub(eq), 1);
                } else {
                    // no chain rule
                    EQSequence innerFunction = new EQSequence(
                            new PowerTerm(1, variable, 2),
                            new PowerTerm(1));
                    return new PowerTerm(-coef, new USub(innerFunction), -1);
                }
            default:
                return null;
        }
    }

    @Override
    public final double limInfSolve() {
        /* Initiating variables */
        double number = getCoef();

        /* Algorithm */
        if (number == 0) {
            return 0;
        }
        boolean isNumberNegative = number < 0;

        switch (trigType) {
            case ARC_TANGENT:
                return isNumberNegative ? -Math.PI / 2 : Math.PI / 2;
            case ARC_SECANT:
                return isNumberNegative ? -Math.PI / 2 : Math.PI / 2;
            case ARC_COSECANT:
                return isNumberNegative ? -0 : 0;
            case ARC_COTANGENT:
                return isNumberNegative ? -0 : 0;
            default:
                return Double.NaN;
        }
    }

    @Override
    public final double limNegInfSolve() {
        /* Initializing variables */
        double number = getCoef();

        /* Algorithm */
        if (number == 0) {
            return 0;
        }
        boolean isNumberNegative = number < 0;

        switch (trigType) {
            case ARC_TANGENT:
                return isNumberNegative ? Math.PI / 2 : -Math.PI / 2;
            case ARC_SECANT:
                return isNumberNegative ? -Math.PI / 2 : Math.PI / 2;
            case ARC_COSECANT:
                return isNumberNegative ? 0 : -0;
            case ARC_COTANGENT:
                return isNumberNegative ? -Math.PI : Math.PI;
            default:
                return Double.NaN;
        }
    }

    //
    // Boolean Methods
    //
    @Override
    public final boolean similarTo(Term term) {
        switch (term.getTermType()) {
            case TRIGONOMETRIC:
                TrigTerm trigTerm = (TrigTerm) term;
                return (trigTerm.getVar().equals(this.getVar())) &&
                        (trigTerm.getTrigType() == this.trigType);
            default:
                return false;
        }
    }

    @Override
    public final boolean equals(Term other) {
        if ((other.getTermType() == TermType.TRIGONOMETRIC) && (getCoef() == other.getCoef())) {
            TrigTerm trigTerm = (TrigTerm) other;
            return (trigType == trigTerm.getTrigType()) &&
                    (getVar().equals(trigTerm.getVar()));
        }
        return false;
    }
}
