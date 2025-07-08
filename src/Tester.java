import java.math.BigInteger;

import whyxzee.blackboard.math.pure.numbers.BNum;

public class Tester {
    public static void main(String[] args) {
        // TermArray terms = new TermArray(
        // new PowerTerm(1, new Variable<String>("x")),
        // new PowerTerm(-15));

        // ExpansionAlg exp = new ExpansionAlg(new AdditiveEQ(terms));
        // System.out.println(exp.run());

        int nthTerm = 108;
        // BNum numerator = BNum.statAdd(BNum.goldenRatio(false).power(nthTerm),
        // BNum.goldenRatio(true).power(nthTerm).negate());
        System.out.println(nthTerm + "th term: "
                + BNum.goldenRatio(false).power(nthTerm).divide(new BNum(Math.sqrt(5))).toString());
        // doubles are innaccurate
    }

}
