package cart.controller;

import cart.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> methodArgumentNotValidException(Exception exception) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponseDto(exception));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponseDto> illegalStateException(Exception exception) {
        return ResponseEntity.internalServerError()
                .body(new ErrorResponseDto(exception));
    }

}
