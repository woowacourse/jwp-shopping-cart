package cart.config;

import cart.exception.ApiException;
import cart.exception.AuthenticationException;
import cart.exception.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<Object> handleValidException(MethodArgumentNotValidException e) {
        LOGGER.info(e.getMessage());
        List<String> errors = e.getBindingResult().getAllErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        Map<String, List<String>> errorsResult = Map.of("errors", errors);

        return ResponseEntity.badRequest().body(errorsResult);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException e) {
        LOGGER.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleAuthenticationException(AuthorizationException e) {
        LOGGER.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleIllegalArgumentsException(IllegalArgumentException e) {
        LOGGER.info(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleApiException(ApiException e) {
        LOGGER.info(e.getMessage());
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(Exception e) {
        LOGGER.info(e.getMessage());
        Map<String, String> errorsResult = Map.of("errors", "서버에서 오류가 발생했습니다");
        return ResponseEntity.internalServerError().body(errorsResult);
    }
}
