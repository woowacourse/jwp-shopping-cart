package woowacourse;

import java.util.List;
import javax.validation.ConstraintViolationException;
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
import woowacourse.shoppingcart.exception.AlreadyExistException;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidOrderException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotExistException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.exception.OutOfStockException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleUnhandledException() {
        return ResponseEntity.badRequest().body("Unhandled Exception");
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity handle() {
        return ResponseEntity.badRequest().body("존재하지 않는 데이터 요청입니다.");
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity handleInvalidRequest(final BindingResult bindingResult) {
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        final String message = fieldErrors.get(0).getDefaultMessage();
        final ErrorResponse errorResponse = ErrorResponse.from(Integer.parseInt(message));
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            ConstraintViolationException.class,
    })
    public ResponseEntity handleInvalidRequest(final RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({
            InvalidCustomerException.class,
            InvalidCartItemException.class,
            InvalidProductException.class,
            InvalidOrderException.class,
            NotInCustomerCartItemException.class,
    })
    public ResponseEntity handleInvalidAccess(final RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorResponse> handleLoginException(LoginException e){
        return ResponseEntity.badRequest().body(e.getErrorResponse());
    }

    @ExceptionHandler(JoinException.class)
    public ResponseEntity<ErrorResponse> handleJoinException(JoinException e){
        return ResponseEntity.badRequest().body(e.getErrorResponse());
    }

    @ExceptionHandler(NotExistException.class)
    public ResponseEntity<ErrorResponse> handleNotExistException(NotExistException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getErrorResponse());
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyExistException(AlreadyExistException e){
        return ResponseEntity.badRequest().body(e.getErrorResponse());
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<ErrorResponse> handleOutOfStockException(OutOfStockException e){
        return ResponseEntity.badRequest().body(e.getErrorResponse());
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTokenException(final AuthException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getErrorResponse());
    }
}
