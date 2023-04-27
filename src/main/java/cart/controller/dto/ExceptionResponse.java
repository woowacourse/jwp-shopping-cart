package cart.controller.dto;

public class ExceptionResponse<T> {

    private final T response;

    public ExceptionResponse(T response) {
        this.response = response;
    }

    public T getResponse() {
        return response;
    }
}
