package cart.controller;

import cart.excpetion.AuthException;
import cart.excpetion.CartException;
import cart.excpetion.ProductException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> handle(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({AuthException.class})
    public ResponseEntity<String> handle(AuthException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler({EmptyResultDataAccessException.class, IllegalStateException.class})
    public ResponseEntity<String> handle(Exception e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({ProductException.class})
    public ResponseEntity<String> handle(ProductException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({CartException.class})
    public ResponseEntity<String> handle(CartException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
