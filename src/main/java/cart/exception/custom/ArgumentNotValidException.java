package cart.exception.custom;

import org.springframework.http.HttpStatus;

//TODO Validation error는 422
public class ArgumentNotValidException extends ApplicationException {

    public ArgumentNotValidException(String message) {
        super(message);
    }

    @Override
    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
