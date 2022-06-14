package woowacourse.shoppingcart.exception;

public class InvalidNameException extends RuntimeException {
    public InvalidNameException() {
        this("유효하지 상품 이름입니다.");
    }

    public InvalidNameException(final String msg) {
        super(msg);
    }
}
