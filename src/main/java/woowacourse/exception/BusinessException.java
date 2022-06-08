package woowacourse.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

	private final ErrorCode code;

	public BusinessException(ErrorCode code, String message) {
		super(message);
		this.code = code;
	}

	public int getRowCode() {
		return code.getCode();
	}
}
