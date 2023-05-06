package cart.exception;

import org.springframework.http.HttpStatus;

public class CartOwnerException extends HttpException {

    public CartOwnerException(final String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
