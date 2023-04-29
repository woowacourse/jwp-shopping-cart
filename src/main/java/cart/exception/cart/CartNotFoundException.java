package cart.exception.cart;

import org.springframework.http.HttpStatus;

public class CartNotFoundException extends CartException {

    public CartNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
