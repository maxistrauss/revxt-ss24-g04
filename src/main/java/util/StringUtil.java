package util;

public class StringUtil {

    private static String trimString(String string, int maxLength) {
        if (string.length() > maxLength) {
            return string.substring(0, maxLength - 3) + "...";
        }
        return string;
    }

    public static String fillString(String string, int length) {
        string = trimString(string, length);
        return string + " ".repeat(length - string.length());
    }

}
