package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.member.dto.response.ErrorResponse;

@RestControllerAdvice
public class ShoppingCartControllerAdvice {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException() {
        return ResponseEntity.badRequest().body(new ErrorResponse("입력하지 않은 정보가 있습니다."));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(RuntimeException e) {
        return ResponseEntity.internalServerError().body(new ErrorResponse("예기치 못한 오류가 발생하였습니다."));
    }
}
