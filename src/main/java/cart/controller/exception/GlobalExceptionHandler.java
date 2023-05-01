package cart.controller.exception;

import cart.config.auth.AuthLoginException;
import cart.config.auth.BasicAuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ResponseEntity<ErrorResponse> infoBadRequest(final Exception e) {
        log.info("", e);
        final ErrorResponse response = new ErrorResponse(e.getMessage(), now(), BAD_REQUEST.value());

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    private ResponseEntity<ErrorResponse> infoUnAuthorized(final Exception e) {
        log.info("", e);
        final ErrorResponse response = new ErrorResponse(e.getMessage(), now(), UNAUTHORIZED.value());

        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(response);
    }

    private ResponseEntity<ErrorResponse> error(final Exception e) {
        log.error("", e);
        final ErrorResponse response = new ErrorResponse(
                "서버 내부에서 오류가 발생하였습니다. 계속 오류가 발생할 경우 관리자 측에 문의해주세요.", now(), INTERNAL_SERVER_ERROR.value());

        return ResponseEntity
                .internalServerError()
                .body(response);
    }

    @ExceptionHandler(value = {
            IllegalArgumentException.class,
            IllegalStateException.class,
            HttpRequestMethodNotSupportedException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentNotValidException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception e) {
        return infoBadRequest(e);
    }

    @ExceptionHandler(value = {
            AuthLoginException.class,
            BasicAuthException.class
    })
    public ResponseEntity<ErrorResponse> handleUnAuthorized(Exception e) {
        return infoUnAuthorized(e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(Exception e) {
        return error(e);
    }
}
