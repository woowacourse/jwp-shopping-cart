package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;
import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.InvalidEmailFormatException;

public class Email {

    private static final String EMAIL_FORMAT_REGEX =
            "^(?=.{1,20}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private final String value;

    public Email(final String value) {
        validateFormat(value);
        this.value = value;
    }

    private void validateFormat(final String value) {
        if (!Pattern.matches(EMAIL_FORMAT_REGEX, value)) {
            throw new InvalidEmailFormatException();
        }
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
