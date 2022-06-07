package woowacourse.shoppingcart.exception;

public class CustomerNotFoundException extends DataNotFoundException {

    private static final String MESSAGE = "존재하지 않는 회원입니다.";

    public CustomerNotFoundException() {
        super(MESSAGE);
    }
}
