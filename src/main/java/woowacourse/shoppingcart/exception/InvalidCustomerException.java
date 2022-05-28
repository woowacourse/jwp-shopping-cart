package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public final class InvalidCustomerException extends CartException {
    public InvalidCustomerException() {
        super("존재하지 않는 유저입니다.", HttpStatus.NOT_FOUND);
    }
}
