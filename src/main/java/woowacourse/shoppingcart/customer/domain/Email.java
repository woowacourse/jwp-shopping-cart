package woowacourse.shoppingcart.customer.domain;

import java.util.Objects;
import java.util.regex.Pattern;
import woowacourse.shoppingcart.customer.exception.badrequest.InvalidEmailException;

public class Email {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    private final String value;

    public Email(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new InvalidEmailException();
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

    @Override
    public String toString() {
        return "Email{" +
                "value='" + value + '\'' +
                '}';
    }
}
