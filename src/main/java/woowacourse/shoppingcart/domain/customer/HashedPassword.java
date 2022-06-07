package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;
import woowacourse.shoppingcart.domain.PasswordConvertor;
import woowacourse.shoppingcart.util.BcryptConvertor;

public class HashedPassword {

    private static final PasswordConvertor passwordConvertor = new BcryptConvertor();

    private final String hashedValue;

    public HashedPassword(final String hashedValue) {
        this.hashedValue = hashedValue;
    }

    public static HashedPassword from(RawPassword rawPassword) {
        return new HashedPassword(rawPassword.hashValue(passwordConvertor));
    }

    public boolean isSameFrom(String passwordValue) {
        return passwordConvertor.isSamePassword(passwordValue, hashedValue);
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
        final HashedPassword that = (HashedPassword) o;
        return Objects.equals(hashedValue, that.hashedValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hashedValue);
    }
}
