package woowacourse.shoppingcart.ui;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import woowacourse.auth.dto.ExceptionResponse;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidOrderException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.exception.WrongPasswordException;

@RestControllerAdvice(basePackages = "woowacourse.shoppingcart.ui")
public class ControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleUnhandledException() {
        return ResponseEntity.badRequest().body("Unhandled Exception");
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
            IllegalArgumentException.class,
            InvalidCustomerException.class,
            InvalidCartItemException.class,
            InvalidProductException.class,
            InvalidOrderException.class,
            NotInCustomerCartItemException.class,
    })
    public ResponseEntity<String> handleInvalidAccess(final RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ExceptionResponse> handleWrongPasswordException(final WrongPasswordException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
