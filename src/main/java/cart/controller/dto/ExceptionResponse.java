package cart.controller.dto;

public class ExceptionResponse<T> {

    private final T message;

    public ExceptionResponse(T message) {
        this.message = message;
    }

    public T getMessage() {
        return message;
    }
}
