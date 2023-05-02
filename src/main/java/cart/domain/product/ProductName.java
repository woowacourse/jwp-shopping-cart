package cart.domain.product;

import java.util.Objects;

public class ProductName {
    private static final int MAX_LENGTH = 20;

    private final String name;

    private ProductName(String name) {
        validate(name);
        this.name = name;
    }

    public static ProductName from(String name) {
        return new ProductName(name);
    }

    private void validate(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("상품 이름은 공백이 아닌 최소 1글자 이상이어야 합니다.");
        }

        if (name.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("상품 이름은 20자보다 이하여야 합니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductName that = (ProductName) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    public String getName() {
        return name;
    }
}
