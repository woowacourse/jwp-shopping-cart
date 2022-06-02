package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateUsernameException extends RuntimeException {

    public DuplicateUsernameException() {
        this("중복된 이름입니다.");
    }

    public DuplicateUsernameException(final String msg) {
        super(msg);
    }
}
