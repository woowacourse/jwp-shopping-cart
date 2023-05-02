package cart.common;

import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionDto> handleIllegalArgumentException(
        final IllegalArgumentException exception) {
        final ExceptionDto exceptionDto = new ExceptionDto(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handleMethodArgumentNotValidException(
        final MethodArgumentNotValidException exception) {
        final ExceptionDto exceptionDto = new ExceptionDto(
            Objects.requireNonNull(exception.getFieldError()).getDefaultMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionDto);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionDto> handleAuthenticationException(
        final AuthenticationException exception) {
        final ExceptionDto exceptionDto = new ExceptionDto(exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exceptionDto);
    }
}
