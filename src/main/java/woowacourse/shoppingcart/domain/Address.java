package woowacourse.shoppingcart.domain;

public class Address {

    private static final int ADDRESS_MAXIMUM_LENGTH = 255;

    private final String value;

    public Address(final String value) {
        validateAddress(value);
        this.value = value;
    }

    private void validateAddress(final String address) {
        validateBlank(address);
        validateLength(address);
    }

    private void validateBlank(String address) {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("주소는 비어있을 수 없습니다.");
        }
    }

    private void validateLength(String address) {
        if (address.length() > ADDRESS_MAXIMUM_LENGTH) {
            throw new IllegalArgumentException(String.format("주소 길이는 %d자를 초과할 수 없습니다.", ADDRESS_MAXIMUM_LENGTH));
        }
    }

    public String getValue() {
        return value;
    }
}
