package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.util.PasswordEncryptor;
import java.util.Objects;

public class EncodedPassword {

    private final String hashedValue;

    public EncodedPassword(final String hashedValue) {
        validateHashed(hashedValue);
        this.hashedValue = hashedValue;
    }

    public boolean isSamePassword(final String rawValue) {
        return PasswordEncryptor.checkPassword(rawValue, hashedValue);
    }

    private static void validateHashed(final String value) {
        if (!PasswordEncryptor.isHashed(value)) {
            throw new IllegalArgumentException("암호화된 비밀번호가 입력되어야 합니다.");
        }
    }

    public String getHashedValue() {
        return hashedValue;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof EncodedPassword)) return false;
        final EncodedPassword that = (EncodedPassword) o;
        return Objects.equals(hashedValue, that.hashedValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hashedValue);
    }
}
