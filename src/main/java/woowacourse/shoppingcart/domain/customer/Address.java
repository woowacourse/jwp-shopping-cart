package woowacourse.shoppingcart.domain.customer;

public class Address {

    private final String address;

    public Address(String address) {
        this.address = address;
    }

    private static void validateAddress(String address) {
        if (address.isBlank()) {
            throw new IllegalArgumentException("올바르지 않은 주소 형식입니다.");
        }
    }

    public String getValue() {
        return address;
    }
}
