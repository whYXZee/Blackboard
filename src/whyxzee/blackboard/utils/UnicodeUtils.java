package whyxzee.blackboard.utils;

import whyxzee.blackboard.Constants;

public class UnicodeUtils {
    // #region General
    /**
     * Turns one individual superscript char or subscript char into a number
     * character.
     * 
     * @param input
     * @return
     */
    private static final char scriptToNum(char input) {
        switch (input) {
            case Constants.Unicode.SUPERSCRIPT_0:
            case Constants.Unicode.SUBSCRIPT_0:
                return '0';

            case Constants.Unicode.SUPERSCRIPT_1:
            case Constants.Unicode.SUBSCRIPT_1:
                return '1';

            case Constants.Unicode.SUPERSCRIPT_2:
            case Constants.Unicode.SUBSCRIPT_2:
                return '2';

            case Constants.Unicode.SUPERSCRIPT_3:
            case Constants.Unicode.SUBSCRIPT_3:
                return '3';

            case Constants.Unicode.SUPERSCRIPT_4:
            case Constants.Unicode.SUBSCRIPT_4:
                return '4';

            case Constants.Unicode.SUPERSCRIPT_5:
            case Constants.Unicode.SUBSCRIPT_5:
                return '5';

            case Constants.Unicode.SUPERSCRIPT_6:
            case Constants.Unicode.SUBSCRIPT_6:
                return '6';

            case Constants.Unicode.SUPERSCRIPT_7:
            case Constants.Unicode.SUBSCRIPT_7:
                return '7';

            case Constants.Unicode.SUPERSCRIPT_8:
            case Constants.Unicode.SUBSCRIPT_8:
                return '8';

            case Constants.Unicode.SUPERSCRIPT_9:
            case Constants.Unicode.SUBSCRIPT_9:
                return '9';

            case Constants.Unicode.SUPERSCRIPT_DASH:
            case Constants.Unicode.SUBSCRIPT_DASH:
                return '-';

            case Constants.Unicode.SUPERSCRIPT_DOT:
            case Constants.Unicode.SUBSCRIPT_DOT:
                return '.';

            case Constants.Unicode.SUPERSCRIPT_SLASH:
            case Constants.Unicode.SUBSCRIPT_SLASH:
                return '/';

            default:
                return ' ';
        }
    }

    /**
     * Turns a superscript/subscript String into a number String.
     * 
     * @param input
     * @return
     */
    public static final String scriptToNum(String input) {
        String output = "";
        for (char i : input.toCharArray()) {
            output += scriptToNum(i);
        }
        return output;
    }

    // #endregion

    // #region Superscript
    /**
     * Turns one number character into a superscript character.
     * 
     * @param input
     * @return
     */
    private static final char supscriptfromNum(char input) {
        switch (input) {
            case '0':
                return Constants.Unicode.SUPERSCRIPT_0;
            case '1':
                return Constants.Unicode.SUPERSCRIPT_1;
            case '2':
                return Constants.Unicode.SUPERSCRIPT_2;
            case '3':
                return Constants.Unicode.SUPERSCRIPT_3;
            case '4':
                return Constants.Unicode.SUPERSCRIPT_4;
            case '5':
                return Constants.Unicode.SUPERSCRIPT_5;
            case '6':
                return Constants.Unicode.SUPERSCRIPT_6;
            case '7':
                return Constants.Unicode.SUPERSCRIPT_7;
            case '8':
                return Constants.Unicode.SUPERSCRIPT_8;
            case '9':
                return Constants.Unicode.SUPERSCRIPT_9;
            case '-':
                return Constants.Unicode.SUPERSCRIPT_DASH;
            case '.':
                return Constants.Unicode.SUPERSCRIPT_DOT;
            default:
                return ' ';
        }
    }

    /**
     * Turns an integer value into a String power.
     * 
     * @param input an int value
     * @return
     */
    public static final String intToSuperscript(int input) {
        String output = "";
        for (char i : Integer.toString(input).toCharArray()) {
            output += supscriptfromNum(i);
        }
        return output;
    }

    /**
     * Turns a double input into a String superscript.
     * 
     * @param input
     * @return
     */
    public static final String doubleToSuperscript(double input) {
        String output = "";
        for (char i : Double.toString(input).toCharArray()) {
            output += supscriptfromNum(i);
        }
        return output;
    }
    // #endregion

    // #region Subscript
    /**
     * Turns one number character into a subscript character.
     * 
     * @param input
     * @return
     */
    private static final char subscriptFromNum(char input) {
        switch (input) {
            case '0':
                return Constants.Unicode.SUBSCRIPT_0;
            case '1':
                return Constants.Unicode.SUBSCRIPT_1;
            case '2':
                return Constants.Unicode.SUBSCRIPT_2;
            case '3':
                return Constants.Unicode.SUBSCRIPT_3;
            case '4':
                return Constants.Unicode.SUBSCRIPT_4;
            case '5':
                return Constants.Unicode.SUBSCRIPT_5;
            case '6':
                return Constants.Unicode.SUBSCRIPT_6;
            case '7':
                return Constants.Unicode.SUBSCRIPT_7;
            case '8':
                return Constants.Unicode.SUBSCRIPT_8;
            case '9':
                return Constants.Unicode.SUBSCRIPT_9;
            case '-':
                return Constants.Unicode.SUBSCRIPT_DASH;
            case '.':
                return Constants.Unicode.SUBSCRIPT_DOT;
            default:
                return ' ';
        }
    }

    /**
     * 
     * @param input
     * @return
     */
    public static final String intToSubscript(int input) {
        String output = "";
        for (char i : Integer.toString(input).toCharArray()) {
            output += subscriptFromNum(i);
        }
        return output;
    }

    /**
     * Turns a double input into a String subscript.
     * 
     * @param input
     * @return
     */
    public static final String doubleToSubscript(double input) {
        String output = "";
        for (char i : Double.toString(input).toCharArray()) {
            output += subscriptFromNum(i);
        }
        return output;
    }
    // #endregion
}
