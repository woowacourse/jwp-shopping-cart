package cart.dto.view.exception;

public class MethodArgumentNotValidExceptionWrapper {
    private final String message;

    private MethodArgumentNotValidExceptionWrapper(String message) {
        this.message = message;
    }

    public static MethodArgumentNotValidExceptionWrapper of(String message) {
        return new MethodArgumentNotValidExceptionWrapper(message);
    }

    public String getMessage() {
        return message;
    }
}
