package cart.dto;

import org.springframework.http.HttpStatus;

public class ApiErrorResponse extends ApiResponse {

    private String errorMessage;

    public ApiErrorResponse(int status, String errorMessage) {
        super(status, false);
        this.errorMessage = errorMessage;
    }

    public static ApiErrorResponse of(HttpStatus httpStatus, Exception exception) {
        return new ApiErrorResponse(httpStatus.value(), exception.getMessage());
    }

    public static ApiErrorResponse of(HttpStatus httpStatus, String exceptionMessage) {
        return new ApiErrorResponse(httpStatus.value(), exceptionMessage);
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
