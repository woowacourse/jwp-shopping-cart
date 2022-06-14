package woowacourse.auth.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.auth.exception.ForbiddenException;
import woowacourse.auth.exception.InvalidLoginFormException;
import woowacourse.auth.exception.UnauthorizedException;
import woowacourse.shoppingcart.dto.ErrorResponse;

@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(InvalidLoginFormException.class)
    public ResponseEntity<ErrorResponse> handleInvalidLoginFormException(InvalidLoginFormException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Void> handleUnauthorizedException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Void> handleForbiddenException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
