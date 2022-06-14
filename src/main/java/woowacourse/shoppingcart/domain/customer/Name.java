package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;

public class Name {

    private static final int MAXIMUM_NAME_LENGTH = 30;

    private final String name;

    public Name(final String name) {
        validateName(name);
        this.name = name;
    }

    public static void validateName(final String name) {
        if (name.isBlank() || name.length() > MAXIMUM_NAME_LENGTH) {
            throw new IllegalArgumentException("올바르지 않은 이름 형식입니다.");
        }
    }

    public String getValue() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name1 = (Name) o;
        return Objects.equals(name, name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
