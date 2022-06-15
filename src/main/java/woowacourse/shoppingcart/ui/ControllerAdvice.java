package woowacourse.shoppingcart.ui;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.shoppingcart.dto.ErrorResponse;
import woowacourse.shoppingcart.exception.ShoppingCartBadRequestException;
import woowacourse.shoppingcart.exception.ShoppingCartNotFoundException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ControllerAdvice {

    private static final int INVALID_FORMAT_ERROR_CODE = 1000;

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleInvalidRequest(MethodArgumentNotValidException e) {
        String message = e.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .findAny()
                .orElse("잘못된 형식입니다.");
        return ResponseEntity.badRequest().body(new ErrorResponse(INVALID_FORMAT_ERROR_CODE, message));
    }

    @ExceptionHandler({ShoppingCartBadRequestException.class})
    public ResponseEntity<ErrorResponse> handleShoppingCartException(final ShoppingCartBadRequestException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler({ShoppingCartNotFoundException.class})
    public ResponseEntity<Void> handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleUnhandledException(RuntimeException e) {
        return ResponseEntity.internalServerError().body("Unhandled Exception");
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
}
