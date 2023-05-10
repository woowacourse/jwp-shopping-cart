package cart.global.exception.product;

import cart.global.exception.common.BusinessException;
import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends BusinessException {

    public ProductNotFoundException(final String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
