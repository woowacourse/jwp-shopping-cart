package cart.domain;

import java.util.Objects;

public class Name {
    private final String value;

    public Name(final String name) {
        validateName(name);
        this.value = name;
    }

    public String getValue() {
        return value;
    }

    private void validateName(final String name) {
        if (name == null || name.isBlank() || name.isEmpty()) {
            throw new IllegalArgumentException("상품의 이름을 입력해주세요.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Name name = (Name) o;
        return Objects.equals(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
