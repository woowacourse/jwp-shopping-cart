package woowacourse.support.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import woowacourse.support.dto.ExceptionResponse;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleUnhandledException() {
        return ResponseEntity.badRequest().body("Unhandled Exception");
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            ConstraintViolationException.class,
    })
    public ResponseEntity<String> handleInvalidRequest(final RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ShoppingCartException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidAccess(final ShoppingCartException e) {
        final ShoppingCartExceptionCode exceptionCode = e.getExceptionCode();
        final ExceptionResponse exceptionResponse = new ExceptionResponse(
                exceptionCode.getCode(), exceptionCode.getMessage());
        return new ResponseEntity<>(exceptionResponse, exceptionCode.getHttpStatus());
    }
}
