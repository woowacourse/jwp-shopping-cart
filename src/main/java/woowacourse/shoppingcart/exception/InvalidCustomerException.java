package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCustomerException extends RuntimeException {
    public InvalidCustomerException() {
        this("존재하지 않는 유저입니다.");
    }

    public InvalidCustomerException(final String msg) {
        super(msg);
    }
}
