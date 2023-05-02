package cart.domain.member;

import java.util.regex.Pattern;

public class Email {

    private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");

    private final String email;

    public Email(final String email) {
        validate(email);

        this.email = email;
    }

    private void validate(final String email) {
        if (!PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다");
        }
    }

    public String getEmail() {
        return email;
    }
}
