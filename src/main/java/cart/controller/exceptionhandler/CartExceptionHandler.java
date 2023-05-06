package cart.controller.exceptionhandler;

import cart.auth.UnauthorizedException;
import cart.domain.exception.WrongEmailFormatException;
import cart.domain.exception.WrongPasswordFormatException;
import cart.domain.exception.WrongPriceException;
import cart.domain.exception.WrongProductNameException;
import cart.service.exception.DuplicateCartException;
import cart.service.exception.InvalidEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CartExceptionHandler {

    private static final String UNEXPECTED_EXCEPTION_MESSAGE = "관리자에게 문의하세요.";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodValidException(MethodArgumentNotValidException exception) {
        String errorMessage = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }

    @ExceptionHandler(DuplicateCartException.class)
    public ResponseEntity<String> handleDuplicateCartException(DuplicateCartException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler({WrongEmailFormatException.class, WrongPasswordFormatException.class, WrongPriceException.class,
            WrongProductNameException.class, InvalidEmailException.class})
    public ResponseEntity<String> handleWrongValueException(RuntimeException runtimeException) {
        return ResponseEntity.badRequest().body(runtimeException.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleUnexpectedException(RuntimeException exception) {
        return ResponseEntity.internalServerError().body(UNEXPECTED_EXCEPTION_MESSAGE);
    }
}
