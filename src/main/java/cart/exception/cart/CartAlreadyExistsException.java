package cart.exception.cart;

import org.springframework.http.HttpStatus;

public class CartAlreadyExistsException extends CartException {

    public CartAlreadyExistsException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
