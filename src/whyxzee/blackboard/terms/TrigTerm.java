package whyxzee.blackboard.terms;

import whyxzee.blackboard.equations.EQMultiplication;
import whyxzee.blackboard.equations.EQSequence;
import whyxzee.blackboard.terms.variables.USubTerm;
import whyxzee.blackboard.terms.variables.USub;
import whyxzee.blackboard.terms.variables.Variable;

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
     * @param num      the constant outside the trig function.
     * @param var      the variable inside of the trig function.
     * @param trigType the type of trig function.
     */
    public TrigTerm(double num, Variable var, TrigType trigType) {
        super(num, var, TermType.TRIGONOMETRIC);
        this.trigType = trigType;
        trigString = declareTrigString();
    }

    @Override
    public String printConsole() {
        return Double.toString(getNum()) + trigString + "(" + getVar().printConsole() + ")";
    }

    //
    // Get and Set Methods
    //
    private String declareTrigString() {
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

    public String getTrigString() {
        return trigString;
    }

    public TrigType getTrigType() {
        return trigType;
    }

    @Override
    public double solve(double value) {
        double innerVal = getVar().solve(value);
        double trigVal = 0;
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
                trigVal = 1 / Math.asin(innerVal);
                break;
            case ARC_COSECANT:
                trigVal = 1 / Math.acos(innerVal);
                break;
            case ARC_COTANGENT:
                trigVal = 1 / Math.atan(innerVal);
                break;
            default:
                break;
        }

        return getNum() * trigVal;
    }

    @Override
    public Term negate() {
        return new TrigTerm(-1 * getNum(), getVar(), trigType);
    }

    @Override
    public Term derive() {
        /* Initializing variables */
        double number = getNum();
        Variable variable = getVar();
        boolean shouldChainRule = variable.getShouldChainRule();

        /* With chain rule */
        switch (trigType) {
            case SINE:
                if (shouldChainRule) {
                    // chain rule
                    USub uSubEQ = new USub(1, new EQMultiplication(
                            // outer function
                            new TrigTerm(1, variable, TrigType.COSINE),

                            // inner function
                            variable.derive()));
                    return new PolynomialTerm(number, uSubEQ);
                } else {
                    // no chain rule
                    return new TrigTerm(number, variable, TrigType.COSINE);
                }
            case COSINE:
                if (shouldChainRule) {
                    // chain rule
                    USub uSubEQ = new USub(1, new EQMultiplication(
                            // outer function
                            new TrigTerm(1, variable, TrigType.SINE),

                            // inner function
                            variable.derive()));
                    return new PolynomialTerm(-number, uSubEQ);

                } else {
                    // no chain rule
                    return new TrigTerm(-number, variable, TrigType.SINE);
                }
            case TANGENT:
                if (shouldChainRule) {
                    // chain rule
                    USub uSubEQ = new USub(1, new EQMultiplication(
                            // outer function
                            new PolynomialTerm(number, new USubTerm(2, new TrigTerm(1, variable,
                                    TrigType.SECANT))),

                            // inner function
                            variable.derive()));
                    return new PolynomialTerm(number, uSubEQ);
                } else {
                    // no chain rule
                    USubTerm uSubTerm = new USubTerm(2, new TrigTerm(1, variable, TrigType.SECANT));
                    return new PolynomialTerm(number, uSubTerm);
                }
            case COSECANT:
                if (shouldChainRule) {
                    // chain rule
                    USub uSubEQ = new USub(1, new EQMultiplication(
                            // outer function
                            new PolynomialTerm(number, new USub(1, new EQMultiplication(
                                    new TrigTerm(1, variable, TrigType.TANGENT),
                                    new TrigTerm(1, variable, TrigType.SECANT)))),

                            // inner function
                            variable.derive()));
                    return new PolynomialTerm(number, uSubEQ);
                } else {
                    // no chain rule
                    USub uSubEQ = new USub(1, new EQMultiplication(
                            new TrigTerm(1, variable, TrigType.TANGENT),
                            new TrigTerm(1, variable, TrigType.SECANT)));
                    return new PolynomialTerm(number, uSubEQ);
                }
            case SECANT:
                if (shouldChainRule) {
                    // chain rule
                    USub uSubEQ = new USub(1, new EQMultiplication(
                            // outer function
                            new PolynomialTerm(number, new USub(1, new EQMultiplication(
                                    new TrigTerm(1, variable, TrigType.COTANGENT),
                                    new TrigTerm(1, variable, TrigType.COSECANT)))),

                            // inner function
                            variable.derive()));
                    return new PolynomialTerm(-number, uSubEQ);
                } else {
                    // no chain rule
                    USub uSubEQ = new USub(1, new EQMultiplication(
                            new TrigTerm(1, variable, TrigType.COTANGENT),
                            new TrigTerm(1, variable, TrigType.COSECANT)));
                    return new PolynomialTerm(-number, uSubEQ);
                }
            case COTANGENT:
                if (shouldChainRule) {
                    // chain rule
                    USub uSubEQ = new USub(1, new EQMultiplication(
                            // outer function
                            new PolynomialTerm(number, new USubTerm(2, new TrigTerm(1, variable,
                                    TrigType.COSECANT))),

                            // inner function
                            variable.derive()));
                    return new PolynomialTerm(-number, uSubEQ);
                } else {
                    // no chain rule
                    USubTerm uSubTerm = new USubTerm(2, new TrigTerm(1, variable, TrigType.SECANT));
                    return new PolynomialTerm(-number, uSubTerm);
                }

            case ARC_SINE:
                if (shouldChainRule) {
                    // chain rule
                    EQSequence innerFunction = new EQSequence(
                            new PolynomialTerm(1, new Variable("x", 0)),
                            new PolynomialTerm(-1, variable.exponentiate(2)));

                    EQMultiplication eq = new EQMultiplication(
                            // outter function (arcsin)
                            new PolynomialTerm(1, new USub(-1, 2, innerFunction)),

                            // Inner function (chain rule)
                            variable.derive());

                    return new PolynomialTerm(number, new USub(1, eq));
                } else {
                    // no chain rule
                    EQSequence innerFunction = new EQSequence(
                            new PolynomialTerm(1, new Variable("x", 0)),
                            new PolynomialTerm(-1, variable));
                    USub uSub = new USub(-1, 2, innerFunction);
                    return new PolynomialTerm(number, uSub);
                }
            case ARC_COSINE:
                // TODO: derivative of arccos unimplemented
            case ARC_TANGENT:
                if (shouldChainRule) {
                    // chain rule
                    EQSequence innerFunction = new EQSequence(
                            new PolynomialTerm(1, variable.exponentiate(2)),
                            new PolynomialTerm(1, new Variable("x", 0)));

                    EQMultiplication eq = new EQMultiplication(
                            // outter function (arcsin)
                            new PolynomialTerm(1, new USub(-1, innerFunction)),

                            // Inner function (chain rule)
                            variable.derive());

                    return new PolynomialTerm(number, new USub(1, eq));
                } else {
                    // no chain rule
                    EQSequence innerFunction = new EQSequence(
                            new PolynomialTerm(1, variable.exponentiate(2)),
                            new PolynomialTerm(1, new Variable("x", 0)));
                    USub uSub = new USub(-1, innerFunction);
                    return new PolynomialTerm(number, uSub);
                }
            case ARC_COSECANT:
                if (shouldChainRule) {
                    // chain rule
                    EQSequence innerFunction = new EQSequence(
                            new PolynomialTerm(1, new Variable("x", 0)),
                            new PolynomialTerm(-1, variable.exponentiate(2)));

                    EQMultiplication eq = new EQMultiplication(
                            // outter function (arcsin)
                            new PolynomialTerm(1, new USub(-1, 2, innerFunction)),

                            // Inner function (chain rule)
                            variable.derive());

                    return new PolynomialTerm(-number, new USub(1, eq));
                } else {
                    // no chain rule
                    EQSequence innerFunction = new EQSequence(
                            new PolynomialTerm(1, new Variable("x", 0)),
                            new PolynomialTerm(-1, variable));
                    USub uSub = new USub(-1, 2, innerFunction);
                    return new PolynomialTerm(-number, uSub);
                }
            case ARC_SECANT:
                // TODO: derivative of arcsec unimplemented
            case ARC_COTANGENT:
                if (shouldChainRule) {
                    // chain rule
                    EQSequence innerFunction = new EQSequence(
                            new PolynomialTerm(1, variable.exponentiate(2)),
                            new PolynomialTerm(1, new Variable("x", 0)));

                    EQMultiplication eq = new EQMultiplication(
                            // outter function (arcsin)
                            new PolynomialTerm(1, new USub(-1, innerFunction)),

                            // Inner function (chain rule)
                            variable.derive());

                    return new PolynomialTerm(-number, new USub(1, eq));
                } else {
                    // no chain rule
                    EQSequence innerFunction = new EQSequence(
                            new PolynomialTerm(1, new Variable("x", 0)),
                            new PolynomialTerm(-1, variable));
                    USub uSub = new USub(-1, innerFunction);
                    return new PolynomialTerm(-number, uSub);
                }
            default:
                return null;
        }
    }

    @Override
    public double limInfSolve() {
        return Double.NaN;
    }

}
