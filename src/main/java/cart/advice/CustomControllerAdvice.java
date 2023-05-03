package cart.advice;

import cart.dto.ApiErrorResponse;
import cart.dto.ApiResponse;
import cart.exception.InvalidPasswordException;
import cart.exception.ProductNotFoundException;
import cart.exception.UserNotFoundException;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ProductNotFoundException.class, UserNotFoundException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleNotFoundException(final RuntimeException exception) {
        return ApiErrorResponse.of(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ApiErrorResponse handleInvalidPasswordException(final RuntimeException exception) {
        return ApiErrorResponse.of(HttpStatus.UNAUTHORIZED, exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        String defaultMessage = Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage();

        return ApiErrorResponse.of(HttpStatus.BAD_REQUEST, defaultMessage);
    }
}
