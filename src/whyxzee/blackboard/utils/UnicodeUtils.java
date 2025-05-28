package whyxzee.blackboard.utils;

import whyxzee.blackboard.Constants;

public class UnicodeUtils {
    //
    // Parsing Methods
    //

    public static final char superscriptToNum(char input) {
        switch (input) {
            case '\u2070': // 0
                return '0';
            case '\u00B9': // 1
                return '1';
            case '\u00B2': // 2
                return '2';
            case '\u00B3': // 3
                return '3';
            case '\u2074': // 4
                return '4';
            case '\u2075': // 5
                return '5';
            case '\u2076': // 6
                return '6';
            case '\u2077': // 7
                return '7';
            case '\u2078': // 8
                return '8';
            case '\u2079': // 9
                return '9';
            case '\u207B': // -
                return '-';
            default:
                return '0';
        }
    }

    public static final String stringToNum(String input) {
        String output = "";
        for (char i : input.toCharArray()) {
            output += superscriptToNum(i);
        }

        return output;
    }

    /**
     * Transfers an integer value into a String power.
     * 
     * @param input an int value
     * @return
     */
    public static final String intToSuperscript(int input) {
        String output = "";
        for (char i : Integer.toString(input).toCharArray()) {
            switch (i) {
                case '0':
                    output += Constants.Unicode.SUPERSCRIPT_0;
                    break;
                case '1':
                    output += Constants.Unicode.SUPERSCRIPT_1;
                    break;
                case '2':
                    output += Constants.Unicode.SUPERSCRIPT_2;
                    break;
                case '3':
                    output += Constants.Unicode.SUPERSCRIPT_3;
                    break;
                case '4':
                    output += Constants.Unicode.SUPERSCRIPT_4;
                    break;
                case '5':
                    output += Constants.Unicode.SUPERSCRIPT_5;
                    break;
                case '6':
                    output += Constants.Unicode.SUPERSCRIPT_6;
                    break;
                case '7':
                    output += Constants.Unicode.SUPERSCRIPT_7;
                    break;
                case '8':
                    output += Constants.Unicode.SUPERSCRIPT_8;
                    break;
                case '9':
                    output += Constants.Unicode.SUPERSCRIPT_9;
                    break;
                case '-':
                    output += Constants.Unicode.SUPERSCRIPT_DASH;
                    break;
                default:
                    break;
            }
        }
        return output;
    }

    public static final String intToSubscript(int input) {
        String output = "";
        for (char i : Integer.toString(input).toCharArray()) {
            switch (i) {
                case '0':
                    output += Constants.Unicode.SUBSCRIPT_0;
                    break;
                case '1':
                    output += Constants.Unicode.SUBSCRIPT_1;
                    break;
                case '2':
                    output += Constants.Unicode.SUBSCRIPT_2;
                    break;
                case '3':
                    output += Constants.Unicode.SUBSCRIPT_3;
                    break;
                case '4':
                    output += Constants.Unicode.SUBSCRIPT_4;
                    break;
                case '5':
                    output += Constants.Unicode.SUBSCRIPT_5;
                    break;
                case '6':
                    output += Constants.Unicode.SUBSCRIPT_6;
                    break;
                case '7':
                    output += Constants.Unicode.SUBSCRIPT_7;
                    break;
                case '8':
                    output += Constants.Unicode.SUBSCRIPT_8;
                    break;
                case '9':
                    output += Constants.Unicode.SUBSCRIPT_9;
                    break;
                case '-':
                    output += Constants.Unicode.SUBSCRIPT_DASH;
                    break;
                default:
                    break;
            }
        }
        return output;
    }

    public static final String doubleToSubscript(double input) {
        String output = "";
        for (char i : Double.toString(input).toCharArray()) {
            switch (i) {
                case '0':
                    output += Constants.Unicode.SUBSCRIPT_0;
                    break;
                case '1':
                    output += Constants.Unicode.SUBSCRIPT_1;
                    break;
                case '2':
                    output += Constants.Unicode.SUBSCRIPT_2;
                    break;
                case '3':
                    output += Constants.Unicode.SUBSCRIPT_3;
                    break;
                case '4':
                    output += Constants.Unicode.SUBSCRIPT_4;
                    break;
                case '5':
                    output += Constants.Unicode.SUBSCRIPT_5;
                    break;
                case '6':
                    output += Constants.Unicode.SUBSCRIPT_6;
                    break;
                case '7':
                    output += Constants.Unicode.SUBSCRIPT_7;
                    break;
                case '8':
                    output += Constants.Unicode.SUBSCRIPT_8;
                    break;
                case '9':
                    output += Constants.Unicode.SUBSCRIPT_9;
                    break;
                case '-':
                    output += Constants.Unicode.SUBSCRIPT_DASH;
                    break;
                case '.':
                    output += Constants.Unicode.SUBSCRIPT_DOT;
                    break;
                default:
                    break;
            }
        }
        return output;
    }
}
