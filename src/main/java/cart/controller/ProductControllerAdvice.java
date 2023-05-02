package cart.controller;

import cart.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException e) {
        String defaultMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity.badRequest().body(new ErrorResponse(defaultMessage));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        logger.error(e.getMessage());
        return ResponseEntity.internalServerError().body(new ErrorResponse("알 수 없는 에러가 발생했습니다."));
    }
}
