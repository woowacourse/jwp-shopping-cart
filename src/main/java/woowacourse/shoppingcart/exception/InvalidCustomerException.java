package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public class InvalidCustomerException extends ClientRuntimeException {

    private static final String MESSAGE = "존재하지 않는 유저입니다.";

    public InvalidCustomerException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
    }
}
