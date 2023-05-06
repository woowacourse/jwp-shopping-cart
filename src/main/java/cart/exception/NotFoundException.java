package cart.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends HttpException {

    public NotFoundException(final String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
