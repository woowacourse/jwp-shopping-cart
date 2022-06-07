package woowacourse.exception;

import javax.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.auth.exception.UnAuthorizedException;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.member.exception.BadRequestException;

@RestControllerAdvice
public class GlobalControllerAdvice {

    private final Logger logger;

    public GlobalControllerAdvice() {
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequestExceptionHandler(BadRequestException e) {
        logger.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ErrorResponse> unAuthorizedExceptionHandler(UnAuthorizedException e) {
        logger.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        logger.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getBindingResult().
                getFieldError().
                getDefaultMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> validationExceptionHandler(ValidationException e) {
        logger.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()
                .split(":")[1]
                .trim()));
    }
}
