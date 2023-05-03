package cart.controller.advice;

import cart.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String UNEXPECTED_ERROR_MESSAGE = "네트워크 연결 상태가 좋지 않습니다 잠시 후 다시 시도해 주세요.";

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUnexpectedException(final Exception exception) {
        final String errorMessage = exception.getMessage();
        logger.error("error : {}", errorMessage);

        return ResponseEntity.internalServerError().body(new ErrorResponse(UNEXPECTED_ERROR_MESSAGE));
    }
}
