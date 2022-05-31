package woowacourse.auth.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.auth.exception.InvalidTokenException;
import woowacourse.globalException.dto.ErrorResponse;
import woowacourse.shoppingcart.exception.InvalidMemberException;

@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidMemberException(InvalidMemberException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }
}
