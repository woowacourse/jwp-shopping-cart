package cart.controller;

import cart.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        logger.info("[IllegalArgumentException] ", exception);
        
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ErrorResponse> handleInvalidArgument(MethodArgumentNotValidException exception) {
        logger.info("[MethodArgumentNotValidException] ", exception);

        List<FieldError> fieldErrors = exception.getFieldErrors();

        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
            stringBuilder.append(fieldError.getDefaultMessage());
        }

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(stringBuilder.toString()));
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorResponse> unhandledException(Exception exception) {
        logger.error("[Internal Server Error] ", exception);

        return ResponseEntity.internalServerError()
                .body(new ErrorResponse("Internal Server Error."));
    }
}
