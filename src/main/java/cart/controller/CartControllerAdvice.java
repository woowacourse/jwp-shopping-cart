package cart.controller;

import cart.controller.dto.ErrorResponseDto;
import cart.exception.NoAuthorizationUserException;
import cart.exception.NoSuchDataException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = CartApiController.class)
public class CartControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> validHandler(final MethodArgumentNotValidException exception) {
        final String message = exception.getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(new ErrorResponseDto(message));
    }

    @ExceptionHandler(NoAuthorizationUserException.class)
    public ResponseEntity<ErrorResponseDto> notAuthorizationUserException(final NoAuthorizationUserException exception) {
        final String message = exception.getMessage();
        return ResponseEntity.badRequest().body(new ErrorResponseDto(message));
    }

    @ExceptionHandler(NoSuchDataException.class)
    public ResponseEntity<ErrorResponseDto> noProductDataHandler(final NoSuchDataException exception) {
        final String message = exception.getMessage();
        return ResponseEntity.badRequest().body(new ErrorResponseDto(message));
    }
}
