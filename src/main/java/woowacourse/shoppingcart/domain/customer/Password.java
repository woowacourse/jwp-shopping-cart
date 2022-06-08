package woowacourse.shoppingcart.domain.customer;

public class Password {

    private final String value;

    public Password(String value) {
        this.value = value;
    }

    public boolean isValidPassword(String value) {
        return this.value.equals(value);
    }

    public String getValue() {
        return value;
    }
}
