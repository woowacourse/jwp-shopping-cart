package woowacourse.auth.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.auth.exception.InvalidTokenException;

@RestControllerAdvice
public class AuthControllerAdvice {
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTokenException(final InvalidTokenException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.UNAUTHORIZED);
    }
}
