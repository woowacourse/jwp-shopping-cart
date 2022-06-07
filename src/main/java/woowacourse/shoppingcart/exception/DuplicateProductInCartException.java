package woowacourse.shoppingcart.exception;

public class DuplicateProductInCartException extends RuntimeException {
    public DuplicateProductInCartException() {
        super("이미 장바구니에 있는 제품입니다.");
    }
}
