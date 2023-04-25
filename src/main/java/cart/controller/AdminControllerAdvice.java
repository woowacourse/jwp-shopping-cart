package cart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import cart.dto.ErrorResponse;

@ControllerAdvice(assignableTypes = AdminController.class)
public class AdminControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleBindException(MethodArgumentNotValidException exception) {
        final String errorMessage = exception.getBindingResult()
                .getFieldError()
                .getDefaultMessage();
        return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception exception) {
        final String errorMessage = exception.getMessage();
        logger.error("error : {}", errorMessage);
        return ResponseEntity.internalServerError().body(new ErrorResponse(errorMessage));
    }
}
