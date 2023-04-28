package cart.presentation;

import cart.presentation.dto.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ExceptionResponse exception = new ExceptionResponse(e.getMessage());
        logger.info(e.getMessage());

        return ResponseEntity.badRequest().body(exception);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        ExceptionResponse exception = new ExceptionResponse("예상하지 못한 오류가 발생했습니다.");
        logger.info(exception.getMessage(), e);

        return ResponseEntity.internalServerError().body(exception);
    }
}
