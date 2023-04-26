package cart.exception;

import java.time.LocalDateTime;

public class ExceptionResponse {

    private final int statusCode;
    private final LocalDateTime timestamp;
    private final String errorMessage;

    public ExceptionResponse(final int statusCode, final LocalDateTime timestamp, final String errorMessage) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.errorMessage = errorMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
