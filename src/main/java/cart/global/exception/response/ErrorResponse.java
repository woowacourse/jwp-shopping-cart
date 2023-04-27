package cart.global.exception.response;

import cart.global.exception.ExceptionStatus;
import java.util.HashMap;
import java.util.Map;

public class ErrorResponse extends Response {

    private final Map<String, String> validation = new HashMap<>();

    public ErrorResponse(ExceptionStatus exceptionResponse) {
        super(exceptionResponse.getStatus(), exceptionResponse.getMessage(),
                exceptionResponse.getHttpStatus().name());
    }

    public void addValidation(String fieldName, String errorMessage) {
        validation.put(fieldName, errorMessage);
    }

    public Map<String, String> getValidation() {
        return validation;
    }
}
