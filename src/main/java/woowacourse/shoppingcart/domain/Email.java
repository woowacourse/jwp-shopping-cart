package woowacourse.shoppingcart.domain;

import java.util.Objects;
import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.IllegalEmailException;

public class Email {

    private static final String REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    private final String value;

    public Email(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!Pattern.matches(REGEX, value)) {
            throw new IllegalEmailException();
        }
    }

    public String getValue() {
        return value;
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
}
