package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public class InvalidCustomerException extends RuntimeException {

    public static int STATUS_CODE = HttpStatus.BAD_REQUEST.value();

    public InvalidCustomerException() {
        this("존재하지 않는 유저입니다.");
    }

    public InvalidCustomerException(final String message) {
        super(message);
    }
}
