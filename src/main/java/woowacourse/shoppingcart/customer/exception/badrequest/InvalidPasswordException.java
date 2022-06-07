package woowacourse.shoppingcart.customer.exception.badrequest;

import woowacourse.shoppingcart.exception.BadRequestException;

public class InvalidPasswordException extends BadRequestException {

    public InvalidPasswordException() {
        super("1000", "비밀번호가 유효하지 않습니다.");
    }
}
