package cart.controller;

import cart.exception.CartCustomException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CartApplicationControllerAdvice {

    private static final String UNEXPECTED_ERROR_LOG_FORMAT = "예상치 못한 에러 발생 : " + System.lineSeparator() + "{}";
    private static final Logger LOGGER = LoggerFactory.getLogger(CartApplicationControllerAdvice.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> sendErrorMessage(final MethodArgumentNotValidException exception) {
        final String errorMessages = exception.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(System.lineSeparator()));
        return ResponseEntity.badRequest().body(errorMessages);
    }

    @ExceptionHandler(CartCustomException.class)
    public ResponseEntity<String> handleCustomException(final CartCustomException cartCustomException) {
        return ResponseEntity.status(cartCustomException.getHttpStatus()).body(cartCustomException.getMessage());
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
