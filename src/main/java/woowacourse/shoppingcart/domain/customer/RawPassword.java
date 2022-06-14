package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;
import woowacourse.shoppingcart.domain.PasswordConvertor;
import woowacourse.shoppingcart.exception.InvalidPasswordLengthException;

public class RawPassword {

    public static final int MIN_RAW_VALUE_LENGTH = 8;
    private static final int MAX_RAW_VALUE_LENGTH = 15;

    private final String rawValue;

    public RawPassword(final String rawValue) {
        validateRawValue(rawValue);
        this.rawValue = rawValue;
    }

    private static void validateRawValue(final String rawValue) {
        Objects.requireNonNull(rawValue);
        if (rawValue.length() < MIN_RAW_VALUE_LENGTH || rawValue.length() > MAX_RAW_VALUE_LENGTH) {
            throw new InvalidPasswordLengthException();
        }
    }

    public String hashValue(PasswordConvertor passwordConvertor) {
        return passwordConvertor.encode(this.rawValue);
    }
}
