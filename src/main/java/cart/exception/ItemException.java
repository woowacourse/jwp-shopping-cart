package cart.exception;

import org.springframework.http.HttpStatus;

public class ItemException extends IllegalArgumentException {

    private final ErrorStatus errorStatus;

    public ItemException(ErrorStatus errorStatus) {
        this.errorStatus = errorStatus;
    }

    @Override
    public String getMessage() {
        return errorStatus.getMessage();
    }

    public HttpStatus getErrorStatus() {
        return errorStatus.getHttpStatus();
    }
}
