package cart.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import cart.controller.dto.ExceptionResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String INVALID_FORMAT_EXCEPTION_MESSAGE = "형이 잘못되었습니다";

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgument(final IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(exception.getMessage()));
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ExceptionResponse> handle() {
        return ResponseEntity.badRequest().body(new ExceptionResponse(INVALID_FORMAT_EXCEPTION_MESSAGE));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException exception
    ) {
        final List<String> exceptionMessages = exception.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(new ExceptionResponse(exceptionMessages));
    }
}
