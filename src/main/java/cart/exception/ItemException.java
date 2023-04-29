package cart.exception;

public class ItemException extends RuntimeException {

    private final String message;

    public ItemException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
