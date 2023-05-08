package cart.exception;

public class ExistProductException extends RuntimeException {

    public ExistProductException() {
    }

    public ExistProductException(String message) {
        super(message);
    }
}
