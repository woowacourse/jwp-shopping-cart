package woowacourse.auth.domain.user;

import java.util.Objects;

public class DetailAddress {
    private static final DetailAddress EMPTY = new DetailAddress("");

    private final String value;

    private DetailAddress(String value) {
        this.value = value;
    }

    public static DetailAddress from(String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            return EMPTY;
        }

        return new DetailAddress(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DetailAddress that = (DetailAddress) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "DetailAddress{" +
                "value='" + value + '\'' +
                '}';
    }
}
