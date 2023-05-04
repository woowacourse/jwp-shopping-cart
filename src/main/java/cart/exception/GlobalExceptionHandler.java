package cart.exception;

import cart.dto.response.ErrorResponse;
import cart.exception.BusinessIllegalArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessIllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> invalidArgumentException(BusinessIllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getErrorCode()));
    }

    @ExceptionHandler(BusinessProductIdNullException.class)
    public ResponseEntity<ErrorResponse> invalidProductException(BusinessProductIdNullException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getErrorCode()));
    }
}
