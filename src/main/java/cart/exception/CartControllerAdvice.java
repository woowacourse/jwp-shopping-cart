package cart.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CartControllerAdvice {
    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<String> handleEmptyDataAccessException(final EmptyResultDataAccessException e) {
        return ResponseEntity.badRequest().body("해당하는 상품이 없습니다.");
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<String> handleValidationException(final MethodArgumentNotValidException e) {
        final String errorMessage = "잘못된 요청입니다. " + e.getFieldErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler({UserAuthentificationException.class})
    public ResponseEntity<String> handleAuthentificationException(final UserAuthentificationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleException(final Exception e) {
        return ResponseEntity.internalServerError().body("서버 내부 오류가 발생했습니다.");
    }
}
