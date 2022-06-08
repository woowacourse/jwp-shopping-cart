package woowacourse.shoppingcart.exception;

public class InvalidPageException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "잘못된 페이지 요청입니다.";

    public InvalidPageException() {
        super(DEFAULT_MESSAGE);
    }
}
