package cart.domain.product;

public class ProductName {

    private final String name;

    public ProductName(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        validateIsNull(name);
        validateIsBlank(name);
    }

    private void validateIsNull(String name) {
        if (name == null) {
            throw new IllegalArgumentException("[ERROR] 상품 이름이 null입니다.");
        }
    }

    private void validateIsBlank(String name) {
        if (name.strip().isBlank()) {
            throw new IllegalArgumentException("[ERROR] 상품 이름이 비어있습니다.");
        }
    }

    public String getName() {
        return name;
    }
}
