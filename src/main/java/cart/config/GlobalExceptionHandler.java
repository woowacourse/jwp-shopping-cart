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
    private static final String DEFAULT_ERROR_KEY = "errors";

    @ExceptionHandler
    public ResponseEntity<Object> handleException(Exception e) {
        LOGGER.warn(e.getMessage());
        return ResponseEntity.internalServerError().body(toErrorResponse("서버에서 오류가 발생했습니다"));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleIllegalArgumentsException(IllegalArgumentException e) {
        LOGGER.warn(e.getMessage());
        return ResponseEntity.badRequest().body(toErrorResponse(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleApiException(ApiException e) {
        LOGGER.warn(e.getMessage());
        return ResponseEntity.internalServerError().body(toErrorResponse(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleValidException(MethodArgumentNotValidException e) {
        LOGGER.info(e.getMessage());
        List<String> errors = e.getBindingResult().getAllErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(toErrorResponse(errors));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException e) {
        LOGGER.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(toErrorResponse("잘못된 인증정보입니다."));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleAuthenticationException(AuthorizationException e) {
        LOGGER.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("해당 서비스에 권한이 없습니다.");
    }

    private static Map<String, String> toErrorResponse(String message) {
        return Map.of(DEFAULT_ERROR_KEY, message);
    }

    private static Map<String, List<String>> toErrorResponse(List<String> messages) {
        return Map.of(DEFAULT_ERROR_KEY, messages);
    }
}
