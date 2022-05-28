package woowacourse.auth.domain.user;

import java.util.Objects;
import woowacourse.auth.exception.InvalidAddressFormatException;

public class Address {
    private final String value;

    public Address(String value) {
        validateFormat(value);
        this.value = value;
    }

    private void validateFormat(String value) {
        if (value.isEmpty()) {
            throw new InvalidAddressFormatException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(value, address.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Address{" +
                "value='" + value + '\'' +
                '}';
    }
}
