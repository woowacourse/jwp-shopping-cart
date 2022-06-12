package woowacourse.shoppingcart.domain.product;

import woowacourse.shoppingcart.exception.InvalidProductException;

public class ProductName {

    public static final int MAX_LENGTH = 64;

    private final String name;

    public ProductName(String name) {
        validateNotBlank(name);
        validateLengthLimit(name);
        this.name = name;
    }

    private void validateNotBlank(String name) {
        if (name.trim().length() == 0) {
            throw new InvalidProductException("상품 이름은 비워둘 수 없습니다.");
        }
    }

    private void validateLengthLimit(String name) {
        if (name.length() > MAX_LENGTH) {
            throw new InvalidProductException("상품 이름은 64자까지 가능합니다.");
        }
    }

    public String getName() {
        return name;
    }
}
