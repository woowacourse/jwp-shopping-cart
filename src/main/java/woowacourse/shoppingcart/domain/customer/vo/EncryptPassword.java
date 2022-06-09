package woowacourse.shoppingcart.domain.customer.vo;

import java.util.Objects;

public class EncryptPassword {

    private final String value;

    public EncryptPassword(String value) {
        this.value = value;
    }

    public EncryptPassword(Password password) {
        this(password.getValue());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EncryptPassword)) {
            return false;
        }
        EncryptPassword password = (EncryptPassword) o;
        return Objects.equals(value, password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
