package cart.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {
    
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> handle(final Exception exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
