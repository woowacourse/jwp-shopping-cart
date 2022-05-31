package woowacourse.auth.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.auth.dto.response.ErrorResponse;
import woowacourse.auth.exception.AuthorizationException;

@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequest(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedRequest(AuthorizationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(e.getMessage()));
    }
}
