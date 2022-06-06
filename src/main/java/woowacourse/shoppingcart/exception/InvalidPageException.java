package woowacourse.shoppingcart.exception;

public class InvalidPageException extends RuntimeException {

    public InvalidPageException() {
        super("해당 페이지는 존재하지 않습니다.");
    }

    public InvalidPageException(String message) {
        super(message);
    }
}
