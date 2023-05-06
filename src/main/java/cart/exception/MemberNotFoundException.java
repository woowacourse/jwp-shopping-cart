package cart.exception;

import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends CartCustomException {

    private static final String MESSAGE = "일치하는 사용자가 없습니다.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public MemberNotFoundException() {
        super(MESSAGE, HTTP_STATUS);
    }
}
