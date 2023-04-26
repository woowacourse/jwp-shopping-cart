package cart.domain;

import java.util.Objects;

public class ProductName {

    public static final int MIN_NAME_LENGTH = 1;
    public static final int MAX_NAME_LENGTH = 64;
    public static final String NAME_LENGTH_ERROR_MESSAGE = "이름의 길이는 " + MIN_NAME_LENGTH + "자 이상 " + MAX_NAME_LENGTH + "자 이하입니다.";

    private final String name;

    public ProductName(final String name) {
        validate(name);
        this.name = name.trim();
    }

    private void validate(final String name) {
        if (name == null) {
            throw new IllegalArgumentException(NAME_LENGTH_ERROR_MESSAGE);
        }
        final String trimmedName = name.trim();
        if (trimmedName.length() < MIN_NAME_LENGTH || MAX_NAME_LENGTH < trimmedName.length()) {
            throw new IllegalArgumentException(NAME_LENGTH_ERROR_MESSAGE);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductName that = (ProductName) o;
        return Objects.equals(name, that.name);
    }

    public String getValue() {
        return name;
    }
}
