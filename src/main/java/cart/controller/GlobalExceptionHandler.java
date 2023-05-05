package cart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler
    public ResponseEntity<String> handleException(MethodArgumentNotValidException exception) {
        logger.info(exception.getClass().getSimpleName() + " - " + exception.getMessage(), exception);
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(IllegalArgumentException exception) {
        logger.info(exception.getClass().getSimpleName() + " - " + exception.getMessage(), exception);
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(Exception exception) {
        logger.error(exception.getClass().getSimpleName() + " - " + exception.getMessage(), exception);
        return ResponseEntity.internalServerError().body("서버 내부 에러가 발생했습니다.");
    }
}
