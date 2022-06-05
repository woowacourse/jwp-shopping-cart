package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public final class PasswordMismatchException extends CartException {

    public PasswordMismatchException() {
        super("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
    }
}
