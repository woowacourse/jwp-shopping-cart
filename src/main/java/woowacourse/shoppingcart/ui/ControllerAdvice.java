package woowacourse.shoppingcart.ui;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.shoppingcart.dto.ExceptionRequest;
import woowacourse.shoppingcart.exception.AuthorizationException;
import woowacourse.shoppingcart.exception.DuplicateNicknameException;
import woowacourse.shoppingcart.exception.DuplicateUsernameException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidInputException;
import woowacourse.shoppingcart.exception.InvalidLoginException;
import woowacourse.shoppingcart.exception.InvalidPasswordException;
import woowacourse.shoppingcart.exception.ResourceNotFoundException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity duplicateKeyException() {
        ExceptionRequest exceptionRequest = new ExceptionRequest("중복된 값이 존재합니다.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionRequest);
    }

    @ExceptionHandler
    public ResponseEntity duplicateUsernameException(final DuplicateUsernameException exception) {
        ExceptionRequest exceptionRequest = new ExceptionRequest(exception.getMessage());
        return ResponseEntity.status(DuplicateUsernameException.STATUS_CODE).body(exceptionRequest);
    }

    @ExceptionHandler
    public ResponseEntity duplicateNicknameException(final DuplicateNicknameException exception) {
        ExceptionRequest exceptionRequest = new ExceptionRequest(exception.getMessage());
        return ResponseEntity.status(DuplicateNicknameException.STATUS_CODE).body(exceptionRequest);
    }

    @ExceptionHandler
    public ResponseEntity invalidPasswordException(final InvalidPasswordException exception) {
        ExceptionRequest exceptionRequest = new ExceptionRequest(exception.getMessage());
        return ResponseEntity.status(InvalidPasswordException.STATUS_CODE).body(exceptionRequest);
    }

    @ExceptionHandler
    public ResponseEntity invalidCustomerException(final InvalidCustomerException exception) {
        ExceptionRequest exceptionRequest = new ExceptionRequest(exception.getMessage());
        return ResponseEntity.status(InvalidCustomerException.STATUS_CODE).body(exceptionRequest);
    }

    @ExceptionHandler
    public ResponseEntity resourceNotFoundException(final ResourceNotFoundException exception) {
        ExceptionRequest exceptionRequest = new ExceptionRequest(exception.getMessage());
        return ResponseEntity.status(ResourceNotFoundException.STATUS_CODE).body(exceptionRequest);
    }

    @ExceptionHandler
    public ResponseEntity invalidLoginException(final InvalidLoginException exception) {
        ExceptionRequest exceptionRequest = new ExceptionRequest(exception.getMessage());
        return ResponseEntity.status(InvalidLoginException.STATUS_CODE).body(exceptionRequest);
    }

    @ExceptionHandler
    public ResponseEntity authorizationException(final AuthorizationException exception) {
        ExceptionRequest exceptionRequest = new ExceptionRequest(exception.getMessage());
        return ResponseEntity.status(AuthorizationException.STATUS_CODE).body(exceptionRequest);
    }

    @ExceptionHandler
    public ResponseEntity invalidInputException(final InvalidInputException exception) {
        ExceptionRequest exceptionRequest = new ExceptionRequest(exception.getMessage());
        return ResponseEntity.status(InvalidInputException.STATUS_CODE).body(exceptionRequest);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity unhandledException() {
        ExceptionRequest exceptionRequest = new ExceptionRequest("예상치못한 에러가 발생했습니다.");
        return ResponseEntity.badRequest().body(exceptionRequest);
    }
}
