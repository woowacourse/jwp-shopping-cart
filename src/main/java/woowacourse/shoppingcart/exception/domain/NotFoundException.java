package woowacourse.shoppingcart.exception.domain;

import woowacourse.shoppingcart.exception.ShoppingCartException;

public class NotFoundException extends ShoppingCartException {

    public NotFoundException(String message) {
        super(message);
    }
}
