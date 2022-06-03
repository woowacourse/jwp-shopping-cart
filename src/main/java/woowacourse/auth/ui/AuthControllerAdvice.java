package woowacourse.auth.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.exception.AuthException;
import woowacourse.exception.dto.ErrorResponse;

@RestControllerAdvice
public class AuthControllerAdvice {
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(final AuthException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getErrorResponse());
    }
}
