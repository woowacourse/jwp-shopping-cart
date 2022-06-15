package woowacourse.shoppingcart.exception;

public class DuplicatedEmailException extends InvalidInputException {

    private static final String MESSAGE = "중복된 이메일이 이미 존재합니다.";

    public DuplicatedEmailException() {
        super(MESSAGE);
    }
}
