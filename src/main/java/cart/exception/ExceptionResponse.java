package cart.exception;

import java.time.LocalDateTime;

public class ExceptionResponse {

    private final LocalDateTime localDateTime;
    private final String errorMessage;

    public ExceptionResponse(final LocalDateTime localDateTime, final String errorMessage) {
        this.localDateTime = localDateTime;
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
