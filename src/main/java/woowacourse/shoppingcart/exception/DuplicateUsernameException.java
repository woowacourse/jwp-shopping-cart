package woowacourse.shoppingcart.exception;

public class DuplicateUsernameException extends RuntimeException {

    public DuplicateUsernameException() {
        this("같은 username이 이미 있습니다.");
    }

    public DuplicateUsernameException(String msg) {
        super(msg);
    }
}
