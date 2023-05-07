package cart.controller;

import cart.controller.exception.CartHasDuplicatedItemsException;
import cart.controller.exception.IncorrectAuthorizationMethodException;
import cart.controller.exception.MissingAuthorizationHeaderException;
import cart.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler({IllegalArgumentException.class, CartHasDuplicatedItemsException.class})
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Exception exception) {
        return ResponseEntity.badRequest().body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler({MissingAuthorizationHeaderException.class, IncorrectAuthorizationMethodException.class})
    public ResponseEntity<ErrorResponse> handleAuthorizationException(
            Exception exception
    ) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(exception.getMessage()));
    }
}
