package woowacourse.shoppingcart.ui;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.auth.application.AuthorizationException;
import woowacourse.shoppingcart.dto.ErrorResponse;
import woowacourse.shoppingcart.exception.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleUnhandledException() {
        return ResponseEntity.internalServerError().body(ErrorResponse.toDto("Unhandled Exception"));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseException() {
        return ResponseEntity.internalServerError().body(ErrorResponse.toDto("database error"));
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> handle() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.toDto("존재하지 않는 데이터 요청입니다."));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequest(final BindingResult bindingResult) {
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        final FieldError mainError = fieldErrors.get(0);

        return ResponseEntity.badRequest().body(ErrorResponse.toDto(mainError.getDefaultMessage()));
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            ConstraintViolationException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ErrorResponse> handleInvalidRequest(final RuntimeException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.toDto(e.getMessage()));
    }

    @ExceptionHandler({
            InvalidCustomerException.class,
            InvalidCartItemException.class,
            InvalidProductException.class,
            InvalidOrderException.class,
            NotInCustomerCartItemException.class,
            NoExistUserException.class
    })
    public ResponseEntity<ErrorResponse> handleInvalidAccess(final RuntimeException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.toDto(e.getMessage()));
    }

    @ExceptionHandler({
            AuthorizationException.class,
            NotLoginException.class
    })
    public ResponseEntity<ErrorResponse> handleUnAuthorizedAccess(final RuntimeException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.toDto("인증되지 않은 사용자입니다."));
    }

    @ExceptionHandler(DuplicateNameException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedName(final RuntimeException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.toDto(e.getMessage()));
    }
}
