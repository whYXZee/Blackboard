package whyxzee.blackboard.utils;

public class UnicodeUtils {
    //
    // Parsing Methods
    //

    public static char superscriptToNum(char input) {
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

    /**
     * Transfers an integer value into a String power.
     * 
     * @param input an int value
     * @return
     */
    public static String intToSuperscript(int input) {
        String output = "";
        for (char i : Integer.toString(input).toCharArray()) {
            switch (i) {
                case '0':
                    output += '\u2070';
                    break;
                case '1':
                    output += '\u00B9';
                    break;
                case '2':
                    output += '\u00B2';
                    break;
                case '3':
                    output += '\u00B3';
                    break;
                case '4':
                    output += '\u2074';
                    break;
                case '5':
                    output += '\u2075';
                    break;
                case '6':
                    output += '\u2076';
                    break;
                case '7':
                    output += '\u2077';
                    break;
                case '8':
                    output += '\u2078';
                    break;
                case '9':
                    output += '\u2079';
                    break;
                case '-':
                    output += '\u207B';
                    break;
                default:
                    break;
            }
        }
        return output;
    }
}
