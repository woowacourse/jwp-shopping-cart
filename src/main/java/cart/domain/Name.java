package cart.domain;

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
}
