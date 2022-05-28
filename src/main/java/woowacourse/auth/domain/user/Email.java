package woowacourse.auth.domain.user;

import java.util.Objects;
import woowacourse.auth.exception.format.InvalidEmailFormatException;

public class Email {
    private static final String EMAIL_REGEX = "[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.([a-zA-Z])+";

    private final String value;

    public Email(String value) {
        validateFormat(value);
        this.value = value;
    }

    private void validateFormat(String value) {
        if (Objects.isNull(value) || !value.matches(EMAIL_REGEX)) {
            throw new InvalidEmailFormatException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Email{" +
                "value='" + value + '\'' +
                '}';
    }
}
