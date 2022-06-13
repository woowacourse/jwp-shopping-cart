package woowacourse.exception.notFound;

public class InvalidOrderException extends NotFoundException {
    public InvalidOrderException() {
        this("유효하지 않은 주문입니다.");
    }

    public InvalidOrderException(final String msg) {
        super(msg);
    }
}
