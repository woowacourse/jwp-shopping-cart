package cart.exceptionHandler;

import cart.exception.AuthException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "서버에 오류가 발생했습니다. 확인 후 다시 이용해주세요.";

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(final IllegalArgumentException e) {
        final Map<String, Object> body = makeBody(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

        logError(body, e.getMessage());

        return ResponseEntity.internalServerError().body(body);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final Map<String, Object> body = makeBody(HttpStatus.BAD_REQUEST,
                ex.getBindingResult()
                        .getFieldError()
                        .getDefaultMessage()
        );

        logError(body, ex.getMessage());

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleUnsuspectedException(final RuntimeException e) {
        final Map<String, Object> body = makeBody(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MESSAGE);

        logError(body, e.getMessage());

        return ResponseEntity.internalServerError().body(body);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Map<String, Object>> handleAuthException(final AuthException e) {
        final Map<String, Object> body = makeBody(HttpStatus.UNAUTHORIZED, e.getMessage());

        logError(body, e.getMessage());

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("WWW-Authenticate", "Basic realm = \"" + e.getRealm() + "\"");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(httpHeaders).body(body);
    }

    private void logError(final Map<String, Object> body, final String message) {
        logger.error(body + ", " + message);
    }

    private Map<String, Object> makeBody(final HttpStatus httpStatus, final String errorMessage) {
        final Map<String, Object> data = new HashMap<>();

        final LocalDateTime time = LocalDateTime.now();
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        data.put("statusCode", httpStatus);
        data.put("timeStamp", time.format(dateTimeFormatter));
        data.put("errorMessage", errorMessage);

        return data;
    }
}
