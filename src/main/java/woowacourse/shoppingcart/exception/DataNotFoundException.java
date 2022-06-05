package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public class DataNotFoundException extends ClientRuntimeException {

    private static final String MESSAGE = "존재하지 않는 데이터입니다.";

    public DataNotFoundException(final String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public DataNotFoundException() {
        this(MESSAGE);
    }
}
