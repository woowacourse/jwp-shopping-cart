package woowacourse.shoppingcart.exception;

public class DuplicateUsernameException extends RuntimeException {

    public DuplicateUsernameException() {
        super("회원이름이 중복되었습니다.");
    }

    public DuplicateUsernameException(String message) {
        super(message);
    }
}
