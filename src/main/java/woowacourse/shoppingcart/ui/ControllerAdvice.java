package woowacourse.shoppingcart.ui;

import java.util.List;
import javax.validation.ConstraintViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.shoppingcart.exception.AuthorizationException;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidInputException;
import woowacourse.shoppingcart.exception.InvalidLoginException;
import woowacourse.shoppingcart.exception.InvalidOrderException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.exception.ResourceNotFoundException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity handle() {
        return ResponseEntity.badRequest().body("존재하지 않는 데이터 요청입니다.");
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity handleInvalidRequest(final BindingResult bindingResult) {
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        final FieldError mainError = fieldErrors.get(0);

        return ResponseEntity.badRequest().body(mainError.getDefaultMessage());
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            ConstraintViolationException.class,
    })
    public ResponseEntity handleInvalidRequest(final RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({
            InvalidCartItemException.class,
            InvalidProductException.class,
            InvalidOrderException.class,
            NotInCustomerCartItemException.class,
    })
    public ResponseEntity handleInvalidAccess(final RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity invalidCustomerException(final InvalidCustomerException exception) {
        return ResponseEntity.status(InvalidCustomerException.STATUS_CODE).body(exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity resourceNotFoundException(final ResourceNotFoundException exception) {
        return ResponseEntity.status(ResourceNotFoundException.STATUS_CODE).body(exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity invalidLoginException(final InvalidLoginException exception) {
        return ResponseEntity.status(InvalidLoginException.STATUS_CODE).body(exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity authorizationException(final AuthorizationException exception) {
        return ResponseEntity.status(AuthorizationException.STATUS_CODE).body(exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity invalidInputException(final InvalidInputException exception) {
        return ResponseEntity.status(InvalidInputException.STATUS_CODE).body(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity unhandledException() {
        return ResponseEntity.badRequest().body("예상치못한 에러가 발생했습니다.");
    }
}
