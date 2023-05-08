package cart.domain.product;

import java.util.Objects;

public class Name {

    private static final int MAX_NAME_LENGTH = 255;

    private final String value;

    public Name(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (value == null) {
            throw new IllegalArgumentException("이름은 null일 수 없습니다.");
        }
        if (value.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("이름은 " + MAX_NAME_LENGTH + "자 이하만 가능합니다.");
        }
        if (value.isBlank()) {
            throw new IllegalArgumentException("이름은 공백일 수 없습니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Name that = (Name) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
