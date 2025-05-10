package whyxzee.blackboard;

import java.awt.Font;

public final class Constants {
    public static final class Unicode {
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
        public static final char SUPERSCRIPT_SLASH = '\u141F';
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
        public static final int[] PRIME_NUMBERS = {
                2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31
        };
    }
}