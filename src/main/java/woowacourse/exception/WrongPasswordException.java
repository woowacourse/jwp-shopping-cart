package woowacourse.exception;

public class WrongPasswordException extends CustomException {

    public WrongPasswordException() {
        super(Error.WRONG_PASSWORD);
    }
}
