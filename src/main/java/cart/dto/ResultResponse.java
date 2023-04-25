package cart.dto;

public class ResultResponse<T> extends Response {
    private final T result;

    public ResultResponse(String code, String message, T result) {
        super(code, message);
        this.result = result;
    }

    public T getResult() {
        return result;
    }
}
