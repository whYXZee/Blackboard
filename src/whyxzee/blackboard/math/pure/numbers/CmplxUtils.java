package whyxzee.blackboard.math.pure.numbers;

import whyxzee.blackboard.math.pure.numbers.Complex.ComplexType;
import whyxzee.blackboard.math.utils.pure.NumberUtils;

public class CmplxUtils {
    /**
     * 
     * Applies the nth real power to a complex number.
     * 
     * 
     * @param power A real number. Cannot be Double.POSITIVE_INFINITY,
     *              Double.NEGATIVE_INFINITY, nor Double.NaN.
     * @return
     */
    private static final Complex power(Complex base, double power) {
        /* Reciprocate if needed */
        if (power < 0) {
            base.reciprocal();
            power *= -1;
        }

        BNum a = base.getA(), b = base.getB();
        if (base.isReal()) {
            if (!base.isANegative()) {
                // positive a so no problems :D
                a.power(power);
                base.calcPolar();
                return base;
            }

            if (!NumberTheory.isRational(power)) {
                // if the power is irrational
                return Complex.fromPolar(ComplexType.COMPLEX, Math.pow(-a.getValue(), power),
                        (Math.PI * power));
            }

            if (!NumberUtils.inOpenRange(power, 0, 1)) {
                // imaginary numbers only crop up if its in (0,1)
                a.power(power);
                base.calcPolar();
                return base;
            }

            int[] ratio = NumberTheory.findRatio(power);
            if (ratio[1] % 2 == 1) {
                // negative base doesn't work for some reason
                base.setA(-Math.pow(-a.getValue(), power));
                base.calcPolar();
                return base;

            } else if (ratio[1] == 2) {
                base.setB(Math.pow(-a.getValue(), power));
                base.setA(0);
                base.calcPolar();
                return base;
            }

            return Complex.fromPolar(ComplexType.COMPLEX, Math.pow(-a.getValue(), power), Math.PI * power);

        } else if (base.isImaginary() && NumberTheory.isInteger(power)) {
            // multiplying by an imaginary number is rotating the number by PI / 2 rads on
            // the imaginary-real axis.

            if (power % 4 == 0) {
                // double check this
                base.setA(Math.pow(b.getValue(), power));
                base.setB(0);

            } else if (power % 2 == 0) {
                base.setA(-Math.pow(b.getValue(), power));
                base.setB(0);

            } else if (power % 4 == 3) {
                base.setA(0);
                base.setB(-Math.pow(b.getValue(), power));

            } else {
                b.power(power);
            }
            base.calcPolar();
            return base;

        } else {
            // imaginary with non-int power / complex

            // DeMoivre's theorem -> rational or irrational, int power or non
            // int power
            return Complex.fromPolar(ComplexType.COMPLEX, BNum.fromObj(base.getModulus()).power(power),
                    base.getTheta() * power);
        }
    }

    // TODO: roots of a complex number

    /**
     * Gets the complex number of a real base to a real, imaginary, or complex
     * power. The following are implemented:
     * <ul>
     * <li>Uncountable power
     * <li>Irrational powers
     * <li>DNE
     * </ul>
     * 
     * <p>
     * <em>Calling pow() does not affect the data of <b>power</b></em>.
     * 
     * @param base
     * @param power
     * @return
     */
    private static final Complex pow(double base, Complex power) {
        /* DNE */
        if (power.isDNE()) {
            return power;
        }

        /* Infinity Power */
        if (power.hasInfinity()) {
            return NumberUtils.powWithInf(base, power);
        }

        /* Complex Power */
        double aPow = power.getA().getValue(), bPow = power.getB().getValue();
        double aOut, bOut;
        if (power.isReal()) {
            return power(new Complex(base, 0, ComplexType.COMPLEX), power.getA().getValue());

        } else if (power.isImaginary()) {
            if (base < 0) {
                // negative base
                Complex output = pow(-base, power);
                if (bPow % 2 == 0) {
                    // (-1)^2i = 1^i = 1
                    return output;
                } else if (bPow % 2 == 1) {
                    // (-1)^3i = (-1)^i = e^(-pi)
                    output.multiplyScalar(Complex.NEG_ONE_TO_I);
                    return output;
                } else {
                    // (-1)^ni = (-1^n)^i
                    Complex negOneToPow = power(Complex.cmplx(Complex.NEG_ONE_TO_I, 0), bPow);
                    // has to be (-1^i)^b -> (e^-pi)^b

                    negOneToPow = pow(negOneToPow, Complex.cmplx(0, 1));
                    return output.multiply(negOneToPow);
                }

            } else {
                aOut = Math.cos(bPow * Math.log(base));
                bOut = Math.sin(bPow * Math.log(base));
                return Complex.cmplx(aOut, bOut);
            }

        } else {
            // complex power
            if (base < 0) {
                // negative base
                Complex output = pow(-base, power);
                Complex negOneA = Complex.negOneToPower(aPow);
                Complex negOneB = power(Complex.cmplx(Complex.NEG_ONE_TO_I, 0), bPow);

                return output.multiply(negOneA, negOneB);

            } else {
                double tTheta = bPow * Math.log(base); // temp theta
                double tModulus = Math.exp(aPow * Math.log(base));
                System.out.println(
                        base + " to " + power + " is " + Complex.fromPolar(ComplexType.COMPLEX, tModulus, tTheta));
                return Complex.fromPolar(ComplexType.COMPLEX, tModulus, tTheta);
            }
        }
    }

    /**
     * Gets the complex number of a real, imaginary, or complex base to a real,
     * imaginary, or complex power. The following are implemented:
     * <ul>
     * <li>Uncountable base and/or uncountable power
     * <li>Irrational numbers
     * <li>DNE
     * </ul>
     * 
     * @param base
     * @param power
     * @return
     */
    public static final Complex pow(Complex base, Complex power) {
        /* DNE */
        if (base.isDNE() || power.isDNE()) {
            return Complex.DNE(ComplexType.COMPLEX);
        }

        /* Uncountable */
        if (power.hasInfinity() && base.isReal()) {
            return NumberUtils.powWithInf(base.getA().getValue(), power);

        } else if (power.hasInfinity()) {
            return Complex.DNE(ComplexType.COMPLEX);

        } else if (base.hasInfinity()) {
            if (power.hasInfinity()) {
                // cis(r2 * theta1 * cos(theta2)) = cis(theta1 * a2) = DNE
                // r1^(a+bi), b cannot be infinity
                return Complex.DNE(ComplexType.COMPLEX);
            }

            /**
             * e^(-r2 * theta1 * sin(theta2)) = e^(-theta1 * b2)
             */
            // double expMod = Math.exp(-power.getModulus() * base.getTheta() *
            // Math.sin(power.getTheta()));
            // always positive lol

            /**
             * r2 * theta1 * cos(theta2) = theta1 * a2
             */
            double theta = BNum.statMultiply(base.getTheta(), power.getA()).getValue();
            return Complex.fromPolar(ComplexType.COMPLEX, BNum.infinity(false, 1), theta);
        }

        /* Complex */
        if (base.isReal()) {
            return pow(base.getA().getValue(), power);

        } else if (power.isReal()) {
            return power(base, power.getA().getValue());

        } else {
            // (complex or imaginary) ^ (complex or imaginary)
            Complex modOneToPow = pow(BNum.fromObj(base.getModulus()).getValue(), power);
            /**
             * e^(-theta1 * r2 * sin(theta2))
             */
            double cisMod = Math.exp(-1 * power.getB().getValue() * base.getTheta());

            /**
             * r2 * cos(theta2) * theta 1
             */
            double cisTheta = BNum.statMultiply(base.getTheta(), power.getA()).getValue();
            Complex cisToPow = Complex.fromPolar(ComplexType.COMPLEX, cisMod, cisTheta);
            return cisToPow.multiply(modOneToPow);
        }
    }
    // #endregion

}
