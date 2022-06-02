package woowacourse.shoppingcart.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException() {
        this("같은 Email이 이미 있습니다.");
    }

    public DuplicateEmailException(String msg) {
        super(msg);
    }
}
