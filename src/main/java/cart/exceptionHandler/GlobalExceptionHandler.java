package cart.exceptionHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidException(final MethodArgumentNotValidException e) {
        final BindingResult bindingResult = e.getBindingResult();
        final FieldError fieldError = bindingResult.getFieldError();

        return ResponseEntity.badRequest().body(fieldError.getDefaultMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illigalArgumentException(final IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
