package cart.controller.advice;

import cart.controller.AdminController;
import cart.controller.AdminProductController;
import cart.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice(assignableTypes = {AdminController.class, AdminProductController.class})
public class AdminControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleBindException(final MethodArgumentNotValidException exception) {
        final String errorMessage = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> String.format("%s : %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.joining(System.lineSeparator()));

        return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
    }


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(final NoSuchElementException exception) {
        final String errorMessage = exception.getMessage();
        logger.error("error : {}", errorMessage);

        return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse(errorMessage));
    }
}
