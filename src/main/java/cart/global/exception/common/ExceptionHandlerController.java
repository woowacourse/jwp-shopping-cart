package cart.global.exception.common;

import cart.global.exception.response.BindingResultExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<BindingResultExceptionResponse>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ) {
        errorLogging(exception);

        final List<BindingResultExceptionResponse> bindingResultExceptionResponses =
                BindingResultExceptionResponse.from(exception.getBindingResult());

        return new ResponseEntity<>(bindingResultExceptionResponses, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAccessResourceFailureException.class)
    public ResponseEntity<String> handleDatabaseException(DataAccessResourceFailureException exception) {
        errorLogging(exception);

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(BusinessException exception) {
        errorLogging(exception);

        final HttpStatus httpStatus = exception.getHttpStatus();

        return new ResponseEntity<>(exception.getMessage(), httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnexpectedException(Exception exception) {
        errorLogging(exception);

        return ResponseEntity.internalServerError()
                             .body("전화 주세요");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleDomainException(IllegalArgumentException exception) {
        errorLogging(exception);

        return ResponseEntity.badRequest()
                             .body(exception.getMessage());
    }

    private void errorLogging(Exception e) {
        log.info("클래스 이름 = {} 메시지 = {}", e.getClass().getSimpleName(), e.getMessage());
    }
}
