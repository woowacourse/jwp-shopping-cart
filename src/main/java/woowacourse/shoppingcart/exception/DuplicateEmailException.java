package woowacourse.shoppingcart.exception;

public class DuplicateEmailException  extends RuntimeException {
    public DuplicateEmailException() {
        this("이메일이 중복입니다.");
    }

    public DuplicateEmailException(final String msg) {
        super(msg);
    }
}
