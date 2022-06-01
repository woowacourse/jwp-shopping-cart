package woowacourse.shoppingcart.domain;

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
}
