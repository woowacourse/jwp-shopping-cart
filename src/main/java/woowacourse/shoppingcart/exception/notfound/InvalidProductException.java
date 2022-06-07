package woowacourse.shoppingcart.exception.notfound;

public class InvalidProductException extends NotFoundException {

    public InvalidProductException() {
        this("올바르지 않은 사용자 아이디이거나 상품 아이디 입니다.");
    }

    public InvalidProductException(final String msg) {
        super(msg);
    }
}
