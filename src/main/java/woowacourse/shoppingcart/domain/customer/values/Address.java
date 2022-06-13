package woowacourse.shoppingcart.domain.customer.values;

import java.util.Objects;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

public class Address {

    private final String address;

    public Address(final String address) {
        validateLength(address);
        this.address = address;
    }

    private void validateLength(final String address) {
        if (address.trim().length() == 0 || address.length() > 80) {
            throw new IllegalArgumentException("주소는 1자 이상, 80자 이하여야 합니다.");
        }
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Address address1 = (Address) o;
        return Objects.equals(address, address1.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}
