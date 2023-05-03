package cart.domain.product;

import java.util.Objects;

public class ProductName {
    private static final int MAX_NAME_LENGTH = 10;
    private final String name;

    public ProductName(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        validateNull(name);
        validateLength(name);
    }

    private void validateNull(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("상품의 이름은 빈 값이 될 수 없습니다.");
        }
    }

    private void validateLength(String name) {
        int nameLength = name.length();
        if (nameLength > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("상품의 이름의 길이는 " + MAX_NAME_LENGTH + "자리 이하여야 합니다.");
        }
    }

    public String getName() {
        return name;
    }
}
