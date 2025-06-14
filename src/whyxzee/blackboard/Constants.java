package whyxzee.blackboard;

import java.awt.Font;

public final class Constants {
    public static final class Unicode {
        /* Superscript */
        public static final char SUPERSCRIPT_X = '\u02E3';
        public static final char SUPERSCRIPT_0 = '\u2070';
        public static final char SUPERSCRIPT_1 = '\u00B9';
        public static final char SUPERSCRIPT_2 = '\u00B2';
        public static final char SUPERSCRIPT_3 = '\u00B3';
        public static final char SUPERSCRIPT_4 = '\u2074';
        public static final char SUPERSCRIPT_5 = '\u2075';
        public static final char SUPERSCRIPT_6 = '\u2076';
        public static final char SUPERSCRIPT_7 = '\u2077';
        public static final char SUPERSCRIPT_8 = '\u2078';
        public static final char SUPERSCRIPT_9 = '\u2079';
        public static final char SUPERSCRIPT_DASH = '\u207B';
        // public static final char SUPERSCRIPT_SLASH = '\u141F';
        public static final char SUPERSCRIPT_SLASH = '\u2E0D';

        /* Subscript */
        public static final char SUBSCRIPT_X = '\u2093';
        public static final char SUBSCRIPT_0 = '\u2080';
        public static final char SUBSCRIPT_1 = '\u2081';
        public static final char SUBSCRIPT_2 = '\u2082';
        public static final char SUBSCRIPT_3 = '\u2083';
        public static final char SUBSCRIPT_4 = '\u2084';
        public static final char SUBSCRIPT_5 = '\u2085';
        public static final char SUBSCRIPT_6 = '\u2086';
        public static final char SUBSCRIPT_7 = '\u2087';
        public static final char SUBSCRIPT_8 = '\u2088';
        public static final char SUBSCRIPT_9 = '\u2089';
        public static final char SUBSCRIPT_DASH = '\u208B';
        public static final char SUBSCRIPT_DOT = '\u05C5';
        public static final char SUBSCRIPT_SLASH = '\u2E1D';

        /* Set Theory Characters */
        public static final char ELEMENT_OF = '\u2208';
        public static final char NOT_ELEMENT_OF = '\u2209';
        public static final char UNION = '\u222A';
        public static final char SET_SUCH_THAT = '|';
        public static final char NULL_SET = '\u2205';
        public static final String NATURAL_SET = "\u2115";
        public static final String INTEGER_SET = "\u2124";
        public static final String RATIONAL_SET = "\u211A";
        public static final String REAL_SET = "\u211D";
        public static final String IMAGINARY_SET = "\u2148";
        public static final String COMPLEX_SET = "\u2102";

        /* Inequality Characters */
        public static final char LESS_THAN = '<';
        public static final char LESS_THAN_EQUAL = '\u2264';
        public static final char GREATER_THAN = '>';
        public static final char GREATER_THAN_EQUAL = '\u2265';
        public static final char EQUAL = '=';
        public static final char NOT_EQUAL = '\u2260';

        /* Greek characters */
        public static final char UPPERCASE_SIGMA = '\u03A3';
        public static final char LOWERCASE_PI = '\u03C0';

        /* Infinity */
        public static final String INFINITY = "\u221E";
        public static final String ALEPH = "\u2135";

        /* General characters */
        public static final char FRACTION_SLASH = '\u2044';
        public static final char IMAGINARY_NUMBER = 'i';
        public static final char INTEGRAL = '\u222B';
        public static final char PLUS_MINUS = '\u00B1';

        /* Random characters */
        public static final char STOPWATCH = '\u23F1';
    }

    public static final class DisplayConstants {
        /* Font */
        public static final double PRINT_WIDTH_PERCENT = 1.0;
        public static final double PRINT_HEIGHT_PERCENT = 1.0;
        public static final double SUP_WIDTH_PERCENT = 1.0;
        public static final double SUP_HEIGHT_PERCENT = 1.0;
        public static final double SUB_WIDTH_PERCENT = 1.0;
        public static final double SUB_HEIGHT_PERCENT = 1.0;

        public static final String FONT_NAME = "Noto Sans";
        public static final int FONT_STYLE = Font.PLAIN;
    }

    public static final class NumberConstants {
        public static final String DEFAULT_VAR = "x";
        public static final int[] PRIME_NUMBERS = {
                2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31
        };
        public static final int MAX_PRIME_NUMBER = 17;
        public static final int SIG_FIGS = 4;

        /* Complex Numbers */
        public static final double NEG_ONE_TO_I = 0.0432139186;
    }

    public static final class TelemetryConstants {
        /* Arithmetic */
        public static final boolean SOLVE_FOR_TELEMETRY = true;

        /* Equations */
        public static final boolean SEQ_FUN_TELEMETRY = false;

        /* Terms */
        public static final boolean POW_TERM_TELEMETRY = false;

    }
}