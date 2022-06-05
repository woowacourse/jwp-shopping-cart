package woowacourse.customer.domain;

import java.util.Objects;

import woowacourse.shoppingcart.exception.PasswordMisMatchException;
import woowacourse.customer.support.passwordencoder.PasswordEncoder;

public class EncodedPassword {

    private final String value;

    public EncodedPassword(final String value) {
        this.value = value;
    }

    public void matches(final PasswordEncoder passwordEncoder, final String raw) {
        if (!passwordEncoder.matches(raw, value)) {
            throw new PasswordMisMatchException();
        }
    }

    public EncodedPassword update(final String value) {
        return new EncodedPassword(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        final EncodedPassword that = (EncodedPassword)o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
