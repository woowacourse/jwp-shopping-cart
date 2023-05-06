package cart.service.product.domain;

public class ProductName {
    private static final int MAX_NAME_LENGTH = 50;
    private final String name;

    public ProductName(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("상품 이름은 최대 50자 입니다.");
        }
    }

    public String getName() {
        return name;
    }
}
