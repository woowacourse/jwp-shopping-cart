package cart.exception;

import org.springframework.http.HttpStatus;

public class NegativePriceException extends CartCustomException {

    private static final String MESSAGE = "가격은 음수일 수 없습니다.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public NegativePriceException() {
        super(MESSAGE, HTTP_STATUS);
    }
}
