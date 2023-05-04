package cart.exception.custom;

import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends ApplicationException {

    public InvalidPasswordException() {
        super("패스워드가 일치하지 않습니다.");
    }

    public InvalidPasswordException(String message) {
        super(message);
    }

    @Override
    public HttpStatus status() {
        return HttpStatus.FORBIDDEN;
    }
}
