package woowacourse.shoppingcart.domain.customer.address;

import java.util.Objects;
import woowacourse.shoppingcart.exception.format.InvalidZonecodeFormatException;

public class Zonecode {
    private static final String ZONE_CODE_REGEX = "\\d{5}";
    private final String value;

    public Zonecode(String value) {
        validateFormat(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private void validateFormat(String value) {
        if (Objects.isNull(value) || !value.matches(ZONE_CODE_REGEX)) {
            throw new InvalidZonecodeFormatException();
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
        Zonecode zonecode = (Zonecode) o;
        return Objects.equals(value, zonecode.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Zonecode{" +
                "value='" + value + '\'' +
                '}';
    }
}
