package cart.global.exception.common;

import cart.global.exception.response.ProductErrorResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProductErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ProductErrorResponse productErrorResponse = new ProductErrorResponse(ExceptionStatus.BAD_INPUT_VALUE_EXCEPTION);
        List<FieldError> errors = exception.getBindingResult().getFieldErrors();
        for (FieldError error : errors) {
            productErrorResponse.addValidation(error.getField(), error.getDefaultMessage());
        }

        return productErrorResponse;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionStatus.BAD_INPUT_VALUE_EXCEPTION.getMessage());
    }

    @ExceptionHandler(CartException.class)
    public ResponseEntity<String> handleAuthorizationException(final CartException e) {
        return ResponseEntity.status(e.getExceptionStatus().getHttpStatus())
                .body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleInternalServerException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionStatus.INTERNAL_SERVER_EXCEPTION.getMessage());
    }
}
