package cart.controller;

import cart.exception.NoSuchProductException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = AdminApiController.class)
public class AdminControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> validHandler(final MethodArgumentNotValidException exception) {
        final String message = exception.getAllErrors().get(0).getDefaultMessage();

        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(NoSuchProductException.class)
    public ResponseEntity<String> noProductDataHandler(final NoSuchProductException exception) {
        final String message = exception.getMessage();

        return ResponseEntity.badRequest().body(message);
    }
}
