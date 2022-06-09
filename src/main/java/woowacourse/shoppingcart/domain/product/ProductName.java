package woowacourse.shoppingcart.domain.product;

import woowacourse.shoppingcart.exception.InvalidProductException;

public class ProductName {

    private final String name;

    public ProductName(String name) {
        validateNotBlank(name);
        this.name = name;
    }

    private void validateNotBlank(String name) {
        if (name.trim().length() == 0) {
            throw new InvalidProductException("상품 이름은 비워둘 수 없습니다.");
        }
    }

    public String getName() {
        return name;
    }
}
