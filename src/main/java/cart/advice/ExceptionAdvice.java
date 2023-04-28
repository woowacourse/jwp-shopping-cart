package cart.advice;

import cart.exception.MemberNotFoundException;
import cart.exception.PasswordInvalidException;
import cart.exception.ProductNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(final ProductNotFoundException exception) {
        return ResponseEntity.badRequest()
                .body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        String defaultMessage = Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage();

        return ResponseEntity.badRequest()
                .body(defaultMessage);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<String> handleMemberNotFoundException(final MemberNotFoundException exception) {
        return ResponseEntity.badRequest()
                .body(exception.getMessage());
    }

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<String> handlePasswordInvalidException(final PasswordInvalidException exception) {
        return ResponseEntity.badRequest()
                .body(exception.getMessage());
    }
}
