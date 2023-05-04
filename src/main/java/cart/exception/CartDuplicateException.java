package cart.exception;

public class CartDuplicateException extends RuntimeException{

    public CartDuplicateException(String message) {
        super(message);
    }
}
