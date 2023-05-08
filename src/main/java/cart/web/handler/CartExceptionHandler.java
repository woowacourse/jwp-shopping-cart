package cart.web.handler;

import cart.exception.IllegalAuthorizationPrefixException;
import cart.exception.NoSuchDataExistException;
import cart.exception.VerifyUserException;
import cart.web.dto.request.PathVariableId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class CartExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(
            final MethodArgumentTypeMismatchException exception
    ) {
        if (exception.getMostSpecificCause().getClass().isAssignableFrom(PathVariableId.class)) {
            log.warn("경로 변수에 잘못된 Id 형식으로 요청되었습니다.", exception.getMostSpecificCause());
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMostSpecificCause().getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleNoSuchDataExistException(
            final NoSuchDataExistException exception
    ) {
        log.warn("존재하지 않은 데이터에 접근하였습니다. \n{}", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleIllegalArgumentRequest(final IllegalArgumentException exception) {
        log.warn("유효하지 않은 값이 사용되었습니다. \n{}", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({VerifyUserException.class, IllegalAuthorizationPrefixException.class})
    public ResponseEntity<String> handleAuthorizationException(final RuntimeException exception) {
        log.warn("사용자 인증 . \n{}", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleRuntimeException(final Exception exception) {
        log.error("예기치 못한 오류가 발생했습니다.", exception);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("예기치 못한 오류가 발생했습니다.");
    }
}
