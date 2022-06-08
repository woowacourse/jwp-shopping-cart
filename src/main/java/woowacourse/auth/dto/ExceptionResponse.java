package woowacourse.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import woowacourse.exception.BusinessException;

@NoArgsConstructor
@Getter
public class ExceptionResponse {

	private Integer code;
	private String message;

	public ExceptionResponse (Exception exception) {
		this.message = exception.getMessage();
	}

	public ExceptionResponse(BusinessException exception) {
		this.code = exception.getRowCode();
		this.message = exception.getMessage();
	}

	public ExceptionResponse(String message) {
		this.message = message;
	}
}
