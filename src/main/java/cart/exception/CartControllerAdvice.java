package cart.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CartControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<String> handleEmptyDataAccessException(final EmptyResultDataAccessException e) {
        return ResponseEntity.badRequest().body("해당하는 상품이 없습니다.");
    }

    @ExceptionHandler({UserAuthenticationException.class})
    public ResponseEntity<String> handleAuthentificationException(final UserAuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleOtherException(final Exception e, WebRequest request) {
        try {
            return handleException(e, request);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 오류가 발생했습니다");
        }
    }
}
