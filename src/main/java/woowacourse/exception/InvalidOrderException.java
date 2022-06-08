package woowacourse.exception;

public class InvalidOrderException extends BusinessException {

	public InvalidOrderException(ErrorCode code, String message) {
		super(code, message);
	}
}
