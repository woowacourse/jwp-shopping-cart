package woowacourse.shoppingcart.domain.customer;

public class EncodedPassword {

    private final String value;

    public EncodedPassword(String value) {
        this.value = value;
    }

    public boolean isSamePassword(EncodedPassword otherPassword) {
        return value.equals(otherPassword.value);
    }

    public String getValue() {
        return value;
    }
}
