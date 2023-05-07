package cart.exception;

public class TableIdNotFoundException extends RuntimeException {
    public TableIdNotFoundException(final String message) {
        super(message);
    }
}
