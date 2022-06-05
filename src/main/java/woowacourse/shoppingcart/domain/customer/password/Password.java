package woowacourse.shoppingcart.domain.customer.password;

public class Password {

    private final String value;

    Password(String value) {
        this.value = value;
    }

    public boolean isSamePassword(final Password hashedPassword) {
        return this.value.equals(hashedPassword.value);
    }

    public String getValue() {
        return value;
    }
}
