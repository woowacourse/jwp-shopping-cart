package cart.domain;

import java.util.regex.Pattern;

public class Email {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-z0-9_]+@[a-z_]+\\.[a-z]{2,}$");

    private final String email;

    public Email(final String email) {
        validate(email);
        this.email = email;
    }

    private void validate(final String email) {
        if (EMAIL_PATTERN.matcher(email).matches()) {
            return;
        }
        throw new IllegalArgumentException("이메일은 숫자, 알파벳 소문자, _로만 구성할 수 있습니다.");
    }

    public String getEmail() {
        return email;
    }
}
