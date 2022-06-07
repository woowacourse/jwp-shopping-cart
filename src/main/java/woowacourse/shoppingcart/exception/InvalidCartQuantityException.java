package woowacourse.shoppingcart.exception;

public class InvalidCartQuantityException extends CartException {

    public InvalidCartQuantityException() {
        super("상품 개수는 1개 이상이어야 합니다.");
    }
}
