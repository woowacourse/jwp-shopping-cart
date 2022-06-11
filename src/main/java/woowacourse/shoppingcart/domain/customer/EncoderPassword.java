package woowacourse.shoppingcart.domain.customer;

public class EncoderPassword {

    private final String value;

    public EncoderPassword(String value) {
        this.value = value;
    }

    public boolean isSamePassword(String password) {
        return value.equals(password);
    }

    public String getValue() {
        return value;
    }
}
