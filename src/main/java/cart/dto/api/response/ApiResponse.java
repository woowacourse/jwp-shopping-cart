package cart.dto.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.http.HttpStatus;

@JsonInclude(Include.NON_NULL)
public class ApiResponse<T> {
    private final int status;
    private final boolean success;
    private T data;

    public static ApiResponse<Void> from(HttpStatus httpStatus) {
        return new ApiResponse<>(httpStatus.value());
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus, T result) {
        return new ApiResponse<>(httpStatus.value(), result);
    }


    public ApiResponse(int status) {
        this.status = status;
        this.success = true;
    }

    public ApiResponse(int status, T data) {
        this(status);
        this.data = data;
    }

    public ApiResponse(int status, boolean success) {
        this.status = status;
        this.success = success;
    }


    public int getStatus() {
        return status;
    }
    public boolean getSuccess() {
        return success;
    }
    public T getData() {
        return data;
    }
}
