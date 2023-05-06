package cart.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialLength extends CartCustomException {

    private static final String MESSAGE = "유효하지 않은 Credential입니다. 다시 로그인 해주십시오.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.UNAUTHORIZED;

    public InvalidCredentialLength() {
        super(MESSAGE, HTTP_STATUS);
    }
}
