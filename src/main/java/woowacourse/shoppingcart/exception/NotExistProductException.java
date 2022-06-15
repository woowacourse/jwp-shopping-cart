package woowacourse.shoppingcart.exception;

public class NotExistProductException extends RuntimeException {

    public NotExistProductException() {
        super("존재하지 않는 상품 ID입니다.");
    }
}
