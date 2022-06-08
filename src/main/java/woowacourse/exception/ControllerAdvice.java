package woowacourse.exception;

import javax.validation.ConstraintViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.auth.dto.ExceptionResponse;

@RestControllerAdvice
public class ControllerAdvice {

//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<String> handleUnhandledException() {
//        return ResponseEntity.badRequest().body("Unhandled Exception");
//    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> handle() {
        return ResponseEntity.badRequest().body("존재하지 않는 데이터 요청입니다.");
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            ConstraintViolationException.class,
    })
    public ResponseEntity<String> handleInvalidRequest(final RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidAccess(final CustomException e) {
        return new ResponseEntity<>(e.toExceptionResponse(), e.getHttpStatus());
    }
}
