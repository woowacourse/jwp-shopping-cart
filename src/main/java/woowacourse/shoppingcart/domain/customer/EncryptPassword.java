package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;

public class EncryptPassword {

    private final String value;

    public EncryptPassword(final String value) {
        this.value = value;
    }

    public boolean matches(final PasswordEncryptor passwordEncryptor, final String plainPassword) {
        return passwordEncryptor.matches(plainPassword, value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EncryptPassword that = (EncryptPassword) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
