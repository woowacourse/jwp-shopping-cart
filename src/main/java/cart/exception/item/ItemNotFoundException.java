package cart.exception.item;

import org.springframework.http.HttpStatus;

public class ItemNotFoundException extends ItemException {

    public ItemNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
