package br.com.alura.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class CommonsUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            return ((String) obj).isEmpty();
        }
        return false;
    }

    public static boolean containsNonLowerCaseCharacters(String username) {
        for (char c : username.toCharArray()) {
            if (!Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsDigits(String username) {
        for (char c : username.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsSpaces(String username) {
        return username.contains(" ");
    }

    public static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
