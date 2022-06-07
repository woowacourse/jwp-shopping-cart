package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.auth.exception.InvalidTokenException;
import woowacourse.shoppingcart.dto.ErrorResponse;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@RestControllerAdvice
public class ControllerAdvice {

    private static final int UNAUTHORIZED = 401;
    private static final String UNEXPECTED = "[ERROR] 예상치 못한 에러가 발생하였습니다.";

    @ExceptionHandler({InvalidCustomerException.class, NotInCustomerCartItemException.class})
    public ResponseEntity handleInvalidSignUp(RuntimeException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler({InvalidTokenException.class})
    public ResponseEntity handleInvalidToken(RuntimeException e) {
        return ResponseEntity.status(UNAUTHORIZED).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity handleValidAnnotation(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity handleUnexpected(Exception e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(UNEXPECTED));
    }
}
