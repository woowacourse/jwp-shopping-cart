package woowacourse.shoppingcart.exception;

public class NotFoundProductException extends RuntimeException{
    public NotFoundProductException() {
        super("없는 상품을 조회할 수 없습니다.");
    }
}
