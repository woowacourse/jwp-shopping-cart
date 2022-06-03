package woowacourse.shoppingcart.ui;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import woowacourse.shoppingcart.dto.ExceptionResponse;
import woowacourse.shoppingcart.exception.AuthorizationException;
import woowacourse.shoppingcart.exception.DuplicatedUsernameException;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidOrderException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.LoginException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleUnhandledException() {
        ExceptionResponse response = new ExceptionResponse(List.of("Unhandled Exception"));
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(DuplicatedUsernameException.class)
    public ResponseEntity<ExceptionResponse> handleDuplicatedException(DuplicatedUsernameException exception) {
        ExceptionResponse response = new ExceptionResponse(List.of(exception.getMessage()));
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ExceptionResponse> handle() {
        ExceptionResponse response = new ExceptionResponse(List.of("존재하지 않는 데이터 요청입니다."));
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ExceptionResponse> handleLoginException(LoginException exception) {
        ExceptionResponse response = new ExceptionResponse(List.of(exception.getMessage()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleBindingException(final BindException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        List<String> messages = fieldErrors.stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());

        ExceptionResponse response = new ExceptionResponse(messages);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            ConstraintViolationException.class,
    })
    public ResponseEntity<ExceptionResponse> handleInvalidRequest(final RuntimeException exception) {
        ExceptionResponse response = new ExceptionResponse(List.of(exception.getMessage()));
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({
            InvalidCustomerException.class,
            InvalidCartItemException.class,
            InvalidProductException.class,
            InvalidOrderException.class,
            NotInCustomerCartItemException.class,
    })
    public ResponseEntity<ExceptionResponse> handleInvalidAccess(final RuntimeException exception) {
        ExceptionResponse response = new ExceptionResponse(List.of(exception.getMessage()));
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleAuthException(final AuthorizationException e) {
        ExceptionResponse response = new ExceptionResponse(List.of(e.getMessage()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
