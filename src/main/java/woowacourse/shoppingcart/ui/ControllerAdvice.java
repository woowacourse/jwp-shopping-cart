package woowacourse.shoppingcart.ui;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.member.dto.response.ErrorResponse;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException() {
        return ResponseEntity.badRequest().body(new ErrorResponse("존재하지 않는 상품입니다."));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException() {
        return ResponseEntity.badRequest().body(new ErrorResponse("입력하지 않은 정보가 있습니다."));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(new ErrorResponse("internal server error"));
    }
}
