package cart.dto.response;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;

public class ApiResponse<T> {
    private static final String SUCCESS_STATUS = "success";
    private static final String ERROR_STATUS = "error";

    private HttpStatus status;
    private T data;
    private String message;

    public static <T> ApiResponse<T> createSuccess(T data) {
        return new ApiResponse<>(HttpStatus.OK, data, SUCCESS_STATUS);
    }

    public static ApiResponse<?> createBadRequest(String message) {
        Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put("error", message);
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, errorMessage, ERROR_STATUS);
    }

    public static ApiResponse<?> createInternalServerError(String message) {
        Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put("error", message);
        return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, ERROR_STATUS);
    }

    public ApiResponse() {

    }

    private ApiResponse(HttpStatus status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
