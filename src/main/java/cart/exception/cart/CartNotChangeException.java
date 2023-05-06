package cart.exception.cart;

import org.springframework.http.HttpStatus;

public class CartNotChangeException extends CartException {

    public CartNotChangeException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
