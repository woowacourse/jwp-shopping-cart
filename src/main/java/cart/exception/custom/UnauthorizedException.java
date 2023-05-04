package cart.exception.custom;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApplicationException {

    public UnauthorizedException(String message) {
        super(message);
    }

    @Override
    public HttpStatus status() {
        return HttpStatus.UNAUTHORIZED;
    }
}
