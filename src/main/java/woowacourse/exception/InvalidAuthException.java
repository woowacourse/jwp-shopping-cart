package woowacourse.exception;

public class InvalidAuthException extends BusinessException {

	public InvalidAuthException() {
		super();
	}

	public InvalidAuthException(String message) {
		super(message);
	}
}
