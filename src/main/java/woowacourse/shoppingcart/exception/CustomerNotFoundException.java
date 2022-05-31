package woowacourse.shoppingcart.exception;

public class CustomerNotFoundException extends IllegalArgumentException {
    private static final String DEFAULT_MESSAGE = "요청하신 회원을 찾을 수 없습니다";

    public CustomerNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
