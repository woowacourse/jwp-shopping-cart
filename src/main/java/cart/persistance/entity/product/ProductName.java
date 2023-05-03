package cart.persistance.entity.product;

public class ProductName {

    private final String name;

    public ProductName(final String name) {
        validate(name);
        this.name = name;
    }

    private void validate(final String name) {
        validateIsNull(name);
        validateIsBlank(name);
    }

    private void validateIsNull(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("[ERROR] 상품 이름이 null입니다.");
        }
    }

    private void validateIsBlank(final String name) {
        if (name.strip().isBlank()) {
            throw new IllegalArgumentException("[ERROR] 상품 이름이 비어있습니다.");
        }
    }

    public String getName() {
        return name;
    }
}
