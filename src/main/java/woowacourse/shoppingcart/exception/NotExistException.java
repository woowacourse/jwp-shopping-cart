package woowacourse.shoppingcart.exception;

public class NotExistException extends IllegalArgumentException {

    public static final String UNKNOWN_ERROR = "알 수 없는 예외입니다.";

    public NotExistException() {
        super(UNKNOWN_ERROR);
    }
}
