package woowacourse.auth.exception;

public class InvalidMemberException extends RuntimeException {
	public InvalidMemberException() {
		super();
	}

	public InvalidMemberException(String message) {
		super(message);
	}
}
