package cart.domain;

import java.util.Objects;

public class Name {

    public static final int MAX_NAME_LENGTH = 50;
    private final String name;

    public Name(String name) {
        this.name = validate(name);
    }

    private String validate(String name) {
        validateNotBlank(name);
        validateLength(name);
        return name;
    }

    private void validateNotBlank(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("이름을 입력하세요.");
        }
    }

    private void validateLength(String name) {
        int length = name.length();
        if (length > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("이름은 " + MAX_NAME_LENGTH + "자 이하여야 합니다. (현재 " + length + "자)");
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Name name1 = (Name) o;
        return Objects.equals(name, name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Name{" +
                "name='" + name + '\'' +
                '}';
    }
}
