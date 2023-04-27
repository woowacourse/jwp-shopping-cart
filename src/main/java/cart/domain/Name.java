package cart.domain;

import java.util.Objects;

public class Name {

    public static final int MAX_LENGTH = 30;

    private final String value;

    public Name(final String value) {
        validateNotEmpty(value);
        validateSize(value);
        this.value = value;
    }

    private void validateNotEmpty(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("이름은 빈 값일 수 없습니다.");
        }
    }

    private void validateSize(final String value) {
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("이름은 " + MAX_LENGTH + "글자 이하여야 합니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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
