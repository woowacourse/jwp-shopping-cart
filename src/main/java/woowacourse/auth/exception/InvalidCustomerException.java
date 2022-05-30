package woowacourse.auth.exception;

public class InvalidCustomerException extends RuntimeException {
	public InvalidCustomerException() {
		super();
	}

	public InvalidCustomerException(String message) {
		super(message);
	}
}
