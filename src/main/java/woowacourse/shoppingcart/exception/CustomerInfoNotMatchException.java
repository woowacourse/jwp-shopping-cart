package woowacourse.shoppingcart.exception;

public class CustomerInfoNotMatchException extends RuntimeException {

    private static final String MESSAGE = "입력된 사용자 정보가 일치하지 않습니다.";

    public CustomerInfoNotMatchException() {
        super(MESSAGE);
    }
}
