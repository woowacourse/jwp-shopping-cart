package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public class DuplicatedEmailException extends ClientRuntimeException {

    private static final String MESSAGE = "중복된 이메일이 이미 존재합니다.";

    public DuplicatedEmailException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
    }
}
