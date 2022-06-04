package woowacourse.shoppingcart.exception.attribute;

import woowacourse.shoppingcart.exception.ShoppingCartException;

public class InvalidPropertyException extends ShoppingCartException {

    public InvalidPropertyException(String message) {
        super(message);
    }
}
