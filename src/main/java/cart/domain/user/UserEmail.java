package cart.domain.user;

import java.util.Objects;
import java.util.regex.Pattern;

public class UserEmail {

    private static final int MIN_EMAIL_LENGTH = 1;
    private static final int MAX_EMAIL_LENGTH = 32;
    private static final String EMAIL_LENGTH_ERROR_MESSAGE = "이메일의 길이는 " + MIN_EMAIL_LENGTH + "자 이상 " + MAX_EMAIL_LENGTH + "자 이하입니다.";
    private static final String EMAIL_PATTERN_ERROR_MESSAGE = "이메일의 형식이 올바르지 않습니다.";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._]+@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)+$";
    private final String email;

    public UserEmail(final String email) {
        validate(email);
        this.email = email;
    }

    private void validate(final String email) {
        if (email == null) {
            throw new IllegalArgumentException(EMAIL_LENGTH_ERROR_MESSAGE);
        }
        if (email.length() < MIN_EMAIL_LENGTH || MAX_EMAIL_LENGTH < email.length()) {
            throw new IllegalArgumentException(EMAIL_LENGTH_ERROR_MESSAGE);
        }
        if (!Pattern.matches(EMAIL_REGEX, email)) {
            throw new IllegalArgumentException(EMAIL_PATTERN_ERROR_MESSAGE);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UserEmail userEmail = (UserEmail) o;
        return Objects.equals(email, userEmail.email);
    }

    public String getValue() {
        return email;
    }
}
