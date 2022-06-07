package woowacourse.shoppingcart.controlleradvice;

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
import woowacourse.shoppingcart.exception.DuplicateCustomerException;
import woowacourse.shoppingcart.exception.DuplicateProductInCartException;
import woowacourse.shoppingcart.exception.IncorrectPasswordException;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidOrderException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.InvalidQuantityException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleUnhandledException() {
        return ResponseEntity.badRequest().body("Unhandled Exception");
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(DuplicateKeyException e) {
        return ResponseEntity.badRequest().body("중복 키가 존재합니다.");
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(DuplicateCustomerException e) {
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
    public ResponseEntity<String> handle(InvalidCustomerException e) {
        return ResponseEntity.badRequest().body("존재하지 않는 유저입니다.");
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(IncorrectPasswordException e) {
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

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(final DuplicateProductInCartException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.ALREADY_EXIST_IN_CART);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(final InvalidQuantityException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.INVALID_QUANTITY);
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
