package whyxzee.blackboard.math.number.complex;

import whyxzee.blackboard.math.number.BVal;
import whyxzee.blackboard.math.number.BNum;
import whyxzee.blackboard.math.number.ComplexSys;

/**
 * A package for the standard Complex system. The imaginary character is
 * represented by "i" and all of the calculations are based on circular
 * trigonometry.
 * 
 * <p>
 * The functionality of this class has not been checked.
 */
public class BaseComplex extends ComplexSys<BVal, Double> {
    // #region Constants
    public static final double NEG_ONE_TO_I = Math.exp(-Math.PI);
    // #endregion

    // #region Constructors
    public BaseComplex() {
        super('i');
    }

    public static final BNum fromPolar(BVal mod, double theta) {
        BVal angle = new BVal(theta);
        return BNum.complex(mod.multiply(angle.cos()), mod.multiply(angle.sin()));
    }
    // #endregion

    // #region Copying/Cloning
    public BaseComplex clone() {
        BaseComplex cmplx = new BaseComplex();
        cmplx.setModulus(getModulus().clone());
        cmplx.setTheta(getTheta());
        return cmplx;
    }
    // #endregion

    // #region System Def
    @Override
    public void calcModulus(BVal a, BVal b) {
        BVal mod = new BVal(Math.sqrt(a.pow(2).getValue() + b.pow(2).getValue()));
        setModulus(mod);
    }

    @Override
    public void calcTheta(BVal a, BVal b) {
        double theta = Math.atan(b.divide(a).getValue());
        if ((a.isNegative())) {
            theta += Math.PI;
        }
        setTheta(theta);
    }

    @Override
    public BNum toRectangular() {
        BVal mod = getModulus();
        BVal angle = new BVal(getTheta());
        return BNum.complex(mod.multiply(angle.cos()), mod.multiply(angle.sin()));
    }
    // #endregion

    // #region Addition
    @Override
    public BNum add(BNum arg0, BNum... addends) {
        BVal a = arg0.cloneA(), b = arg0.cloneB();

        for (BNum i : addends) {
            ComplexSys<?, ?> iSys = i.getCmplxSys();
            if (iSys instanceof BaseComplex) {
                a = a.add(i.getA());
                b = b.add(i.getB());
            } else {
                a = a.add(i.getA());
                // conversion :sob:
            }
        }

        return BNum.complex(a, b);
    }
    // #endregion

    // #region Multiplication
    @Override
    public BNum multiply(BNum arg0, BNum... factors) {
        BVal mod;
        double theta;
        try {
            BaseComplex cmplxSys = (BaseComplex) arg0.getCmplxSys();
            mod = cmplxSys.getModulus().clone();
            theta = cmplxSys.getTheta();
        } catch (ClassCastException e) {
            // conversions yay
            mod = null;
            theta = 0;
        }

        for (BNum i : factors) {
            try {
                BaseComplex cmplxSys = (BaseComplex) i.getCmplxSys();
                mod = mod.multiply(cmplxSys.getModulus());
                theta += cmplxSys.getTheta();
            } catch (ClassCastException e) {
                // conversions sob

            }
        }

        return fromPolar(mod, theta);
    }
    // #endregion

    // #region Division
    @Override
    public BNum divide(BNum arg0, BNum... factors) {
        BVal mod;
        double theta;
        try {
            BaseComplex cmplxSys = (BaseComplex) arg0.getCmplxSys();
            mod = cmplxSys.getModulus().clone();
            theta = cmplxSys.getTheta();
        } catch (ClassCastException e) {
            // conversions yay
            mod = null;
            theta = 0;
        }

        /* Iterate */
        for (BNum i : factors) {
            try {
                BaseComplex cmplxSys = (BaseComplex) i.getCmplxSys();
                mod = mod.divide(cmplxSys.getModulus());
                theta -= cmplxSys.getTheta();
            } catch (ClassCastException e) {
                // conversions sob

            }
        }

        return fromPolar(mod, theta);
    }
    // #endregion

    // #region Power
    @Override
    public BNum power(BNum base, BNum exponent) {
        BaseComplex baseData, expData;
        try {
            expData = (BaseComplex) exponent.getCmplxSys();
        } catch (ClassCastException e) {
            expData = new BaseComplex();
        }

        try {
            baseData = (BaseComplex) base.getCmplxSys();
        } catch (ClassCastException e) {
            baseData = new BaseComplex();
        }

        if (base.isReal() && exponent.isReal()) {
            /* real^real */
            BVal out = base.getA().pow(exponent.getA());
            return BNum.complex(out, new BVal(0));

        } else if (!base.isReal() && exponent.isReal()) {
            /* complex^real */
            return fromPolar(baseData.getModulus().pow(exponent.getA()),
                    exponent.getA().getValue() * baseData.getTheta());

        } else if (base.isReal() && !exponent.isReal()) {
            /* real^complex */
            BVal baseVal = base.getA();
            boolean isNegative = baseVal.isNegative();
            if (isNegative) {
                baseVal = baseVal.negate();
            }
            BVal mod = BVal.exp(exponent.getA().multiply(baseVal.ln())); // e^(a * lnx)
            BVal theta = exponent.getB().multiply(baseVal.ln());

            BNum output = fromPolar(mod, theta.getValue());

            return isNegative
                    ? output.multiply(
                            fromPolar(BVal.exp(exponent.getB().multiply(BVal.PI())), // e^(-pi * b)
                                    exponent.getA().multiply(BVal.PI()).getValue())) // (pi * a)
                    : output;
        } else {
            /* complex^complex */
            BVal exp = BVal.exp(exponent.getB().negate().multiply(new BVal(baseData.getTheta()))); // e^(-b2 * theta1)

            return power(BNum.complex(baseData.getModulus(), new BVal(0)), exponent) // r1^z2
                    .multiply(fromPolar(exp, exponent.getA().multiply(new BVal(baseData.getTheta())).getValue()));
        }
    }
    // #endregion

}
