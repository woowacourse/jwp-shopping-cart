package woowacourse.exception;

public class InvalidCustomerException extends BusinessException {

	public InvalidCustomerException() {
		super();
	}

	public InvalidCustomerException(String message) {
		super(message);
	}
}
