package cart.controller.dto;

import static java.lang.System.lineSeparator;

import java.util.regex.Pattern;

public class PasswordRequestValidator {

    private static final String PASSWORD_REGAX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()])[A-Za-z\\d!@#$%^&*()]{10,}$";

    public static void validate(String password) {
        isNullOrBlank(password);
        isMatchWithPattern(password);
    }

    private static void isNullOrBlank(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호 입력이 비어있습니다.");
        }
    }

    private static void isMatchWithPattern(String password) {
        boolean isValid = Pattern.matches(PASSWORD_REGAX, password);
        if (!isValid) {
            throw new IllegalArgumentException("비밀번호 형식이 잘못되었습니다." + lineSeparator()
                    + "입력된 비밀번호: " + password);
        }
    }
}
