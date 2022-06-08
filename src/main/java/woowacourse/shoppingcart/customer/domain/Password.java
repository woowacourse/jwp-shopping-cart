package woowacourse.shoppingcart.customer.domain;

import java.util.Objects;
import java.util.regex.Pattern;

import woowacourse.shoppingcart.customer.support.exception.CustomerException;
import woowacourse.shoppingcart.customer.support.exception.CustomerExceptionCode;

public class Password {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{10,}");

    private final String value;

    public Password(final String value) {
        validatePassword(value);
        this.value = value;
    }

    private void validatePassword(final String value) {
        if (isPasswordOutOfForm(value)) {
            throw new CustomerException(CustomerExceptionCode.INVALID_FORMAT_PASSWORD);
        }
    }

    private boolean isPasswordOutOfForm(final String value) {
        return !PASSWORD_PATTERN.matcher(value).matches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Password password = (Password) o;
        return value.equals(password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public String get() {
        return value;
    }
}
