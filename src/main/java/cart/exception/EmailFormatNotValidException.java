package cart.exception;

import org.springframework.http.HttpStatus;

public class EmailFormatNotValidException extends CartCustomException {

    private static final String MESSAGE = "이메일 형식이 올바르지 않습니다.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public EmailFormatNotValidException() {
        super(MESSAGE, HTTP_STATUS);
    }
}
