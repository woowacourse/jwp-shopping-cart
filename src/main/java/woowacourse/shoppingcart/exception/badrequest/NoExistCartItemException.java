package woowacourse.shoppingcart.exception.badrequest;

public class NoExistCartItemException extends BadRequestException {

    public NoExistCartItemException() {
        super("1004", "장바구니에 상품이 존재하지 않습니다.");
    }
}
