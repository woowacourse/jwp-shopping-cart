package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public final class CannotUpdateUserNameException extends ShoppingCartException {

    public CannotUpdateUserNameException() {
        this("유저 이름을 변경할 수 없습니다.");
    }

    public CannotUpdateUserNameException(final String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
