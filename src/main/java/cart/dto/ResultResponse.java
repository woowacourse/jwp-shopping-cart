package cart.dto;

import lombok.Getter;

@Getter
public class ResultResponse<T> extends Response {
    private final T result;

    public ResultResponse(String code, String message, T result) {
        super(code, message);
        this.result = result;
    }

    public static <T> ResultResponse<T> ok(String message, T result) {
        return new ResultResponse<>("200", message, result);
    }

    public static <T> ResultResponse<T> created(String message, T result) {
        return new ResultResponse<>("201", message, result);
    }
}
