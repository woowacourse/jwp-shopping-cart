package woowacourse.exception;

public class InvalidCartItemException extends BusinessException {

	public InvalidCartItemException(ErrorCode code, String message) {
		super(code, message);
	}
}
