package cart.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

    private static final String MESSAGE = "로그인이 필요합니다.";

    public UnauthorizedException() {
        super(MESSAGE);
    }
}
