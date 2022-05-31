package woowacourse.auth.domain.customer.privacy;

import java.util.Objects;
import woowacourse.auth.exception.format.InvalidNameFormatException;

public class Name {
    private static final String NAME_REGEX = "[가-힣]{2,5}";
    private final String value;

    public Name(String value) {
        validateFormat(value);
        this.value = value;
    }

    private void validateFormat(String value) {
        if (Objects.isNull(value) || !value.matches(NAME_REGEX)) {
            throw new InvalidNameFormatException();
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Name name = (Name) o;
        return Objects.equals(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Name{" +
                "value='" + value + '\'' +
                '}';
    }
}
