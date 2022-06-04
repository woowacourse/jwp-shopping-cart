package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {
        this("Email에 한글과 공백은 입력할 수 없습니다.");
    }

    public InvalidEmailException(final String msg) {
        super(msg);
    }
}
