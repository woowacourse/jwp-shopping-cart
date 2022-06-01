package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;

public class Address {

    private final String value;

    public Address(String value) {
        validateAddress(value);
        this.value = value;
    }

    private void validateAddress(String value) {
        checkNull(value);
        checkLength(value);
    }

    private void checkNull(String value) {
        if (value == null) {
            throw new NullPointerException("address는 필수 입력 사항입니다.");
        }
    }

    private void checkLength(String value) {
        if (value.length() > 255) {
            throw new IllegalArgumentException("address 형식이 올바르지 않습니다. (길이: 255 이하)");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Address address = (Address)o;
        return Objects.equals(value, address.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
