package cart.dto;

import org.springframework.http.HttpStatus;

public class ApiDataResponse<T> extends ApiResponse{

    private T data;
    public ApiDataResponse(int status, T data) {
        super(status);
        this.data = data;
    }


    public static <T> ApiDataResponse<T> of(HttpStatus httpStatus, T result) {
        return new ApiDataResponse<>(httpStatus.value(), result);
    }


    public T getData() {
        return data;
    }
}
