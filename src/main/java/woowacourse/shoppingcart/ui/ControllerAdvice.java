package woowacourse.shoppingcart.ui;

import javax.validation.ConstraintViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.shoppingcart.dto.ErrorResponse;
import woowacourse.shoppingcart.exception.NotFoundProductException;
import woowacourse.shoppingcart.exception.BadRequestException;

@RestControllerAdvice
public class ControllerAdvice {

    private static final int INVALID_FORMAT_ERROR_CODE = 1000;

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleInvalidRequest(MethodArgumentNotValidException e) {
        String message = e.getFieldErrors().stream()
            .map(FieldError::getDefaultMessage)
            .findAny()
            .orElse("잘못된 형식입니다.");
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(INVALID_FORMAT_ERROR_CODE, message));
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ErrorResponse> handleShoppingCartException(BadRequestException e) {
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(NotFoundProductException.class)
    public ResponseEntity<Void> handleNotFoundProductException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleUnhandledException() {
        return ResponseEntity.internalServerError().body("요청을 처리할 수 없습니다.");
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
