package woowacourse.auth.domain;

import java.util.Objects;

public class Address {

    private final String value;

    public Address(String value) {
        this.value = value.trim();
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
