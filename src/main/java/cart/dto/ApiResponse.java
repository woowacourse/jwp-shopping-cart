package cart.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.http.HttpStatus;

@JsonInclude(Include.NON_NULL)
public class ApiResponse<T> {
    private final int status;
    private T data;
    private String error;

    public ApiResponse(int status) {
        this.status = status;
    }

    public ApiResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public ApiResponse(int status, String error) {
        this.status = status;
        this.error = error;
    }

    public static ApiResponse from(HttpStatus httpStatus) {
        return new ApiResponse(httpStatus.value());
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus, T result) {
        return new ApiResponse(httpStatus.value(), result);
    }

    public static ApiResponse of(HttpStatus httpStatus, Exception exception) {
        return new ApiResponse(httpStatus.value(), exception.getMessage());
    }

    public static ApiResponse of(HttpStatus httpStatus, String exceptionMessage) {
        return new ApiResponse(httpStatus.value(), exceptionMessage);
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getError() {
        return error;
    }
}
