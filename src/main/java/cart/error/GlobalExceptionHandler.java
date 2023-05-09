package cart.error;

import cart.error.exception.CartException;
import cart.error.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(CartException.class)
    public ResponseEntity<ErrorResponse> handlingApplicationException(final CartException e) {
        final ErrorCode errorCode = e.getErrorCode();
        log.error("\nSTATUS : {}\nCODE : {}\nMESSAGE : {}\n",
                errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage());

        return new ResponseEntity<>(
                new ErrorResponse(
                        errorCode.getStatus(),
                        errorCode.getCode(),
                        errorCode.getMessage()),
                HttpStatus.valueOf(errorCode.getStatus())
        );
    }

    @ExceptionHandler({BindException.class})
    public ResponseEntity<?> bindException(final BindException e) {
        Map<String, String> errorMap = new HashMap<>();

        for (FieldError error : e.getFieldErrors()) {
            errorMap.put(error.getField(), error.getDefaultMessage());
            log.error(error.toString());
            log.error(error.getDefaultMessage());
        }
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

}
