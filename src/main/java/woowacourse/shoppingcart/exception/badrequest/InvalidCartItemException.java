package woowacourse.shoppingcart.exception.badrequest;

public class InvalidCartItemException extends BadRequestException {

    public InvalidCartItemException() {
        super("1004", "유효하지 않은 장바구니입니다.");
    }
}
