package cart.exception.custom;

import org.springframework.http.HttpStatus;

public class ArgumentNotValidException extends ApplicationException {

    public ArgumentNotValidException(String message) {
        super(message);
    }

    @Override
    public HttpStatus status() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }
}
