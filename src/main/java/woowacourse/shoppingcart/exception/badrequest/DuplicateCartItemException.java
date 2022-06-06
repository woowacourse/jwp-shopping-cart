package woowacourse.shoppingcart.exception.badrequest;

public class DuplicateCartItemException extends BadRequestException {

    public DuplicateCartItemException() {
        super("1003", "중복된 상품입니다.");
    }
}
