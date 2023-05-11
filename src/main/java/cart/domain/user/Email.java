package cart.domain.user;

import cart.exception.GlobalException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {
    private static final int MAX_LENGTH = 30;
    private static final String EMAIL_FORM = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
    private static final Pattern EMAIL_FORM_PATTERN = Pattern.compile(EMAIL_FORM);

    private final String email;

    private Email(String email) {
        validate(email);
        this.email = email;
    }

    private void validate(String email) {
        validateForm(email);
        validateLength(email);
    }

    private void validateForm(String email) {
        Matcher emailMatcher = EMAIL_FORM_PATTERN.matcher(email);
        if (!emailMatcher.matches()) {
            throw new GlobalException("올바르지 않은 이메일 형식입니다.");
        }
    }

    private static void validateLength(String email) {
        if (email.isBlank()) {
            throw new GlobalException("이메일은 1글자 이상이어야 합니다.");
        }

        if (email.length() > MAX_LENGTH) {
            throw new GlobalException("이메일은 30글자 이하여야 합니다.");
        }
    }

    public static Email from(String email) {
        return new Email(email);
    }

    public String getEmail() {
        return email;
    }
}
