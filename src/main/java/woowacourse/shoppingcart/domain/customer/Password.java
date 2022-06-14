package woowacourse.shoppingcart.domain.customer;

import woowacourse.shoppingcart.exception.PasswordMisMatchException;

public class Password {

    private final String value;

    private Password(String value) {
        this.value = value;
    }

    public static Password from(String value) {
        return new Password(value);
    }

    public void match(String password) {
        if (!this.equals(Password.from(password))) {
            throw new PasswordMisMatchException();
        }
    }

    public Password update(String password) {
        return Password.from(password);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Password password = (Password)o;

        return getValue() != null ? getValue().equals(password.getValue()) : password.getValue() == null;
    }

    @Override
    public int hashCode() {
        return getValue() != null ? getValue().hashCode() : 0;
    }
}
