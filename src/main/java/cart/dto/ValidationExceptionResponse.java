package cart.dto;

public class ValidationExceptionResponse {

    private final String message;

    public ValidationExceptionResponse(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
