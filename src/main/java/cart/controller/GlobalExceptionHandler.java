package cart.controller;

import cart.exception.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(final IllegalArgumentException exception) {
        logger.warn(exception.getMessage());
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        final Map<String, String> errorMessageByFields = new HashMap<>();
        final List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();

        allErrors.forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorMessageByFields.put(fieldName, message);
            logger.warn(message);
        });

        return ResponseEntity.badRequest().body(errorMessageByFields);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<String> handleAuthorizationException(final AuthorizationException exception) {
        logger.warn(exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException exception) {
        logger.warn(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("요청 리소스에 맞는 메서드가 아닙니다.");
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> handleEmptyResultDataAccessException(final EmptyResultDataAccessException exception) {
        logger.warn(exception.getMessage());
        return ResponseEntity.badRequest().body("접근하려는 데이터가 존재하지 않습니다.");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(final HttpMessageNotReadableException exception) {
        logger.warn(exception.getMessage());
        return ResponseEntity.badRequest().body("요청 body에서 문제가 발생했습니다. body를 확인해주세요.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(final Exception exception) {
        logger.error("예기치 못한 오류: " + exception);
        return ResponseEntity.internalServerError().body("예기치 못한 예외가 발생했습니다.");
    }
}
