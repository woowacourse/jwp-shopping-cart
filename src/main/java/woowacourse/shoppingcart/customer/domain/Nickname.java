package woowacourse.shoppingcart.customer.domain;

import java.util.Objects;
import woowacourse.shoppingcart.customer.exception.badrequest.InvalidNicknameException;

public class Nickname {

    private final String value;

    public Nickname(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (!value.matches("[a-zA-Z0-9가-힣]{2,8}")) {
            throw new InvalidNicknameException();
        }
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
        final Nickname nickname = (Nickname) o;
        return Objects.equals(value, nickname.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Nickname{" +
                "value='" + value + '\'' +
                '}';
    }
}
