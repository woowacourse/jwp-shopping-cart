package cart.domain;

public class Name {

    private final String value;

    public Name(final String value) {
        validateIsBlank(value);
        this.value = value;
    }

    private void validateIsBlank(final String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException("이름은 빈칸이 될 수 없습니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
