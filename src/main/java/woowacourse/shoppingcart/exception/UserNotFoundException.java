package woowacourse.shoppingcart.exception;

public class UserNotFoundException extends LoginException {

    private static final String MESSAGE = "해당하는 username이 없습니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
