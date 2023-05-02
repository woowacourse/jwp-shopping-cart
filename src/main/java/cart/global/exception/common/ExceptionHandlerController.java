package cart.global.exception.common;

import cart.global.exception.response.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ) {
        return ExceptionResponse.convertFrom(ExceptionStatus.INVALID_INPUT_VALUE_EXCEPTION, exception);
    }

    @ExceptionHandler(DataAccessResourceFailureException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleDatabaseException(DataAccessResourceFailureException exception) {
        errorLogging(exception);

        return ExceptionResponse.convertFrom(ExceptionStatus.DATABASE_EXCEPTION);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponse> handleBusinessException(BusinessException exception) {
        errorLogging(exception);

        final ExceptionResponse exceptionResponse = ExceptionResponse.convertFrom(exception);
        final HttpStatus httpStatus = exception.getHttpStatus();

        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnexpectedException(Exception exception) {
        errorLogging(exception);

        return ResponseEntity.internalServerError().body("전화 주세요");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleDomainException(IllegalArgumentException exception) {
        errorLogging(exception);

        return ExceptionResponse.convertFrom(ExceptionStatus.INVALID_INPUT_VALUE_EXCEPTION);
    }

    private void errorLogging(Exception e) {
        log.info("클래스 이름 = {} 메시지 = {}", e.getClass().getSimpleName(), e.getMessage());
    }
}
