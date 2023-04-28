package cart.controller.dto;

public class ExceptionResponse {

	private final String exceptionMessage;

	public ExceptionResponse(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}
}
