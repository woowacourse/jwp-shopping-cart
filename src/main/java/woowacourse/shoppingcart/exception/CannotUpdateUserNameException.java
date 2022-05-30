package woowacourse.shoppingcart.exception;

public class CannotUpdateUserNameException extends RuntimeException {

    public CannotUpdateUserNameException() {
        this("유저 이름을 변경할 수 없습니다.");
    }

    public CannotUpdateUserNameException(String message) {
        super(message);
    }
}
