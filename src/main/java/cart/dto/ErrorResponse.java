package cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private String message;
    private int status;
    private List<FieldError> errors;

    public ErrorResponse(final ErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus();
    }

    public ErrorResponse(final String message, final int status) {
        this.message = message;
        this.status = status;
    }
}
