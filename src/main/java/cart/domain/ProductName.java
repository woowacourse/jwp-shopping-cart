package cart.domain;

public class ProductName {

    private static final int MAX_LENGTH = 10;
    private final String name;

    public ProductName(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        validateLength(name);
        validateBlank(name);
    }

    private void validateLength(String name) {
        if (name.length() >= MAX_LENGTH) {
            throw new IllegalArgumentException(String.format("이름은 %d글자 이상 넘길 수 없습니다.", MAX_LENGTH));
        }
    }

    private void validateBlank(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("이름은 공백일 수 없습니다.");
        }
    }
}
