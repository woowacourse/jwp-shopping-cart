package woowacourse.shoppingcart.exception.notfound;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("요청한 자원을 찾을 수 없습니다.");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
