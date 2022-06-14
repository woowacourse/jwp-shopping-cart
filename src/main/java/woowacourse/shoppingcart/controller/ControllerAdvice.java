package woowacourse.shoppingcart.controller;

import java.util.List;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.exception.AuthException;
import woowacourse.exception.JoinException;
import woowacourse.exception.LoginException;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.exception.ShoppingCartException;

@RestControllerAdvice
public class ControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleUnhandledException(Exception e) {
        logger.error(e.getMessage());
        return ResponseEntity.badRequest().body("예기치 못한 에러가 발생했습니다.");
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> handle() {
        return ResponseEntity.badRequest().body("존재하지 않는 데이터 요청입니다.");
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleInvalidRequest(final BindingResult bindingResult) {
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        final String message = fieldErrors.get(0).getDefaultMessage();
        final ErrorResponse errorResponse = ErrorResponse.from(Integer.parseInt(message));
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            ConstraintViolationException.class,
    })
    public ResponseEntity<String> handleInvalidRequest(final RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ShoppingCartException.class)
    public ResponseEntity<ErrorResponse> handleShoppingCartException(final ShoppingCartException e) {
        return ResponseEntity.badRequest().body(e.getErrorResponse());
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(AuthException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getErrorResponse());
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorResponse> handleLoginException(LoginException e) {
        return ResponseEntity.badRequest().body(e.getErrorResponse());
    }

    @ExceptionHandler(JoinException.class)
    public ResponseEntity<ErrorResponse> handleJoinException(JoinException e) {
        return ResponseEntity.badRequest().body(e.getErrorResponse());
    }
}
