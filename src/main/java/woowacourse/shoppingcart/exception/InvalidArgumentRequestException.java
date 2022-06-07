package woowacourse.shoppingcart.exception;

public class InvalidArgumentRequestException extends RuntimeException {
    public InvalidArgumentRequestException() {
        this("잘못된 요청입니다.");
    }

    public InvalidArgumentRequestException(final String msg) {
        super(msg);
    }
}
