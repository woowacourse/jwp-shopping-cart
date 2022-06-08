package woowacourse.shoppingcart.exception;

public class CartNotFoundException extends CartException {

    public CartNotFoundException() {
        super("존재하지 않는 장바구니 정보입니다.");
    }
}
