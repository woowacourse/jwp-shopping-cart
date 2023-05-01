package cart.controller;

import cart.dto.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGlobalException(final Exception exception) {
        log.error(exception.getMessage());

        final ExceptionResponse exceptionResponse = new ExceptionResponse("잠시 후 다시 시도해주세요");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(final IllegalArgumentException exception) {
        log.error(exception.getMessage());

        final ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
}
