package cart.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidException(final MethodArgumentNotValidException e) {
        final String errorMessage = e.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body(
                new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), errorMessage));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleNoProductException(final IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(
                new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleUnexpectedException(final Exception e) {
        return ResponseEntity.internalServerError().body(
                new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
}
