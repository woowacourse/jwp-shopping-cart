package woowacourse.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ExceptionResponse {

	private String message;

	public ExceptionResponse (Exception exception) {
		this.message = exception.getMessage();
	}
}
