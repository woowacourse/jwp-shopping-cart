package cart.dto.response;

import java.time.LocalDateTime;

public class ErrorResponse {

    private final String message;
    private final LocalDateTime time;

    public ErrorResponse(String message, LocalDateTime time) {
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
