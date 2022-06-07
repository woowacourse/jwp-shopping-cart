package woowacourse.shoppingcart.auth.exception;

import java.util.NoSuchElementException;

public class NoSuchEmailException extends NoSuchElementException {

    public NoSuchEmailException() {
        super("해당 이메일이 존재하지 않습니다.");
    }
}
