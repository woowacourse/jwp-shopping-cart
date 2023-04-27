package cart.global.exception.response;

public class ExceptionResponse {

    private final int statusCode;
    private final String message;
    private final String status;

    protected ExceptionResponse(final int statusCode, final String message, final String status) {
        this.statusCode = statusCode;
        this.message = message;
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
}
