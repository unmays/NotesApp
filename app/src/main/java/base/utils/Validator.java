package base.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private Pattern pattern;
    private Matcher matcher;

    public static final String ANDROID_EMAIL_PATTERN = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+";
    public static final String EMAIL_PATTERN =
            "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
    public static final String PEN_CARD_PATTERN = "[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}$";
    public static final String IFSC_PATTERN = "[A-Z|a-z]{4}[0][A-Z|a-z|0-9]{6}$";
    public static final String BANK_ACCOUNT_PATTERN = "";
    public static final String DOB_FORMAT = "^[0-3][0-9]-[0-1][0-9]-(?:[0-9][0-9])?[0-9][0-9]$";
    public static final String MOBILE_NUMBER = "[6-9]{1}[0-9]{9}";
    public static final String INTEGER_ONLY = "[0-9]+";
    public static final String INTEGER_OR_FLOAT = "[+-]?([0-9]*[.])?[0-9]+";

    public Validator(String rgx) {
        pattern = Pattern.compile(rgx);
    }

    /**
     * Validate hex with regular expression
     *
     * @return true valid hex, false invalid hex
     */
    public boolean validate(final String text) {

        matcher = pattern.matcher(text);
        return matcher.matches();

    }
}