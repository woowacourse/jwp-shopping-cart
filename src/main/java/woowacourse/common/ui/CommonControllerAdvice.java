package woowacourse.common.ui;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.common.exception.AuthException;
import woowacourse.common.exception.CustomException;
import woowacourse.common.exception.NotFoundException;
import woowacourse.common.exception.dto.ErrorResponse;

@RestControllerAdvice
public class CommonControllerAdvice {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        return ResponseEntity.badRequest().body(e.getErrorResponse());
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleJoinException(CustomException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getErrorResponse());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(CustomException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getErrorResponse());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnhandledException() {
        return ResponseEntity.badRequest().body("Unhandled Exception");
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> handle() {
        return ResponseEntity.badRequest().body("존재하지 않는 데이터 요청입니다.");
    }
}
