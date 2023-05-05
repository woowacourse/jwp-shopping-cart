package cart.service.product;

public class ProductName {
    private final String name;

    public ProductName(String name) {
        if (name.length() > 50) {
            throw new IllegalArgumentException("상품 이름은 최대 50자 입니다.");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
