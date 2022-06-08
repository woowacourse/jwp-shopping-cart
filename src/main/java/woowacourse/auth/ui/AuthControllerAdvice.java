package woowacourse.auth.ui;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import woowacourse.auth.dto.ExceptionResponse;
import woowacourse.exception.BusinessException;
import woowacourse.exception.ErrorCodeToStatusCodeMapper;
import woowacourse.exception.InvalidAuthException;

@RestControllerAdvice(basePackages = "woowacourse.auth")
public class AuthControllerAdvice {

	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<ExceptionResponse> handleInvalidRequest(final BindingResult bindingResult) {
		final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		final FieldError mainError = fieldErrors.get(0);
		return ResponseEntity.badRequest()
			.body(new ExceptionResponse(mainError.getDefaultMessage()));
	}

	@ExceptionHandler({
		HttpMessageNotReadableException.class,
		ConstraintViolationException.class,
	})
	public ResponseEntity<ExceptionResponse> handleInvalidRequest(final RuntimeException exception) {
		return ResponseEntity.badRequest()
			.body(new ExceptionResponse(exception));
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ExceptionResponse> handleInvalidAccess(BusinessException exception) {
		return ResponseEntity.status(ErrorCodeToStatusCodeMapper.find(exception.getCode()))
			.body(new ExceptionResponse(exception));
	}

	@ExceptionHandler
	public ResponseEntity<ExceptionResponse> handleUnhandledException(RuntimeException exception) {
		return ResponseEntity.badRequest()
			.body(new ExceptionResponse(exception));
	}
}
