package cart.controller;

import cart.dto.response.ErrorResponse;
import cart.dto.response.Response;
import cart.dto.response.SimpleResponse;
import cart.exception.AuthorizationException;
import cart.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Response> handle(RuntimeException e) {
        log.error("알 수 없는 문제가 발생했습니다.", e);
        return ResponseEntity
                .internalServerError()
                .body(SimpleResponse.internalServerError("알 수 없는 문제가 발생했습니다."));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handle(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = ErrorResponse.badRequest("잘못된 요청입니다.");
        for (FieldError fieldError : e.getFieldErrors()) {
            errorResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response> handle(NotFoundException e) {
        Response response = SimpleResponse.badRequest(e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<Response> handle(AuthorizationException e) {
        Response response = SimpleResponse.unAuthorized(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }
}
