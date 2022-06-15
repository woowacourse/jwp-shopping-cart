package woowacourse.exception;

public class InvalidAuthException extends BusinessException {

	public InvalidAuthException(ErrorCode code, String message) {
		super(code, message);
	}
}
