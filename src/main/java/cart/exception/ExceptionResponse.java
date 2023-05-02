package cart.exception;

import java.time.LocalDateTime;

public class ExceptionResponse {

    private final LocalDateTime timestamp;
    private final String errorMessage;

    public ExceptionResponse(final LocalDateTime timestamp, final String errorMessage) {
        this.timestamp = timestamp;
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
