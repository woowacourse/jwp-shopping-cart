package woowacourse.shoppingcart.exception.notfound;

import woowacourse.shoppingcart.exception.badrequest.NotFoundException;

public class InvalidProductException extends NotFoundException {

    public InvalidProductException() {
        super("1004", "올바르지 않은 사용자 이름이거나 상품 아이디 입니다.");
    }
}
