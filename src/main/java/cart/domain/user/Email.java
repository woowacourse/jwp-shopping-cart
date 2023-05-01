package cart.domain.user;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Email {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    private final String value;

    public Email(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String email) {
        final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        final Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return;
        }
        throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
