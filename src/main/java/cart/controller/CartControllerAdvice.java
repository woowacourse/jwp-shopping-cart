package cart.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CartControllerAdvice {

    private static final String UNEXPECTED_ERROR_LOG_FORMAT = "예상치 못한 에러 발생 : " + System.lineSeparator() + "{}";
    private static final Logger LOGGER = LoggerFactory.getLogger(CartControllerAdvice.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> sendErrorMessage(final IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> loggingUnexpectedException(final RuntimeException runtimeException) {
        LOGGER.error(UNEXPECTED_ERROR_LOG_FORMAT, convertToString(runtimeException));
        return ResponseEntity.internalServerError().build();
    }

    private String convertToString(final Exception e) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
