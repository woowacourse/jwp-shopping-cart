package cart.exception;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                ex.getMessage()
        );
        return ResponseEntity
                .badRequest()
                .body(exceptionResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                ex.getMessage()
        );
        return ResponseEntity
                .badRequest()
                .body(exceptionResponse);
    }

    @ExceptionHandler(BasicAuthException.class)
    public ResponseEntity<ExceptionResponse> handleBasicAuthException(final BasicAuthException exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                "인증된 사용자가 아닙니다."
        );
        exception.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exceptionResponse);
    }

    @ExceptionHandler(InvalidDomainException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidDomainException(final InvalidDomainException exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                exception.getMessage()
        );

        return ResponseEntity
                .badRequest()
                .body(exceptionResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(final IllegalArgumentException exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                exception.getMessage()
        );

        return ResponseEntity
                .badRequest()
                .body(exceptionResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleCommonException(final Exception exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                "알 수 없는 서버 에러가 발생헀습니다."
        );

        exception.printStackTrace();
        return ResponseEntity
                .internalServerError()
                .body(exceptionResponse);
    }
}
