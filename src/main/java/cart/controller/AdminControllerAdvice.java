package cart.controller;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import cart.dto.ErrorResponse;

@ControllerAdvice(assignableTypes = AdminController.class)
public class AdminControllerAdvice {

    public static final String UNKNOWN_ERROR_MESSAGE = "Unknown Error";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleBindException(MethodArgumentNotValidException exception) {
        final FieldError fieldError = exception.getBindingResult().getFieldError();
        if(fieldError == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse(UNKNOWN_ERROR_MESSAGE));
        }

        final String errorMessage = fieldError.getDefaultMessage();
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(Objects.requireNonNullElse(errorMessage, UNKNOWN_ERROR_MESSAGE)));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception exception) {
        final String errorMessage = exception.getMessage();
        logger.error("error : {}", errorMessage);
        return ResponseEntity.internalServerError().body(new ErrorResponse(errorMessage));
    }
}
