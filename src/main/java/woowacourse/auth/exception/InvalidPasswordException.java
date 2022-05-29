package woowacourse.auth.exception;

public class InvalidPasswordException extends InvalidPropertyException {

    private static final String ERROR_MESSAGE = "패스워드 형식이 맞지않습니다. %s";

    public InvalidPasswordException(String message) {
        super(String.format(ERROR_MESSAGE, message));
    }
}
