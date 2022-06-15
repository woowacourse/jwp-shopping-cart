package woowacourse.shoppingcart.domain.customer;

public class Address {

    private static final int LENGTH_LIMIT = 255;

    private final String value;

    public Address(String address) {
        validateAddressLength(address);
        this.value = address;
    }

    private void validateAddressLength(String address) {
        if (address.length() > LENGTH_LIMIT) {
            throw new IllegalArgumentException("주소 길이는 255자를 초과할 수 없습니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
