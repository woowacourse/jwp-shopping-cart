package woowacourse.shoppingcart.controller;

import static org.springframework.http.HttpStatus.*;

import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import woowacourse.auth.exception.InvalidLoginException;
import woowacourse.auth.exception.InvalidTokenException;
import woowacourse.shoppingcart.dto.ErrorResponse;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidOrderException;
import woowacourse.shoppingcart.exception.InvalidPasswordException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleUnhandledException() {
        return ResponseEntity.badRequest().body("Unhandled Exception");
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(DuplicateKeyException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.DUPLICATED_EMAIL);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> requestBodyInvalidError(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.from(getMessage(e)));
    }

    private String getMessage(MethodArgumentNotValidException e) {
        return e.getFieldErrors().stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.joining(" "));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(InvalidTokenException e) {
        return ResponseEntity.status(UNAUTHORIZED).body(ErrorResponse.INVALID_TOKEN);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(InvalidLoginException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.LOGIN_FAIL);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(InvalidPasswordException e) {
        return ResponseEntity.status(UNAUTHORIZED).body(ErrorResponse.INCORRECT_PASSWORD);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.from(e.getMessage()));
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> handle() {
        return ResponseEntity.badRequest().body("존재하지 않는 데이터 요청입니다.");
    }

    @ExceptionHandler({
        HttpMessageNotReadableException.class,
        ConstraintViolationException.class,
    })
    public ResponseEntity<String> handleInvalidRequest(final RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({
        InvalidCartItemException.class,
        InvalidProductException.class,
        InvalidOrderException.class,
        NotInCustomerCartItemException.class,
    })
    public ResponseEntity<String> handleInvalidAccess(final RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
