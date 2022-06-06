package woowacourse.shoppingcart.domain.product;

public class ProductName {

    private final String name;

    public ProductName(String name) {
        validateNotBlank(name);
        this.name = name;
    }

    private void validateNotBlank(String name) {
        if (name.trim().length() == 0) {
            throw new IllegalArgumentException("상품 이름은 비워둘 수 없습니다.");
        }
    }

    public String getName() {
        return name;
    }
}
