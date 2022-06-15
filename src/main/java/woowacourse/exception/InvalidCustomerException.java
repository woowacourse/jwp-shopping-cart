package woowacourse.exception;

public class InvalidCustomerException extends BusinessException {

	public InvalidCustomerException(ErrorCode code, String message) {
		super(code, message);
	}
}
