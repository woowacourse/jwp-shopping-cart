package woowacourse.auth.exception;

public class InvalidEmailException extends InvalidPropertyException {

    private static final String ERROR_MESSAGE = "이메일 형식이 맞지않습니다. %s";

    public InvalidEmailException(String message) {
        super(String.format(ERROR_MESSAGE, message));
    }
}
