package cart.domain;

import java.util.regex.Pattern;

public class Email {

    private static final Pattern emailPatter
            = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private final String email;

    private Email(final String email) {
        validate(email);
        this.email = email;
    }

    public static Email email(final String email) {
        return new Email(email);
    }

    private void validate(final String email) {
        if (!emailPatter.matcher(email).matches()) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }
    }

    public String getEmail() {
        return email;
    }
}
