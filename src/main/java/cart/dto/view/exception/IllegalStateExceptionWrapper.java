package cart.dto.view.exception;

public class IllegalStateExceptionWrapper {

    private final String message;

    private IllegalStateExceptionWrapper(String message) {
        this.message = message;
    }

    public static IllegalStateExceptionWrapper of(String message) {
        return new IllegalStateExceptionWrapper(message);
    }

    public String getMessage() {
        return message;
    }
}
