package woowacourse.shoppingcart.ui;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.validation.ConstraintViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.exception.custum.AuthorizationException;
import woowacourse.shoppingcart.exception.custum.DuplicatedValueException;
import woowacourse.shoppingcart.exception.custum.InvalidInputException;
import woowacourse.shoppingcart.exception.custum.InvalidLoginException;
import woowacourse.shoppingcart.exception.custum.InvalidPasswordException;
import woowacourse.shoppingcart.exception.custum.ResourceNotFoundException;

@RestControllerAdvice
public class ControllerAdvice {

    private static final Logger LOG = Logger.getGlobal();
    private static final String INVALID_DATA_ERROR_MESSAGE = "존재하지 않는 데이터 요청입니다.";
    private static final String UNHANDLED_ERROR_MESSAGE = "예상치못한 에러가 발생했습니다.";

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Map<String,String>> handle() {
        return ResponseEntity.badRequest().body(toMessageMap(INVALID_DATA_ERROR_MESSAGE));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String,String>> handleInvalidRequest(final BindingResult bindingResult) {
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        final FieldError mainError = fieldErrors.get(0);

        return ResponseEntity.badRequest().body(toMessageMap(mainError.getDefaultMessage()));
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            ConstraintViolationException.class,
    })
    public ResponseEntity<Map<String,String>> handleInvalidRequest(final RuntimeException exception) {
        return ResponseEntity.badRequest().body(toMessageMap(exception.getMessage()));
    }

    @ExceptionHandler({
            InvalidCartItemException.class,
            InvalidProductException.class,
            NotInCustomerCartItemException.class,
    })
    public ResponseEntity<Map<String,String>> handleInvalidAccess(final RuntimeException exception) {
        return ResponseEntity.badRequest().body(toMessageMap(exception.getMessage()));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Map<String,String>> duplicateKeyException(final DuplicateKeyException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(toMessageMap("중복된 값이 존재합니다."));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,String>> duplicateValueException(final DuplicatedValueException exception) {
        return ResponseEntity.status(DuplicatedValueException.STATUS_CODE).body(toMessageMap(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,String>> invalidPasswordException(final InvalidPasswordException exception) {
        return ResponseEntity.status(InvalidPasswordException.STATUS_CODE).body(toMessageMap(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,String>> invalidCustomerException(final InvalidCustomerException exception) {
        return ResponseEntity.status(InvalidCustomerException.STATUS_CODE).body(toMessageMap(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,String>> resourceNotFoundException(final ResourceNotFoundException exception) {
        return ResponseEntity.status(ResourceNotFoundException.STATUS_CODE).body(toMessageMap(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,String>> invalidLoginException(final InvalidLoginException exception) {
        return ResponseEntity.status(InvalidLoginException.STATUS_CODE).body(toMessageMap(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,String>> authorizationException(final AuthorizationException exception) {
        return ResponseEntity.status(AuthorizationException.STATUS_CODE).body(toMessageMap(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,String>> invalidInputException(final InvalidInputException exception) {
        return ResponseEntity.status(InvalidInputException.STATUS_CODE).body(toMessageMap(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> unhandledException(final Exception e) {
        LOG.warning(e.getMessage());
        LOG.warning(Arrays.toString(e.getStackTrace()));
        return ResponseEntity.badRequest().body(toMessageMap(UNHANDLED_ERROR_MESSAGE));
    }

    private Map<String,String> toMessageMap(String message) {
        return Map.of("message", message);
    }
}
