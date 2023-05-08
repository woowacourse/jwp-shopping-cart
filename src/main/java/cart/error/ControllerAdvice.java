package cart.error;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cart.error.dto.ErrorResponse;
import cart.error.exception.CartException;
import cart.error.exception.ErrorCode;

@RestControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(final Exception e) {
		return ResponseEntity.internalServerError()
			.body(new ErrorResponse(500, "SERVER-500-1", "Internal Server Error"));

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleBindException(final MethodArgumentNotValidException exception) {

		final FieldError fieldError = exception.getBindingResult()
			.getFieldError();
		final String exceptionMessage = Objects.requireNonNull(fieldError)
			.getDefaultMessage();

		return ResponseEntity.badRequest()
			.body(new ErrorResponse(400, "CLIENT-400-1", exceptionMessage));
	}

	@ExceptionHandler(CartException.class)
	public ResponseEntity<ErrorResponse> handlingApplicationException(final CartException exception) {
		final ErrorCode errorCode = exception.getErrorCode();

		return ResponseEntity.status(HttpStatus.valueOf(errorCode.getStatus()))
			.body(new ErrorResponse(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage()));

	}

}
