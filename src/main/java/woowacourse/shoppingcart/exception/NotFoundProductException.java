package woowacourse.shoppingcart.exception;

public class NotFoundProductException extends NotFoundException {

    public NotFoundProductException() {
        this("올바르지 않은 사용자 이름이거나 상품 아이디 입니다.");
    }

    public NotFoundProductException(final String msg) {
        super(msg);
    }
}
