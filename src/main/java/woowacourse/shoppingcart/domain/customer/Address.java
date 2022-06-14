package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;

public class Address {
    private final String address;

    public Address(final String address) {
        validateAddress(address);
        this.address = address;
    }

    public static void validateAddress(final String address) {
        if (address.isBlank()) {
            throw new IllegalArgumentException("올바르지 않은 주소 형식입니다.");
        }
    }

    public String getValue() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address1 = (Address) o;
        return Objects.equals(address, address1.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}
