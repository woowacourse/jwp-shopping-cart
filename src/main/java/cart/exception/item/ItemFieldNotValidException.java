package cart.exception.item;

import org.springframework.http.HttpStatus;

public class ItemFieldNotValidException extends ItemException {

    public ItemFieldNotValidException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
