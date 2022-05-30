package woowacourse.auth.domain;

import java.util.Objects;

public class Address {

    private final String value;

    public Address(String value) {
        value = value.trim();
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (value.length() > 255) {
            throw new IllegalArgumentException(
                    String.format("주소는 최대 255자까지 가능합니다. 입력값 : %s", value));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(value, address.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
