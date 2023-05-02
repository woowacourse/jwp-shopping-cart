package cart.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.http.HttpStatus;

@JsonInclude(Include.NON_NULL)
public class ApiResponse {
    private final int status;
    private final boolean success;


    public ApiResponse(int status) {
        this.status = status;
        this.success = true;
    }

    public ApiResponse(int status, boolean success) {
        this.status = status;
        this.success = success;
    }

    public static ApiResponse from(HttpStatus httpStatus) {
        return new ApiResponse(httpStatus.value());
    }


    public int getStatus() {
        return status;
    }

    public boolean getSuccess() {
        return success;
    }
}
