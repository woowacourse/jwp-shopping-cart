package woowacourse.shoppingcart.ui;

import java.util.List;
import javax.validation.ConstraintViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.auth.exception.AuthException;
import woowacourse.shoppingcart.dto.ErrorResponse;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({
            RuntimeException.class,
            Exception.class,
    })
    public ResponseEntity<ErrorResponse> handleUnhandledException(Exception e) {
        final ErrorResponse errorResponse = new ErrorResponse("Unhandled Exception");
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> handleEmptyResultDataAccess() {
        final ErrorResponse errorResponse = new ErrorResponse("존재하지 않는 데이터 요청입니다.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(final BindingResult bindingResult) {
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        final FieldError mainError = fieldErrors.get(0);
        final ErrorResponse errorResponse = new ErrorResponse(mainError.getDefaultMessage());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(final BindException e) {
        final ErrorResponse errorResponse = new ErrorResponse(e.getFieldErrors().get(0).getDefaultMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            ConstraintViolationException.class,
    })
    public ResponseEntity<ErrorResponse> handleInvalidRequest(final RuntimeException e) {
        final ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler({
            InvalidCustomerException.class,
            InvalidCartItemException.class,
            InvalidProductException.class,
            NotInCustomerCartItemException.class,
    })
    public ResponseEntity<ErrorResponse> handleInvalidAccess(final RuntimeException e) {
        final ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAuth(final AuthException e) {
        final ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
