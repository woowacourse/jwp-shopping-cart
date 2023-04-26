package cart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handle(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleAnyException(Exception e) {
        return ResponseEntity.internalServerError().body("예기치 못한 에러가 발생했습니다.");
    }
}
