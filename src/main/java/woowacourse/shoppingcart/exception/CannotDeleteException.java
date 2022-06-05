package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public final class CannotDeleteException extends ShoppingCartException {

    public CannotDeleteException() {
        this("해당 데이터가 존재하지 않아 삭제에 실패했습니다.");
    }

    public CannotDeleteException(final String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
