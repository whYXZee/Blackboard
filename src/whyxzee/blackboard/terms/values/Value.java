package whyxzee.blackboard.terms.values;

import whyxzee.blackboard.utils.ArithmeticUtils;

public class Value {
    /* Values */
    private double decValue;
    private int wholeNum;
    private int numerator;
    private int denominator;

    public Value(int wholeNum, int numerator, int denominator) {
        /* Values */
        this.wholeNum = wholeNum;
        this.numerator = numerator;
        this.denominator = denominator;
        this.decValue = wholeNum + ((double) numerator / denominator);
    }

    public Value(double value) {
        /* Values */
        this.decValue = value;
        this.wholeNum = (int) Math.floor(value);

        /* Decimal to Fraction */
        double tempDoubleVal = value - wholeNum;
        String stringVal = Double.toString(tempDoubleVal);
        int decPlaces = 0;
        boolean shouldCount = false;
        for (char i : stringVal.toCharArray()) {
            if (shouldCount) {
                decPlaces++;
            } else {
                if (i == '.') {
                    shouldCount = true;
                }
            }
        }
        Value tempVal = ArithmeticUtils.simplifyFraction((int) (tempDoubleVal * Math.pow(10, decPlaces)),
                (int) Math.pow(10, decPlaces));

        this.numerator = tempVal.getNumerator();
        this.denominator = tempVal.getDenominator();

    }

    //
    // Get and Set Methods
    //
    public double getDecimal() {
        return decValue;
    }

    public int getWholeNum() {
        return wholeNum;
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }
}
