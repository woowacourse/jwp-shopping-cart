package woowacourse.exception;

public class InvalidProductException extends BusinessException {

	public InvalidProductException(ErrorCode code, String message) {
		super(code, message);
	}
}
