package woowacourse.shoppingcart.exception;

public class InvalidProductException extends RuntimeException {
    public InvalidProductException() {
        this("존재하지 않는 상품 아이디 입니다.");
    }

    public InvalidProductException(final String msg) {
        super(msg);
    }
}
