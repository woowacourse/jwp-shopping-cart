package cart.controller.dto;

import java.util.List;

public class ExceptionResponse {

    private final List<String> messages;

    public ExceptionResponse(final String message) {
        this.messages = List.of(message);
    }

    public ExceptionResponse(final List<String> messages) {
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }
}
