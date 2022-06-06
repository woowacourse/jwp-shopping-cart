package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public final class CannotDeleteCustomerException extends ShoppingCartException {

    public CannotDeleteCustomerException() {
        this("회원 데이터가 존재하지 않아 삭제에 실패했습니다.");
    }

    public CannotDeleteCustomerException(final String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
