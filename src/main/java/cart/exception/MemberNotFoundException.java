package cart.exception;

import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends CustomException {

    public MemberNotFoundException(final String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
