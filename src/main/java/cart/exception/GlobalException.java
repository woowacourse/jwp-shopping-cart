package cart.exception;

public class GlobalException extends RuntimeException {
    private final String message;

    public GlobalException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
