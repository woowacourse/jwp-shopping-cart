package woowacourse.shoppingcart.domain.exception;

public class InvalidPageException extends ExpectedException {

    private static final String MESSAGE = "정상적인 페이지가 아닙니다.";

    public InvalidPageException() {
        super(MESSAGE);
    }
}
