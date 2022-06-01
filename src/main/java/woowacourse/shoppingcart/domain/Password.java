package woowacourse.shoppingcart.domain;

import java.util.Objects;
import woowacourse.shoppingcart.util.PasswordEncryptor;

public class Password {

    private final String hashedValue;

    private Password(final String hashedValue) {
        this.hashedValue = hashedValue;
    }

    public static Password fromRawValue(final String rawValue) {
        return new Password(PasswordEncryptor.encrypt(rawValue));
    }

    public static Password fromHashedValue(final String hashedValue) {
        return new Password(hashedValue);
    }

    public boolean isSamePassword(final String rawValue) {
        return PasswordEncryptor.checkPassword(rawValue, hashedValue);
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
