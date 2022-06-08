package woowacourse.shoppingcart.exception;

public class ExistSameProductIdException extends RuntimeException {

    public ExistSameProductIdException() {
        super("이미 담겨있는 상품입니다.");
    }

    public ExistSameProductIdException(String message) {
        super(message);
    }
}
