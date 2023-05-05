package cart.controller;

import cart.controller.Exception.CartHasDuplicatedItemsException;
import cart.controller.Exception.MissingAuthorizationHeaderException;
import cart.dto.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<List<ErrorResponse>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ) {
        List<ErrorResponse> responses = exception.getBindingResult().getAllErrors()
                .stream()
                .map(error -> new ErrorResponse(
                        ((FieldError) error).getField() + " : " + error.getDefaultMessage()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(responses);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleCartHasDuplicatedItemsException(
            CartHasDuplicatedItemsException exception
    ) {
        return ResponseEntity.badRequest().body(new ErrorResponse(exception.getMessage()));
    }
}
