package woowacourse.exception;

import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.auth.exception.AuthorizationException;
import woowacourse.shoppingcart.dto.response.ErrorResponse;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.exception.duplicate.DuplicateException;
import woowacourse.shoppingcart.exception.invalid.InvalidException;
import woowacourse.shoppingcart.exception.notfound.NotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedName(final RuntimeException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequest(final MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(getErrorMessage(e)));
    }

    private String getErrorMessage(final MethodArgumentNotValidException e) {
        final StringBuilder message = new StringBuilder();
        for (final FieldError error : e.getFieldErrors()) {
            message.append(error.getDefaultMessage()).append("\n");
        }
        return message.toString();
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            ConstraintViolationException.class,
    })
    public ResponseEntity<ErrorResponse> handleInvalidRequest(final RuntimeException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler({
            InvalidException.class,
            NotInCustomerCartItemException.class,
    })
    public ResponseEntity<ErrorResponse> handleInvalidAccess(final RuntimeException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ErrorResponse> handleUnAuthorizedAccess(final RuntimeException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(final RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleUnhandledException(final RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(new ErrorResponse("관리자에게 문의해주세요🥺"));
    }
}
