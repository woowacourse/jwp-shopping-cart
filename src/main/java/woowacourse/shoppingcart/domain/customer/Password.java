package woowacourse.shoppingcart.domain.customer;

public class Password {

    private final String value;

    public Password(final String password) {
        this.value = password;
    }

    public String getValue() {
        return value;
    }
}
