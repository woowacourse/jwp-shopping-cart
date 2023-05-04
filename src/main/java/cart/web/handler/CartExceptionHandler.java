package cart.web.handler;

import cart.web.config.auth.exception.IllegalCredentialException;
import java.util.NoSuchElementException;
import javax.security.auth.login.LoginException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CartExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleIllegalResourceAccess() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("존재하지 않는 자원입니다.");
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> handleIllegalNumberFormatRequest() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("옳바르지 않은 숫자 형식입니다.");
    }

    @ExceptionHandler
    public ResponseEntity<String> handleIllegalArgumentRequest(final IllegalArgumentException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({LoginException.class, IllegalCredentialException.class})
    public ResponseEntity<String> handleLoginException() {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("유효하지 않은 인증 정보 입니다.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleRuntimeException() {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("예기치 못한 오류가 발생했습니다.");
    }
}
