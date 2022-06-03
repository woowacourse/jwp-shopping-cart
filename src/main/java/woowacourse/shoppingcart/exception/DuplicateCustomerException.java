package woowacourse.shoppingcart.exception;

import org.springframework.dao.DuplicateKeyException;

public class DuplicateCustomerException extends DuplicateKeyException {
    public DuplicateCustomerException(String msg) {
        super(msg);
    }
}
