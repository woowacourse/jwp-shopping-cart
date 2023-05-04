package cart.controller;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import cart.controller.dto.ExceptionResponse;

@RestController
public class ControllerAdvice {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleException(final Exception e) {
		return ResponseEntity.internalServerError()
			.body(new ExceptionResponse("서버가 응답할 수 없습니다."));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponse> handleBindException(final MethodArgumentNotValidException exception) {
		final FieldError fieldError = exception.getBindingResult()
			.getFieldError();
		final String exceptionMessage = Objects.requireNonNull(fieldError)
			.getDefaultMessage();

		return ResponseEntity.badRequest()
			.body(new ExceptionResponse(exceptionMessage));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(final IllegalArgumentException exception) {
		return ResponseEntity.badRequest()
			.body(new ExceptionResponse(exception.getMessage()));
	}
}
