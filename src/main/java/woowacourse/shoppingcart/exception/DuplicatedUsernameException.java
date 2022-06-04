package woowacourse.shoppingcart.exception;

public class DuplicatedUsernameException extends RuntimeException {

    private static final String MESSAGE = "중복된 username으로 customer를 생성할 수 없습니다.";

    public DuplicatedUsernameException() {
        super(MESSAGE);
    }
}
