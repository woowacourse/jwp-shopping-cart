package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;
import woowacourse.shoppingcart.domain.PasswordConvertor;
import woowacourse.shoppingcart.exception.InvalidPasswordLengthException;

public class Password {

    public static final int MIN_RAW_VALUE_LENGTH = 8;
    private static final int MAX_RAW_VALUE_LENGTH = 255;

    private final String hashedValue;

    private Password(final String hashedValue) {
        this.hashedValue = hashedValue;
    }

    public static Password fromRawValue(final String rawValue, final PasswordConvertor passwordConvertor) {
        validateRawValue(rawValue);
        return new Password(passwordConvertor.encode(rawValue));
    }

    private static void validateRawValue(final String rawValue) {
        Objects.requireNonNull(rawValue);
        if (rawValue.length() < MIN_RAW_VALUE_LENGTH || rawValue.length() > MAX_RAW_VALUE_LENGTH) {
            throw new InvalidPasswordLengthException();
        }
    }

    public static Password fromHashedValue(final String hashedValue) {
        return new Password(hashedValue);
    }

    public boolean isSamePassword(final String rawValue, final PasswordConvertor passwordConvertor) {
        return passwordConvertor.isSamePassword(rawValue, this.hashedValue);
    }

    public String getHashedValue() {
        return hashedValue;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Password password = (Password) o;
        return Objects.equals(hashedValue, password.hashedValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hashedValue);
    }
}
