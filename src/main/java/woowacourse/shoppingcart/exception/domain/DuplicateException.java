package woowacourse.shoppingcart.exception.domain;

import woowacourse.shoppingcart.exception.ShoppingCartException;

public class DuplicateException extends ShoppingCartException {

    public DuplicateException(String message) {
        super(message);
    }
}
