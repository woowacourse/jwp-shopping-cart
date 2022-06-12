package woowacourse.shoppingcart.exception.domain;

import woowacourse.shoppingcart.exception.ShoppingCartException;

public class ProductNotFoundException extends ShoppingCartException {

    public ProductNotFoundException() {
        this("올바르지 않은 사용자 이름이거나 상품 아이디 입니다.");
    }

    public ProductNotFoundException(final String msg) {
        super(msg);
    }
}
