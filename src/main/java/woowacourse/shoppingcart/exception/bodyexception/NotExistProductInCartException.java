package woowacourse.shoppingcart.exception.bodyexception;

public class NotExistProductInCartException extends IllegalRequestException {

    public NotExistProductInCartException() {
        super("1102", "장바구니에 상품이 존재하지 않습니다.");
    }
}
