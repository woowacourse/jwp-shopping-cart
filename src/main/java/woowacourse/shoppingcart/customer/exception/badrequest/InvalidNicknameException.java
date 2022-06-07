package woowacourse.shoppingcart.customer.exception.badrequest;

import woowacourse.shoppingcart.exception.BadRequestException;

public class InvalidNicknameException extends BadRequestException {

    public InvalidNicknameException() {
        super("1000", "닉네임이 유효하지 않습니다.");
    }
}
