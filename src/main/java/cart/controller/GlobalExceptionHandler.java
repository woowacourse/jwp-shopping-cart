package cart.controller;

import cart.dto.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<List<ErrorResponse>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<ErrorResponse> responses = exception
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> new ErrorResponse(
                        ((FieldError) error).getField() + " : " + error.getDefaultMessage(), LocalDateTime.now()
                        )
                )
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(responses);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponse(exception.getMessage(), LocalDateTime.now()));
    }
}
