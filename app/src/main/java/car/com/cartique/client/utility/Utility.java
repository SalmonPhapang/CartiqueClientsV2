package car.com.cartique.client.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    public static final String PREFS_NAME = "TurnupPrefs";
    public static final String hasloggedin = "hasloggedin";
    public static final String email = "email";
    public static final String username = "username";
    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static Pattern passwordPattern;
    private static Matcher passwordMatcher;
    private static Pattern pattern;
    private static Matcher matcher;

    public static boolean validate(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isNotNull(String txt) {
        return txt != null && txt.trim().length() > 0;
    }

    public static boolean validatePassword(final String password) {
        passwordPattern = Pattern.compile(PASSWORD_PATTERN);
        passwordMatcher = passwordPattern.matcher(password);
        return passwordMatcher.matches();

    }
}
