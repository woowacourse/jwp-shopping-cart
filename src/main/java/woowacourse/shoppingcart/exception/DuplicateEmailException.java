package woowacourse.shoppingcart.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException() {
        this("중복된 이메일입니다.");
    }

    public DuplicateEmailException(final String msg) {
        super(msg);
    }
}
