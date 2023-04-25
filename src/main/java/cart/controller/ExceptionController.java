package cart.controller;

import cart.dto.ErrorResponse;
import cart.dto.Response;
import cart.dto.SimpleResponse;
import cart.exception.ProductNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    private final Log log = LogFactory.getLog(ExceptionController.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Response> handle(RuntimeException e) {
        log.error("알 수 없는 문제가 발생했습니다.", e);
        return ResponseEntity
                .internalServerError()
                .body(new SimpleResponse("500", "알 수 없는 문제가 발생했습니다."));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handle(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = new ErrorResponse("400", "잘못된 요청입니다.");
        for (FieldError fieldError : e.getFieldErrors()) {
            errorResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Response> handleIllegalArgument(ProductNotFoundException e) {
        SimpleResponse response = new SimpleResponse("400", e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(response);
    }
}
