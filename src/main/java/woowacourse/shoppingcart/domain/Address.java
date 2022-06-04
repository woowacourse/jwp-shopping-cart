package woowacourse.shoppingcart.domain;

public class Address {

    public static final int ADDRESS_LENGTH_LIMIT = 255;

    private final String value;

    public Address(String address) {
        validateAddressLength(address);
        this.value = address;
    }

    private void validateAddressLength(String address) {
        if (address.length() > ADDRESS_LENGTH_LIMIT) {
            throw new IllegalArgumentException("주소 길이는 255자를 초과할 수 없습니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
