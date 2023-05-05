package cart.exception;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends CustomException {

    public ProductNotFoundException(final String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
