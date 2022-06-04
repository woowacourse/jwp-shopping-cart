package woowacourse.auth.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import woowacourse.auth.exception.AuthenticationFailureException;
import woowacourse.auth.exception.InvalidTokenException;

@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler({
        AuthenticationFailureException.class,
        InvalidTokenException.class
    })
    public ResponseEntity<String> handleAuthenticationFailure(final RuntimeException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

}
