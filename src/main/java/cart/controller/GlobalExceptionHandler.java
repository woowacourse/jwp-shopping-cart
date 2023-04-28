package cart.controller;

import cart.controller.dto.ErrorResponse;
import java.util.NoSuchElementException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException() {
        ErrorResponse errorResponse = new ErrorResponse("유효하지 않은 값입니다.");
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException() {
        ErrorResponse errorResponse = new ErrorResponse("존재하지 않는 id입니다.");
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
