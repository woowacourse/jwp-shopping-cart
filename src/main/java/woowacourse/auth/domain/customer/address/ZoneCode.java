package woowacourse.auth.domain.customer.address;

import java.util.Objects;
import woowacourse.auth.exception.format.InvalidZoneCodeFormatException;

public class ZoneCode {
    private static final String ZONE_CODE_REGEX = "\\d{5}";
    private final String value;

    public ZoneCode(String value) {
        validateFormat(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private void validateFormat(String value) {
        if (Objects.isNull(value) || !value.matches(ZONE_CODE_REGEX)) {
            throw new InvalidZoneCodeFormatException();
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
        ZoneCode zoneCode = (ZoneCode) o;
        return Objects.equals(value, zoneCode.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "ZoneCode{" +
                "value='" + value + '\'' +
                '}';
    }
}
