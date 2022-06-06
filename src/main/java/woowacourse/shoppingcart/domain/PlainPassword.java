package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.domain.exception.InvalidPasswordLengthException;
import woowacourse.shoppingcart.util.PasswordEncryptor;
import java.util.Objects;

public class PlainPassword {

    public static final int MIN_RAW_VALUE_LENGTH = 8;

    private final String rawValue;

    public PlainPassword(final String rawValue) {
        validateRawValue(rawValue);
        this.rawValue = rawValue;
    }

    private static void validateRawValue(final String rawValue) {
        Objects.requireNonNull(rawValue);
        if (rawValue.length() < MIN_RAW_VALUE_LENGTH) {
            throw new InvalidPasswordLengthException();
        }
    }

    public String encode() {
        return PasswordEncryptor.encrypt(rawValue);
    }
}
