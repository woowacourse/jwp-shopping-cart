package cart.web.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class CartExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<String> handleIllegalResourceAccess(NoSuchElementException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("존재하지 않는 자원입니다.");
    }

    @ExceptionHandler
    public ResponseEntity<String> handleIllegalNumberFormatRequest(NumberFormatException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("옳바르지 않은 숫자 형식입니다.");
    }

    @ExceptionHandler
    public ResponseEntity<String> handleIllegalArgumentRequest(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("예기치 못한 오류가 발생했습니다.");
    }
}
